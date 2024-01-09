/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The general configuration for eTWIG platform.
 	*/

package net.grinecraft.etwig.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.Data;

@Data
@Configuration
public class ConfigFile implements WebMvcConfigurer{

	/**
	 * The application name of this site.
	 */
	
	@Value("${etwig.app.name}")
    private String appName;
	
	/**
	 * The owner of the application.
	 */
	
	@Value("${etwig.app.owner}")
    private String appOwner;
	
    @Value("${etwig.media.root-location}")
    private String rootLocation;

    @Value("${etwig.security.cookie-key}")
    private String cookieKey;

}
