package urlshortener2015.candypink.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	protected JdbcTemplate jdbc;

 	@Autowired
 	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
   		auth.jdbcAuthentication().dataSource(jdbc.getDataSource())
  			.usersByUsernameQuery(
  	 		"select username,password from users where username=? and role!='ADMIN'")
  			.authoritiesByUsernameQuery(
   			"select username, role from users where username=? and role='ADMIN'");
 	} 

 	@Override
 	protected void configure(HttpSecurity http) throws Exception {
   		http.csrf().disable()
			.authorizeRequests()
				.antMatchers("/",  "/webjars/**", "/css/**", "/images/**", "/js/**").permitAll()
				.antMatchers(HttpMethod.POST, "/admin").permitAll()
				.antMatchers(HttpMethod.POST, "/register").permitAll()
				.antMatchers("/fileUpload").authenticated()
				.and()
				.formLogin()
					.loginPage("/")
					.loginProcessingUrl("/login").permitAll()
			.and()
			.logout().permitAll();
 	}
}
