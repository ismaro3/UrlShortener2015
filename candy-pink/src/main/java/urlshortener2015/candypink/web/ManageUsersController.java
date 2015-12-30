package urlshortener2015.candypink.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import urlshortener2015.candypink.repository.UserRepositoryImpl;

@Controller
@RequestMapping("/manageUsers")
public class ManageUsersController {

	private static final Logger logger = LoggerFactory.getLogger(ManageUsersController.class);
	
	@Autowired
	protected UserRepositoryImpl repo;

	public ManageUsersController() {}

	public ManageUsersController(UserRepositoryImpl repo) {
		this.repo = repo;
  	}
  
  	@RequestMapping(method = RequestMethod.GET)
  	public String getUsers(Model model) {
		logger.info("Requested all users info");
    		model.addAttribute("users", repo.getAllUsers());
		return "manageUsersPage";
  	}
}
