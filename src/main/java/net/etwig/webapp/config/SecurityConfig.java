/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The security configuration for eTWIG platform.
 	*/

package net.etwig.webapp.config;

import net.etwig.webapp.util.Endpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import net.etwig.webapp.handler.CustomAuthenticationEntryPoint;
import net.etwig.webapp.handler.LoginSuccessHandler;
import net.etwig.webapp.services.RememberMeService;
import net.etwig.webapp.services.UserRoleService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)

/**
 * Configuration class for web security, defining the security constraints and behaviors
 * for various types of HTTP requests within the application. It uses Spring Security
 * to enforce authentication and authorization policies.
 *
 * <p>Key configurations include:</p>
 * <ul>
 *     <li>Definition of public and secured endpoints.</li>
 *     <li>Form login and logout configurations.</li>
 *     <li>Remember Me functionality setup.</li>
 *     <li>Custom handling of authentication entry points for unauthenticated requests.</li>
 *     <li>Security enhancements such as HTTPS enforcement and headers configuration.</li>
 * </ul>
 */

public class SecurityConfig {

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private LoginSuccessHandler loginSuccessHandler;

	@Autowired
	private RememberMeService rememberMeService;

	@Autowired
	private Endpoints endpoints;

	//@Autowired
	//private CustomAuthenticationProvider customAuthenticationProvider; // TODO: Inject custom authentication provider

	// Array of paths that should be accessible publicly without authentication.
	private final String[] publicPages = {
			endpoints.getHOME(),

			"/static/**",
			"/api/public/**",
			"/twig/**",
			endpoints.getASSETS_CONTENT(),
			endpoints.getUSER_REFERRER_LOGIN()
	};

	/**
	 * Configures the security filter chain that handles HTTP requests to enforce security constraints.
	 * @param http the {@link HttpSecurity} to modify
	 * @return the configured {@link SecurityFilterChain}
	 * @throws Exception if an error occurs during the configuration
	 */

	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		// Permit all requests to public pages while securing all other requests.
		http.authorizeHttpRequests((requests) -> requests
				.requestMatchers(this.publicPages).permitAll()
				.anyRequest().authenticated()
		);

		// Configuration for the form login.
		http.formLogin((form) -> form
				.loginPage(endpoints.getUSER_LOGIN())
				.loginProcessingUrl("/user/login")
				.permitAll()
				.failureUrl("/user/login.do?success=false")
				.successHandler(loginSuccessHandler)
		);

		// Configuration for the "remember me" functionality.
		http.rememberMe().rememberMeServices(rememberMeService);

		// Configuration for the logout process.
		http.logout((logout) -> logout.logoutUrl("/user/logout"));

		// Disable CSRF to avoid conflicts with stateless APIs or client-side technologies.
		http.csrf().disable();

		// Allow all frame origins and disable frame options for clickjacking protection.
		http.headers()
				.frameOptions().disable()
				.contentSecurityPolicy("frame-ancestors 'self' https:;");

		// Enforce HTTPS to secure channel communications.
		http.requiresChannel((channel) -> channel.anyRequest().requiresSecure());

		// Exception handling to redirect to the custom entry point when authentication is needed.
		http.exceptionHandling((exception) -> exception
				.authenticationEntryPoint(authenticationEntryPoint())
		);

		return http.build();
	}

	/**
	 * Configures the {@link AuthenticationManagerBuilder} with authentication providers.
	 * <p>
	 * This method sets up the standard {@link DaoAuthenticationProvider} with a {@link UserDetailsService}
	 * and a {@link PasswordEncoder}, and adds a custom authentication provider.
	 * </p>
	 *
	 * @param auth the {@link AuthenticationManagerBuilder} to configure.
     */

	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) {

		// Configure the DaoAuthenticationProvider with UserDetailsService and PasswordEncoder
		DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
		daoAuthProvider.setUserDetailsService(userRoleService);
		daoAuthProvider.setPasswordEncoder(passwordEncoder());
		auth.authenticationProvider(daoAuthProvider); // Standard authentication provider

		// Add custom AuthenticationProvider
		//auth.authenticationProvider(customAuthenticationProvider);
	}

	/**
	 * Provides a BCrypt password encoder for hashing passwords securely.
	 * @return an instance of {@link BCryptPasswordEncoder}
	 */

	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Defines a custom authentication entry point to start the authentication process when unauthenticated requests are made to secured resources.
	 * @return an instance of {@link AuthenticationEntryPoint}
	 */

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new CustomAuthenticationEntryPoint();
	}

	//@Bean
	//public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	//	return authenticationConfiguration.getAuthenticationManager();
	//}
}