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
	 * Configures the "SameSite" attribute for all cookies to "None", allowing cookies to be sent in all contexts,
	 * including cross-origin requests where cookies are needed for non-idempotent cross-site requests.
	 * This configuration is particularly useful for applications that require cross-origin access to function properly.
	 *
	 * <p>Note: Cookies with "SameSite=None" must also be "Secure" and sent over HTTPS to be respected by browsers.
	 *
	 * @see  See Spring Boot documentation on cookie properties and SameSite configuration:
	 * <a href="https://docs.spring.io/spring-boot/docs/2.6.0/reference/html//web.html#web.servlet.embedded-container.customizing.samesite">Spring Boot SameSite</a>
	 *
	 * @return CookieSameSiteSupplier configured to apply "SameSite=None" to all cookies.
	 */
	
	@Bean
    public CookieSameSiteSupplier applicationCookieSameSiteSupplier() {
        return CookieSameSiteSupplier.ofNone().whenHasNameMatching(".*");
    }
}
