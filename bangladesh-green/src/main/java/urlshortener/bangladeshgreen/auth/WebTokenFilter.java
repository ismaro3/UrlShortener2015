package urlshortener.bangladeshgreen.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.filter.GenericFilterBean;
import urlshortener.bangladeshgreen.domain.messages.ErrorResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


/**
 * This filter checks if authorization header is present in an HTTP request.
 * If so, checks if the JSON Web Token (JWT) is correct and not expired.
 * If there is an error, an error message is returned. Else, the filter chain continues.
 * NOTE: Only executed for protected paths (View Application.java)
 */
@Configurable
public class WebTokenFilter extends GenericFilterBean {

    private String key;

    private ArrayList<URLProtection> toProtect;

    /**
     * Constructor of servlet filter.
     * @param key is the secret key for signing.
     */
    public WebTokenFilter(String key){
        this.key = key;
        toProtect = new ArrayList<>();
    }

    public void addUrlToProtect(URLProtection p){
        this.toProtect.add(p);
    }





    @Override
    public void doFilter(final ServletRequest req,
                         final ServletResponse res,
                         final FilterChain chain) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) req; //Request
        final HttpServletResponse response  = (HttpServletResponse) res; //Response
        final String authHeader = request.getHeader("Authorization"); //Authorization header




        if(requiresAuthentication(request)){

            //Requires authentication
            System.out.println("Requires authentication");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                //No authentication in the request
                sendErrorResponse(response,
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Authorization error: No token is supplied. Please obtain one from /login.");
            }

            else{
                //Authentication in the request
                final String token = extractToken(authHeader);
                try {
                    //Parse claims from JWT
                    final Claims claims = Jwts.parser().setSigningKey(key)
                            .parseClaimsJws(token).getBody();

                        //Correct token -> User is logged-in
                        request.setAttribute("claims",claims);
                        chain.doFilter(req, res); //Continue with filters

                }
                catch(ExpiredJwtException expiredException){
                    sendErrorResponse(response,
                            HttpServletResponse.SC_UNAUTHORIZED,
                            "Authorization error: " + expiredException.getMessage());
                }
                catch (final SignatureException  | NullPointerException  |MalformedJwtException ex) {
                    sendErrorResponse(response,
                            HttpServletResponse.SC_UNAUTHORIZED,
                            "Authorization error: Invalid token format. Please obtain a new token from /login");
                }


            }
        }
        else{
            //Does not require authentication
            System.out.println("Does not require authentication");
            chain.doFilter(req,res);
        }

    }



    /**
     * Returns true if the URL requires authentication.
     */
    public boolean requiresAuthentication(HttpServletRequest request){
        String destinationURL = request.getRequestURI();
        System.out.println("Checking for " + destinationURL);
        System.out.println(request.getMethod());

        //Check every URL to protect
        for(URLProtection url: toProtect){

            System.out.println(url.getUrl());
            if(url.getUrl().equals(destinationURL)){ //A filter has been found for that URL
                //Check method
                System.out.println("Check");
                if(url.hasMethod(request.getMethod())){
                    //It has a method that needs to be authenticated
                    return true;
                }
            }
        }
        return false;
    }

    public String extractToken(String authHeader){
        return authHeader.substring(7);

    }
    /**
     * Writes to the response an error message.
     */
    private void sendErrorResponse(HttpServletResponse response,int code,String message) throws IOException{

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(message);
        ObjectMapper mapper = new ObjectMapper();

        response.setStatus(code);
        response.setContentType("application/json");
        response.getOutputStream().println(mapper.writeValueAsString(errorResponse));

    }

}
