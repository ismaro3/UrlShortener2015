package checker.web.ws;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

import urlshortener2015.candypink.domain.ShortURL;
import checker.service.CheckerService;

@Endpoint
public class URLCheckerEndpoint{

	@Autowired
	CheckerService checkerService;

	@PayloadRoot(namespace = "http://checker/web/ws/schema", localPart = "ShortURL")
	public void checker(@RequestPayload ShortURL url){
		checkerService.checkURL(url);
	}
}
