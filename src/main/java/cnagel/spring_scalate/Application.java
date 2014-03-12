
package cnagel.spring_scalate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * The standalone application running an own Server.
 * 
 * @author cnagel
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("cnagel.spring_scalate")
public class Application {

	public static void main(String... args) {
		SpringApplication.run(Application.class, args);
	}

}
