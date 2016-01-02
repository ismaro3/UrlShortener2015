package urlshortener2015.candypink;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by david on 2/01/16.
 */
@Configuration
@ComponentScan("checker")
@Import(checker.WebServiceConfig.class)
public class ApplicationConfig {

}
