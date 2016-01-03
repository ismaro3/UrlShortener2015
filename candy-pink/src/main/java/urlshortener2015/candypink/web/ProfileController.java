package urlshortener2015.candypink.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import urlshortener2015.candypink.repository.ShortURLRepository;
import urlshortener2015.candypink.repository.ShortURLRepositoryImpl;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/profile")
public class ProfileController {

	private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

	@Autowired
	protected ShortURLRepository shortURLRepository;


	public ProfileController() {}

	public ProfileController(ShortURLRepositoryImpl shortURLRepository) {
		this.shortURLRepository = shortURLRepository;
  	}
  
  	@RequestMapping(method = RequestMethod.GET)
  	public ModelAndView getUrisFromUser(HttpServletRequest request, HttpSession session) {
		String user = (String) session.getAttribute("Username");
		logger.info("Requested profile from user " + user);
		ModelAndView model = new ModelAndView();
    		model.addObject("Uris", shortURLRepository.findByUserlast24h(user));
		model.setViewName("profilePage.html");
		return model;
  	}
}
