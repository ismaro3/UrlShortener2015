package urlshortener2015.candypink.auth.support;

import java.io.File;
import java.io.FileNotFoundException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class AuthUtils {

	private static final String FILE_URIS = "";

	public static String createToken(String username, String role, String key, Date expiration) {
		return Jwts.builder().setSubject(username)
            	.claim("role", role).setIssuedAt(new Date()).setExpiration(expiration)
            	.signWith(SignatureAlgorithm.HS256, key).compact();
	 }

	public static AuthURI[] buildAuthURIs() {
		ArrayList<AuthURI> authList = new ArrayList<AuthURI>();
		Scanner scan = null;
		AuthURI[] auth = null;
		try { 
			scan = new Scanner(new File(FILE_URIS));
			scan.useDelimiter(";|,|-");
			while(scan.hasNext()) {
				String uri = scan.next();
				HashMap<String, String> methods = new HashMap<String, String>();
				while(scan.hasNext()) {
					String method = scan.next();
					String permission = scan.next();
					methods.put(method, permission);		
				}
				authList.add(new AuthURI(uri, methods));
				scan.nextLine();
			}
			auth = new AuthURI[authList.size()];
			auth = authList.toArray(auth);
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		finally {
			if (scan != null) {
				scan.close();
			}
		}
		return auth;
	}
}
