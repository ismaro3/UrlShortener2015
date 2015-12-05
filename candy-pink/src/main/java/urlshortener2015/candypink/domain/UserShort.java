package urlshortener2015.candypink.domain;

public class UserShort {
    
	private String username;
	private String shortURL;

	public UserShort(String username, String shortURL) {
		this.username = username;
		this.shortURL = shortURL;
	}

	public String getUsername() {
		return this.username;	
	}
	
	public void setUsername(String username) {
		this.username = username;	
	}

	public String getShortURL() {
		return this.shortURL;
	}
	
	public void setShortURL(String password) {
		this.shortURL = shortURL;	
	}
}
