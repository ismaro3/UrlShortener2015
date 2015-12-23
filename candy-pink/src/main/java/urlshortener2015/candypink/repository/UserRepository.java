package urlshortener2015.candypink.repository;

import java.util.List;

import urlshortener2015.candypink.domain.User;

public interface UserRepository {

	List<User> getAllUsers();

	User findByUsernameOrEmail(String id);

	User save(User user);

	void update(User user);

	void delete(String username);

	void deleteAll();

	Long count();
}
