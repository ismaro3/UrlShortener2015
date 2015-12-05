package urlshortener2015.candypink.repository;

import java.util.List;
import urlshortener2015.candypink.domain.UserShort;
import urlshortener2015.common.domain.ShortURL; 

public interface UserShortRepository {

	//List<ShortURL> findShortURLofUser(String username);
	
	//List<ShortURL> findShortURLofUser(String username, String dateIni, String dateFin);
	
	UserShort save(UserShort us);

	Long count();
}
