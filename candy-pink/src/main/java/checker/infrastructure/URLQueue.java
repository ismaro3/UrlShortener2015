package checker.infrastructure;

import urlshortener2015.candypink.domain.ShortURL;

public interface URLQueue {
	
	ShortURL take();
	boolean add(ShortURL element);
}