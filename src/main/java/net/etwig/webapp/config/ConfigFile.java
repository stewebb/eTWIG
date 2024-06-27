/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: Get the values from the configuration file.
 	*/

package net.etwig.webapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.Data;

/**
 * Configuration class for loading application-specific settings from properties files.
 * This class uses Spring's {@link Value} annotation to inject property values defined in
 * the application's configuration files (e.g., application.properties or application.yml).
 * It implements {@link WebMvcConfigurer} allowing further customization of MVC configurations.
 */

@Data
@Configuration
public class ConfigFile implements WebMvcConfigurer{

	/**
	 * The application name as specified in the configuration properties.
	 * This value is typically used in UI elements or logging.
	 */

	@Value("${etwig.app.name}")
	private String appName;

	/**
	 * The owner of the application. This could be an individual's name, a company name, or
	 * any other type of administrative ownership designation.
	 */

	@Value("${etwig.app.owner}")
	private String appOwner;

	/**
	 * The root location where media assets are stored. This path is used to configure
	 * where the application should look for its media files, such as images, videos, etc.
	 */

	@Value("${etwig.media.root-location}")
	private String rootLocation;

	/**
	 * A security key used for cookie operations. Altering this value will invalidate
	 * all existing cookies, which might be a necessary action to enforce new security measures.
	 */

	@Value("${etwig.security.cookie-key}")
	private String cookieKey;

	/**
	 * The display name used in outgoing mails sent by the application.
	 * This could appear as the sender's name in email clients.
	 */

	@Value("${etwig.mail.display-name}")
	private String displayName;

	/**
	 * The URL where the application is hosted. This might be used internally for
	 * generating absolute URLs for emails, link generation, and redirects within the application.
	 */

	@Value("${etwig.app.url}")
	private String appURL;
}
