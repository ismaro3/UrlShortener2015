package urlshortener2015.candypink.domain;

public class User {
    
	private String username;
	private String password;
	private String email;
	private String role;
	private String name;

	public User(String username, String password, String email, String role, String name) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.name = name;
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
	
	public String getEmail() {
		return this.email;	
	}
	
	public void setEmail(String email) {
		this.email = email;	
	}

	public String getRole() {
		return this.role;
	}
	
	public void setRole(String role) {
		this.role = role;	
	}
	
	public String getName() {
		return this.name;	
	}
	
	public void setName(String name) {
		this.name = name;	
	}
}