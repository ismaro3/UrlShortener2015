package urlshortener2015.candypink.repository.fixture;

import urlshortener2015.candypink.domain.User;

public class UserFixture {

	public static User user1() {
		return new User("user1", "pwd1", "user1@email.com", "null");
	}
	
	public static User user1Modified() {
		return new User("user1", "pwd2", "user1@email.com", "null");
	}	

	public static User user2() {
		return new User("user2", "pwd2", "user2@email.com", "null");
	}

	public static User userPassword() {
		return new User("user3", "password", "null", "null");
	}

	public static User userEmail() {
		return new User("user4", "null", "email", "null");
	}

	public static User userRole() {
		return new User("user5", "null", "null", "rol");
	}

	public static User badUser() {
		return new User(null, "null", "null", "null");
	}
}
