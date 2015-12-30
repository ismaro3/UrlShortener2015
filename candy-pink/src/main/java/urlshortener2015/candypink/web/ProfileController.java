package urlshortener2015.candypink.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import urlshortener2015.candypink.repository.ShortURLRepositoryImpl;

@Controller
@RequestMapping("/profile")
public class ProfileController {

	private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
	
	@Autowired
	protected ShortURLRepositoryImpl repo;

	public ProfileController() {}

	public ProfileController(ShortURLRepositoryImpl repo) {
		this.repo = repo;
  	}
  
  	@RequestMapping(method = RequestMethod.GET)
  	public String getUrisFromUser(Model model, String user) {
		logger.info("Requested profile from user " + user);
    		model.addAttribute("uris", repo.findByUserlast24h(user));
		return "profilePage";
  	}
}
