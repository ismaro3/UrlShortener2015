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
@RequestMapping("/admin")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	private UserRepositoryImpl repo = new UserRepositoryImpl();

	public AdminController() {}

	public AdminController(UserRepositoryImpl repo){
        	this.repo = repo;
    	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<User> login(@RequestParam("id") String id,
			        @RequestParam("password") String password, HttpServletRequest request) {
		logger.info("Requested login with username " + id);
		//Verify the fields aren´t empty
		if(verifyFields(id, password)) {
		  User user = repo.findByUsernameOrEmail(id);
		  //There are a user with this username or email
		  if(user != null) {
			BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
			// The password is correct
			if(encoder.matches(password, user.getPassword())) {
				// The user is admin
				if(user.getRole().equals("ADMIN")) {
					return new ResponseEntity<>(user, HttpStatus.CREATED);
				}
				// The user is not admin
				else {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}
			// The password is incorrect
			else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		  }
		  //There aren't a user with this username or email
		  else {
		    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		  }
		}
		// There are empty fields
		else {
		  return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

       /**
	 * Return true if id and password are not empty. Return false otherwise.
	 * @param id - Id to verify.
	 * @param password - Paswword to verify.
	 * @return Return true if id and password are not empty. Return false otherwise.
	 */
	private boolean verifyFields(String id, String password) {
		// Check id
	  	if(id == null || id.isEmpty()) {
	  	  return false;
	  	}
	  	// Check password
	  	else if(password == null || password.isEmpty()) {
	  	  return false;
	  	}
	  	return true;
	}
}
