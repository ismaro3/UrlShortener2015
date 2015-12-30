package urlshortener2015.candypink.domain;

public class User {
    
	private String username;
	private String password;
	private Boolean enabled;
	private String email;
	private String authority;

	public User(String username, String password, Boolean enabled, String email, String authority) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.email = email;
		this.authority = authority;
	}

	public String getUsername() {
		return this.username;	
	}
	
	public void setUsername(String username) {
		this.username = username;	
	}

	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;	
	}
	
	public Boolean getEnabled() {
		return this.enabled;
	}
	
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;	
	}
	public String getEmail() {
		return this.email;	
	}
	
	public void setEmail(String email) {
		this.email = email;	
	}


	public String getAuthority() {
		return this.authority;
	}
	
	public void setAuthority(String authority) {
		this.authority = authority;	
	}
}
