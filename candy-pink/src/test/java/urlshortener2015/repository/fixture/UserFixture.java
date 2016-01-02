package urlshortener2015.repository.fixture;

import urlshortener2015.candypink.domain.User;

public class UserFixture {

	public static User user1() {
		return new User("user1", "pwd1", true, "user1@email.com", "ROLE_USER");
	}
	
	public static User user1Modified() {
		return new User("user1", "pwd2", true, "user1@email.com", "ROLE_USER");
	}	

	public static User user2() {
		return new User("user2", "pwd2", true, "user2@email.com", "ROLE_USER");
	}

	public static User userPassword() {
		return new User("user3", "password", true, "user3@email.com", "ROLE_USER");
	}

	public static User userEmail() {
		return new User("user4", "null", true, "email", "ROLE_USER");
	}

	public static User userAuthority() {
		return new User("user5", "null", true, "null", "ROLE_USER");
	}

	public static User badUser() {
		return new User(null, "null", true, "null", "ROLE_USER");
	}
}
