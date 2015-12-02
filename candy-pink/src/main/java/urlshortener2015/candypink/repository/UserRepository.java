package urlshortener2015.candypink.repository;

import urlshortener2015.candypink.domain.User;

public interface UserRepository {

	User findByUsername(String username);
	
	User findByEmail(String email);

	User save(User user);

	void update(User user);

	void delete(String username);

	void deleteAll();

	Long count();
}
