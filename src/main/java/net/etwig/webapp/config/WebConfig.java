/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The general configuration for eTWIG platform.
 	*/

package net.grinecraft.etwig.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.CacheControl;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.grinecraft.etwig.EtwigInterceptor;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class WebConfig implements WebMvcConfigurer{

	@Autowired
    private EtwigInterceptor userInterceptor;
    
	/**
	 * Register the Interceptor.
	 * @param registry InterceptorRegistry
	 */
	
    @SuppressWarnings("null")
	@Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor);
    }

	/**
	 * Allow static contents (e.g., CSS and JS) are cached for at most 14 days (i.e., 2 weeks).
	 * @param registry ResourceHandlerRegistry
	 */

	@Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
        	.addResourceLocations("classpath:/static/static/")
            .setCacheControl(CacheControl.maxAge(14, TimeUnit.DAYS));
    }

}
