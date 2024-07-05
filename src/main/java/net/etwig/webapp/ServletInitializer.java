/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The servlet initializer of the eTWIG platform.
 	*/

package net.etwig.webapp;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Servlet initializer for the Etwig Spring Boot application when deployed as a WAR file in a servlet container.
 * This class extends {@link SpringBootServletInitializer}, which provides the bridge between Spring Boot and the traditional
 * Servlet API. It is particularly useful for applications that need to be deployed in an external servlet container rather than
 * running via an embedded server.
 * <p>
 * The {@code configure} method is overridden to set up the initial configuration for the Spring application context. This setup
 * is critical for initializing and configuring the Spring framework in a servlet-based environment.
 *
 * @see SpringBootServletInitializer
 */

public class ServletInitializer extends SpringBootServletInitializer {

	/**
	 * Configures the Spring application builder for servlet environments by specifying the primary source
	 * for the Spring configuration. This method is called on application startup when deployed as a WAR file.
	 * <p>
	 * This method overrides the default configuration to point to {@link EtwigApplication class}, which contains
	 * all the configuration annotated by SpringBootApplication.
	 *
	 * @param application The {@link SpringApplicationBuilder} to configure.
	 * @return The configured {@code SpringApplicationBuilder} instance, ready for further customization and ultimately
	 *         used to launch the Spring application.
	 */

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(EtwigApplication.class);
	}

}
