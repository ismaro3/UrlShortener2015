package urlshortener2015.candypink.repository;

import java.util.List;

import urlshortener2015.candypink.domain.ShortURL;

public interface ShortURLRepository {

	ShortURL findByKey(String id);

	List<ShortURL> findByTarget(String target);

	ShortURL save(ShortURL su);

	ShortURL mark(ShortURL urlSafe, boolean safeness);

	void update(ShortURL su);

	void delete(String id);

	Long count();

	List<ShortURL> list(Long limit, Long offset);

	List<ShortURL> findByUser(String user);
	
	List<ShortURL> findByUserlast24h(String user);

}
