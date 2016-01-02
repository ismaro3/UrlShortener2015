package urlshortener2015.candypink.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 

import urlshortener2015.candypink.domain.User;
import urlshortener2015.candypink.repository.UserRepositoryImpl;

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
