package urlshortener2015.candypink.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import urlshortener2015.candypink.repository.ShortURLRepository;
import urlshortener2015.candypink.repository.ShortURLRepositoryImpl;

import urlshortener2015.candypink.repository.UserRepository;
import urlshortener2015.candypink.repository.UserRepositoryImpl;

@Configuration
public class PersistenceContext {

	@Autowired
    	protected JdbcTemplate jdbc;

	@Bean
	ShortURLRepository shortURLRepository() {
		return new ShortURLRepositoryImpl(jdbc);
	}
	
	@Bean
	UserRepository userRepository() {
		return new UserRepositoryImpl(jdbc);	
	}
	
}
