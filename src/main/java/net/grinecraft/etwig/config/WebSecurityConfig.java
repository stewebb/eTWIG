/**
 * eTWIG - The event and banner management software for residential halls and student unions.
 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
 * @license: MIT
 * @author: Steven Webb [xiaoancloud@outlook.com]
 * @website: https://etwig.grinecraft.net
 * @function: The security configuration for eTWIG platform.
 */

package net.grinecraft.etwig.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import net.grinecraft.etwig.LoginSuccessHandler;
import net.grinecraft.etwig.services.RememberMeService;
import net.grinecraft.etwig.services.UserAuthService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{

	@Autowired
    private UserAuthService userAuthService;
	
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	
	@Autowired
	private RememberMeService rememberMeService;
	
	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
		//http
        //	.sessionManagement(session -> session.
        //			sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        //	);
          
		// Set the public access resources.
		http.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/static/**", "/api/public/**", "/twig/**", "/error").permitAll()
				.anyRequest().authenticated()
			);
		
		// Set the login page.
		http.formLogin((form) -> form
				.loginPage("/user/login")
	            .loginProcessingUrl("/user/login")
				.permitAll()
				.failureUrl("/user/login?success=false")
				.successHandler(loginSuccessHandler)
			);
		
		// Remember Me for 90 days. (60*60*90)
		http.rememberMe().tokenValiditySeconds(7776000).rememberMeServices(rememberMeService);
		
		// Set the logout URL.
		http.logout((logout) -> logout.logoutUrl("/user/logout"));
		
		// Disable CSRF.
		http.csrf().disable();
		
		// Allow frames from any origin
		http.headers()
        	.frameOptions().disable()
            .contentSecurityPolicy("frame-ancestors 'self' https:;");
		
        http.requiresChannel(
        		(channel) -> channel.anyRequest().requiresSecure()
        );
        
		return http.build();
	}

    @Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(userAuthService).passwordEncoder(passwordEncoder());
    }

   /**
    * Use BCrypt to encrypt the password.
    * @return BCryptPasswordEncoder;
    */
    
    public BCryptPasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
}