package urlshortener2015.candypink.repository;

import urlshortener2015.candypink.domain.User;

public interface UserRepository {

	User findByName(String name);

	User save(User user);

	void update(User user);

	void delete(String name);

	void deleteAll();

	Long count();
}
