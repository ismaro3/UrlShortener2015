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
import java.util.Random;

@RestController
@RequestMapping("/signUp")
public class SignUpController {

	private static final Logger logger = LoggerFactory.getLogger(SignUpController.class);
	
	private UserRepositoryImpl repo = new UserRepositoryImpl();

	public SignUpController() {}

	public SignUpController(UserRepositoryImpl repo){
        	this.repo = repo;
    	}

	/**
	 *
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<User> register(@RequestParam("username") String username,
			        @RequestParam("password") String password, @RequestParam("email") String email,
			        @RequestParam("name") String name, HttpServletRequest request) {
		logger.info("Requested registration with username " + username);
		Random r = new Random();
		String role = null;
		switch (r.nextInt(2)) {
			case 0: role = "USER_NORMAL";
			case 1: role = "USER_PREMIUM";
		}
		User user = new User(username, password, email, role, name);
		//Verify the fields aren´t empty
		if(verifyFields(user)) {
		  //There are a user with the same username
		  if(repo.findByUsernameOrEmail(username) != null) {
		    return new ResponseEntity<>(HttpStatus.CONFLICT);
		  }
		  //There are a user with the same email
		  else if(repo.findByUsernameOrEmail(email) != null) {
		    return new ResponseEntity<>(HttpStatus.CONFLICT);
		  }
		  // There aren´t other users with this username or email
		  else {
		    BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		    user.setPassword(encoder.encode(password));
		    repo.save(user);
         	    return new ResponseEntity<>(user, HttpStatus.CREATED);
		  }
		}
		// There are empty fields
		else {
		  return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

    /**
	 * Return true if the user haven´t got empty fields. Return false otherwise.
	 * @param user - The user to verify.
	 * @return Return true if the user haven´t got empty fields. Return false otherwise.
	 */
	private boolean verifyFields(User user) {
		// Check username
	  	if(user.getUsername()==null || user.getUsername().isEmpty()) {
	  	  return false;
	  	}
	  	// Check password
	  	else if(user.getPassword()==null || user.getPassword().isEmpty()) {
	  	  return false;
	  	}
	  	// Check email
	  	else if(user.getEmail()==null || user.getEmail().isEmpty()) {
	  	  return false;
	  	}
	  	// Check role
	  	else if(user.getRole()==null || user.getRole().isEmpty()) {
	  	  return false;
	  	}
	  	//Check name
	  	else if(user.getName()==null || user.getName().isEmpty()) {
	  	  return false;
	  	}
	  	return true;
	}
}