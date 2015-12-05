package urlshortener2015.candypink.repository;

import urlshortener2015.candypink.domain.UserShort;

public interface UserShortRepository {

	List<ShortURL> findShortURLofUser(String username);
	
	//List<ShortURL> findShortURLofUser(String username, String dateIni, String dateFin);
	
	UserShort save(UserShort us);

	Long count();
}
