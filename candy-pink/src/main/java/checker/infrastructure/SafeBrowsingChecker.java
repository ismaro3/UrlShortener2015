package checker.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import urlshortener2015.candypink.repository.ShortURLRepositoryImpl;
import urlshortener2015.candypink.domain.ShortURL;

@Component("safeBrowsingChecker")
public class SafeBrowsingChecker extends CheckerImpl {

	@Value("{apiKey}")
	private String apiKey;

	@Value("{client}")
	private String client;

	@Value("{appver}")
	private String appver;

	@Value("{pver}")
	private String pver;

	@Autowired
	private ShortURLRepositoryImpl shortURLRepositoryImpl;

	public String getApiKey(){
		return this.apiKey;
	}
	
	/*
	*This method checks an URI against the Google Safe Browsing API,
	* then it updates the database if needed.
	*According to Google's API, by making a GET request the URI sent
	* is checked and message is created with status code in response.
	*Status OK 200 means that uri is unsafe, and 204 indicates that is
	* safe. Other reponse status are caused by error. 
	*/
    @Override
   public void checkInternal(ShortURL url){
		Client client = ClientBuilder.newClient();
		
		//Preparing URI to check 
		WebTarget target = client.target("https://sb-ssl.google.com/safebrowsing/api/lookup");
		WebTarget targetWithQueryParams = target.queryParam("client", client);
		targetWithQueryParams = targetWithQueryParams.queryParam("appver",appver);
		targetWithQueryParams = targetWithQueryParams.queryParam("pver",pver);
		targetWithQueryParams = targetWithQueryParams.queryParam("url",url.getTarget());

		Response response = targetWithQueryParams.request(MediaType.TEXT_PLAIN_TYPE).get();
		ShortURL res;
		if(response.getStatus()==204){//Uri is safe
			res = shortURLRepositoryImpl.mark(url,true);
			shortURLRepositoryImpl.update(res);
		}else if(response.getStatus()==200){//Uri is unsafe
			res = shortURLRepositoryImpl.mark(url,false);
			shortURLRepositoryImpl.update(res);
		}
	}
}
