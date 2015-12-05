package urlshortener2015.candypink.repository.fixture;

import urlshortener2015.candypink.domain.User;

public class UserFixture {

	public static User user1() {
		return new User("user1", "null", "null", "null", "Name1");
	}
	
	public static User user1Modified() {
		return new User("user1", "null", "null", "null", "Name1Updated");
	}	

	public static User user2() {
		return new User("user2", "null", "null", "null", "null");
	}

	public static User userPassword() {
		return new User("user3", "password", "null", "null", "null");
	}

	public static User userEmail() {
		return new User("user4", "null", "email", "null", "null");
	}

	public static User userRole() {
		return new User("user5", "null", "null", "rol", "null");
	}

	public static User userName() {
		return new User("user6", "null", "null", "null", "name");
	}

	public static User badUser() {
		return new User("null", "null", "null", "null", "null");
	}
}
