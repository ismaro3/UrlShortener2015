package urlshortener2015.candypink.domain;

public class User {
    /* Name of the user */
	private String name;

	/* Type of the user */
	private String type;

	public Person(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return this.name;	
	}

	public String getType() {
		return this.type;
	}
}
