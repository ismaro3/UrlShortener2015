package urlshortener2015.candypink.web;

import com.google.common.hash.Hashing;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urlshortener2015.candypink.domain.ShortURL;
import urlshortener2015.candypink.repository.ShortURLRepository;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.Random;
import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UrlShortenerController {

	private static final Logger logger = LoggerFactory.getLogger(UrlShortenerController.class);
	
	@Autowired
	protected ShortURLRepository shortURLRepository;

	@RequestMapping(value = "/{id:(?!link|index|login|signUp|profile|manage).*}", method = RequestMethod.GET)
	public ResponseEntity<?> redirectTo(@PathVariable String id, 
					    @RequestParam(value = "token", required = false) String token,
					    HttpServletRequest request) {
		logger.info("Requested redirection with hash " + id);
		ShortURL l = shortURLRepository.findByKey(id);
		logger.info("Client token " + token + " - Real token: " + l.getToken());
		if (l != null) {
			// URL is safe, we must check token
			if (l.getSafe() == true) {
				// Token doesn't match
				if (!token.equals(l.getToken())) {
					return new ResponseEntity<>(HttpStatus.FORBIDDEN);
				}
			}
			// Url is not safe or token matches
			return createSuccessfulRedirectToResponse(l);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	protected String extractIP(HttpServletRequest request) {
		return request.getRemoteAddr();
	}

	protected ResponseEntity<?> createSuccessfulRedirectToResponse(ShortURL l) {
		HttpHeaders h = new HttpHeaders();
		h.setLocation(URI.create(l.getTarget()));
		return new ResponseEntity<>(h, HttpStatus.valueOf(l.getMode()));
	}


	@RequestMapping(value = "/link", method = RequestMethod.POST)
	public ResponseEntity<ShortURL> shortener(@RequestParam("url") String url,
			@RequestParam(value = "users", required = false) String users,
			@RequestParam(value = "time", required = false) String time,
			@RequestParam(value = "sponsor", required = false) String sponsor,
			@RequestParam(value = "brand", required = false) String brand,
			HttpServletRequest request) {
		logger.info("Requested new short for uri " + url);
		logger.info("Users who can redirect: " + users);
		logger.info("Time to be safe: " + time);
		Client client = ClientBuilder.newClient();
		Response response = client.target(url).request().get();
		// Url is reachable
		if (response.getStatus() == 200) {
			logger.info("Uri " + url + " is reachable");
			// Url requested is safe -> True
			// Url requested is not safe -> False
			boolean safe = !(users.equals("select") && time.equals("select"));
			ShortURL su = createAndSaveIfValid(url, safe, sponsor, brand, UUID
				.randomUUID().toString(), extractIP(request));
			if (su != null) {
				HttpHeaders h = new HttpHeaders();
				h.setLocation(su.getUri());
				return new ResponseEntity<ShortURL>(su, h, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<ShortURL>(HttpStatus.BAD_REQUEST);
			}
		}
		// Url is not reachable
		else {
			return new ResponseEntity<ShortURL>(HttpStatus.BAD_REQUEST);
		}
	}

	protected ShortURL createAndSaveIfValid(String url, boolean safe, String sponsor,
			String brand, String owner, String ip) {
		UrlValidator urlValidator = new UrlValidator(new String[] { "http",
				"https" });
		if (urlValidator.isValid(url)) {
			String id = Hashing.murmur3_32()
					.hashString(url, StandardCharsets.UTF_8).toString();
			String token = null;
			// If Url is safe, we create the token, else token = null
			if (safe == true) {
				// Random token of ten digits
				token = createToken(10);
			}
			// ShortUrl
			ShortURL su = new ShortURL(id, url,
					linkTo(
						methodOn(UrlShortenerController.class).redirectTo(
							id, token, null)).toUri(), token, sponsor,
							new Date(System.currentTimeMillis()),
							owner, HttpStatus.TEMPORARY_REDIRECT.value(),
							safe, null,null,null, null, ip, null, null);
			// This checks if uri is malware
			if (su != null) {
				boolean spam = checkInternal(su);	
				if (!spam) {
					return shortURLRepository.save(su);	
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	* This method checks an URI against the Google Safe Browsing API,
	* then it updates the database if needed.
	* According to Google's API, by making a GET request the URI sent
	* is checked and message is created with status code in response.
	* Status OK 200 means that uri is unsafe, and 204 indicates that is
	* safe. Other reponse status are caused by error. 
	*/
   	public boolean checkInternal(ShortURL url) {
		Client client = ClientBuilder.newClient();
		
		// Preparing URI to check 
		WebTarget target = client.target("https://sb-ssl.google.com/safebrowsing/api/lookup");
		WebTarget targetWithQueryParams = target.queryParam("key", "AIzaSyDI60aszp__CG9n4B3n3gd-YDEh-uowUwM");
		targetWithQueryParams = targetWithQueryParams.queryParam("client", "CandyShort");
		targetWithQueryParams = targetWithQueryParams.queryParam("appver","1.0");
		targetWithQueryParams = targetWithQueryParams.queryParam("pver","3.1");
		targetWithQueryParams = targetWithQueryParams.queryParam("url",URLEncoder.encode(url.getTarget()));

		Response response = targetWithQueryParams.request(MediaType.TEXT_PLAIN_TYPE).get();
		ShortURL res;
		if (response.getStatus()==204) { 		// Uri is safe
			logger.info("La uri no es malware | no deseada");
			return false;
		} else if (response.getStatus()==200) { 	// Uri is unsafe
			logger.info("La uri es malware o no deseada");
			return true;
		}
		return false;
	}
	
	/**
	 * Creates a random token of digits
	 * @param length - length of the token to return
	 */
	private String createToken(int length) {
		Random r = new Random();
		String token = "";
		for (int i = 0; i < length; i++) {
			// Only digits in the token
			token += r.nextInt(10);
		}
		return token;
	}
}
