package urlshortener2015.candypink.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/403")
public class FailController {

	private static final Logger logger = LoggerFactory.getLogger(FailController.class);
	

	public FailController() {}


	public String fail(HttpServletRequest request) {
		logger.info("No puedes pasar");
		return "403";
	}
}
