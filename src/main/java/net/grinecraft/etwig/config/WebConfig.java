/**
	 * eTWIG - The event management software for university communities.
	 * @copyright: Copyright (c) 2024 Steven Webb
 * @license: MIT
 * @author: Steven Webb [xiaoancloud@outlook.com]
 * @website: https://etwig.grinecraft.net
 * @function: The general configuration for eTWIG platform.
 */

package net.grinecraft.etwig.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.grinecraft.etwig.EtwigInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	@Autowired
    private EtwigInterceptor userInterceptor;
	
    @Value("${etwig.media.root-location}")
    private String rootLocation;

    @Value("${etwig.security.cookie-key}")
    private String cookieKey;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor);
    }

    public String getRootLocation() {
        return rootLocation;
    }
    
    public String getCookieKey() {
    	return cookieKey;
    }
}
