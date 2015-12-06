package urlshortener2015.candypink.repository.fixture;

import urlshortener2015.candypink.domain.ShortURL;

public class ShortURLFixture {

	public static ShortURL url1() {
		return new ShortURL("1", "http://www.unizar.es/", null, null, null, null, null, false,
				null, null, null);
	}

	public static ShortURL url1modified() {
		return new ShortURL("1", "http://www.unizar.org/", null, null, null, null, null, false,
				null, null, null);
	}

	public static ShortURL url2() {
		return new ShortURL("2", "http://www.unizar.es/", null, null, null, null, null, false,
				null, null, null);
	}

	public static ShortURL url3() {
		return new ShortURL("3", "http://www.google.es/", null, null, null, null, null, false,
				null, null, null);
	}

	public static ShortURL badUrl() {
		return new ShortURL(null, null, null, null, null, null, null, false,
				null, null, null);
	}

	public static ShortURL urlSponsor() {
		return new ShortURL("3", null, null, "sponsor", null, null, null,
				false, null, null, null);
	}

	public static ShortURL urlSafe() {
		return new ShortURL("4", null, null, "sponsor", null, null, null, true,
				null, null, null);
	}
	public static ShortURL url1user1() {
		return new ShortURL("10", null, null, "sponsor", null, null, null, true,
				null, null, "user1");
	}
	public static ShortURL url2user1() {
		return new ShortURL("11", null, null, "sponsor", null, null, null, true,
				null, null, "user1");
	}
}
