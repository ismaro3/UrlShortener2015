package urlshortener2015.candypink.web;

import com.google.common.hash.Hashing;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.ws.client.core.WebServiceTemplate;

import checker.web.ws.schema.GetCheckerRequest;
import checker.web.ws.schema.GetCheckerResponse;
import urlshortener2015.candypink.domain.ShortURL;
import urlshortener2015.candypink.repository.ShortURLRepository;

import javax.annotation.PostConstruct;
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
import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UrlShortenerController {

	private static final Logger logger = LoggerFactory.getLogger(UrlShortenerController.class);

	private Jaxb2Marshaller marshaller;//Communication to WS
	
	@Autowired
	protected ShortURLRepository shortURLRepository;

	@RequestMapping(value = "/{id:(?!link|index|login|signUp|profile|manage).*}", method = RequestMethod.GET)
	public ResponseEntity<?> redirectTo(@PathVariable String id, HttpServletRequest request) {
		logger.info("Requested redirection with hash " + id);
		ShortURL l = shortURLRepository.findByKey(id);
		if (l != null) {
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
			@RequestParam(value = "safe", required = false) String safe,
			@RequestParam(value = "users", required = false) String users,
			@RequestParam(value = "time", required = false) String time,
			@RequestParam(value = "sponsor", required = false) String sponsor,
			@RequestParam(value = "brand", required = false) String brand,
			HttpServletRequest request) {
		logger.info("Requested new short for uri " + url);
		logger.info("Uri" + url);
		logger.info("Sponsor" + sponsor);
		logger.info("Brand" + brand);
		logger.info("Safe: " + safe);
		logger.info("Users: " + users);
		logger.info("Time: " + time);
		Client client = ClientBuilder.newClient();
		ShortURL su = createAndSaveIfValid(url, safe, sponsor, brand, UUID
			.randomUUID().toString(), extractIP(request));
		if (su != null) {
			// Url requested is not safe
			if (su.getSafe() == false) {
				HttpHeaders h = new HttpHeaders();
				h.setLocation(su.getUri());
				logger.info("Requesting to Checker service");
				GetCheckerRequest requestToWs = new GetCheckerRequest();
				requestToWs.setUrl(url);
				Object response = new WebServiceTemplate(marshaller).marshalSendAndReceive("http://localhost:"
						+ "8080" + "/ws", requestToWs);
				GetCheckerResponse checkerResponse = (GetCheckerResponse) response;
				String resultCode = checkerResponse.getResultCode();
				logger.info("respuesta recibida por el Web Service: "+resultCode);
				if(resultCode.equals("ok")){
					return new ResponseEntity<ShortURL>(su, h, HttpStatus.CREATED);
				}else{
					return new ResponseEntity<ShortURL>(HttpStatus.BAD_REQUEST);
				}
				// Url requested is safe
			} else {
				return null;
			}
		} else {
			return new ResponseEntity<ShortURL>(HttpStatus.BAD_REQUEST);
		}
	}

	protected ShortURL createAndSaveIfValid(String url, String token, String sponsor,
			String brand, String owner, String ip) {
		UrlValidator urlValidator = new UrlValidator(new String[] { "http",
				"https" });
		if (urlValidator.isValid(url)) {
			String id = Hashing.murmur3_32()
					.hashString(url, StandardCharsets.UTF_8).toString();
			ShortURL su = new ShortURL(id, url,
					linkTo(
						methodOn(UrlShortenerController.class).redirectTo(
							id, null)).toUri(), token, sponsor,
							new Date(System.currentTimeMillis()),
							owner, HttpStatus.TEMPORARY_REDIRECT.value(),
							false, null,null,null, null, ip, null, null);
			if (su != null) {
					return shortURLRepository.save(su);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@PostConstruct
	private void initWsComs(){
		marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan(ClassUtils.getPackageName(GetCheckerRequest.class));
		try {
			marshaller.afterPropertiesSet();
		} catch (Exception e) {}
	}

	/*
	* This method checks an URI against the Google Safe Browsing API,
	* then it updates the database if needed.
	* According to Google's API, by making a GET request the URI sent
	* is checked and message is created with status code in response.
	* Status OK 200 means that uri is unsafe, and 204 indicates that is
	* safe. Other reponse status are caused by error. 
	*/
   public boolean checkInternal(ShortURL url){
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
}
