/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The main start point of the eTWIG platform.
 */

package net.etwig.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * The entry point of the Etwig Spring Boot application.
 * This class serves as the bootstrap for launching a Spring application. It configures the Spring application context,
 * setting up the entire application's configuration and dependencies.
 * <p>
 * The class is annotated with {@link SpringBootApplication}, which is a convenience annotation that adds:
 * - {@link Configuration}: Tags the class as a source of bean definitions for the application context.
 * - {@link EnableAutoConfiguration}: Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
 * - {@link ComponentScan}: Tells Spring to look for other components, configurations, and services in the current package, allowing it to find controllers, services, etc.
 */

@SpringBootApplication
public class EtwigApplication {

	/**
	 * Main method which serves as the entry point for the Spring Boot application.
	 * This method uses SpringApplication.run to launch the application which starts the embedded server, initializes Spring application context,
	 * and prepares the application for handling HTTP requests.
	 *
	 * @param args The command line arguments passed to the application, which can be used to configure aspects of the application at runtime.
	 */

	public static void main(String[] args) {
		SpringApplication.run(EtwigApplication.class, args);
	}

	/**
	 * Configures the Spring application builder for the servlet environment. This method is typically used when the application
	 * needs to be configured as a servlet initializer.
	 * <p>
	 * This method customizes the SpringApplicationBuilder for this application's needs, explicitly registering the primary configuration
	 * class of the application, which is {@code EtwigApplication.class} itself here.
	 *
	 * @param builder The SpringApplicationBuilder that is used to configure and bootstrap the application in a servlet container.
	 * @return The SpringApplicationBuilder with the sources set, ready for further configuration and usage in servlet initialization.
	 */

    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(EtwigApplication.class);
    }

}
