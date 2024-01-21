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
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.grinecraft.etwig.EtwigInterceptor;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class WebConfig implements WebMvcConfigurer{

	@Autowired
    private EtwigInterceptor userInterceptor;
    
	/**
	 * Register the Interceptor
	 * @param registry
	 */
	
    @SuppressWarnings("null")
	@Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor);
    }
    
    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/static/**")
        //	.addResourceLocations("classpath:/static/static/")
        //    .setCacheControl(CacheControl.maxAge(90, TimeUnit.DAYS));
    }

}
