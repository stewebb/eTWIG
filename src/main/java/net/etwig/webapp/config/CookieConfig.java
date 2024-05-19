/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: Configure the cookies.
 */

package net.etwig.webapp.config;

import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CookieConfig {

	/**
     * Set "same site none secure" to all cookies
     * @doc <a href="https://docs.spring.io/spring-boot/docs/2.6.0/reference/html//web.html#web.servlet.embedded-container.customizing.samesite">...</a>
     * @return
     */
	
	@Bean
    public CookieSameSiteSupplier applicationCookieSameSiteSupplier() {
        return CookieSameSiteSupplier.ofNone().whenHasNameMatching(".*");
    }
}
