package urlshortener2015.candypink.repository;

import java.util.List;

public interface UserRepository {

	User findByName(String name);

	User save(User user);

	void update(User user);

	void delete(String name);

	void deleteAll();

	Long count();
}
