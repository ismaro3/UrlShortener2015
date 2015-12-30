package urlshortener2015.candypink.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import urlshortener2015.candypink.repository.UserRepositoryImpl;

@RestController
@RequestMapping("/manageUsers")
public class ManageUsersController {

	private static final Logger logger = LoggerFactory.getLogger(ManageUsersController.class);
	
	private UserRepositoryImpl repo = new UserRepositoryImpl();

	public ManageUsersController() {}

	public ManageUsersController(UserRepositoryImpl repo) {
		this.repo = repo;
  	}
  
  	@RequestMapping(method = RequestMethod.GET)
  	public void getUsers(Model model, HttpServletResponse response) throws IOException {
		logger.info("Requested all users info");
    		model.addAttribute("users", repo.getAllUsers());
		response.sendRedirect("manageUsersPage.html");
  	}
}
