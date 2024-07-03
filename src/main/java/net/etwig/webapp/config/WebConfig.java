/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The general configuration for eTWIG platform.
 	*/

package net.etwig.webapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.CacheControl;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.etwig.webapp.EtwigInterceptor;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class WebConfig implements WebMvcConfigurer{

	@Autowired
    private EtwigInterceptor userInterceptor;

	/**
	 * Registers an interceptor to the application's interceptor registry. This is typically used to perform operations
	 * like logging, authentication, or other pre- and post-request activities common in web applications.
	 *
	 * @param registry The InterceptorRegistry to which the interceptor will be added. This is managed by Spring
	 *                 and allows for the configuration of interceptors that can intercept HTTP requests dynamically.
	 */
	
    //@SuppressWarnings("null")
	@Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor);
    }

	/**
	 * Configures resource handlers to enhance the serving of static contents such as CSS and JavaScript files.
	 * This setup specifies caching policies for static resources, which helps in reducing load times and bandwidth usage.
	 *
	 * @param registry The ResourceHandlerRegistry used to manage resource handlers. This allows for custom handling,
	 *                 such as setting cache periods or specifying resource locations within the classpath.
	 * @description Adds a resource handler for serving static resources located under "/static/**" path and applies
	 *              a caching policy that allows these resources to be cached for up to 14 days, aiding in better
	 *              performance and reduced server load.
	 */

	@Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
        	.addResourceLocations("classpath:/static/static/")
            .setCacheControl(CacheControl.maxAge(14, TimeUnit.DAYS));
    }

}
