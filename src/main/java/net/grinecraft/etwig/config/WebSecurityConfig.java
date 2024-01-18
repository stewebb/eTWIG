/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
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
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import net.grinecraft.etwig.handler.CustomAuthenticationEntryPoint;
import net.grinecraft.etwig.handler.LoginSuccessHandler;
import net.grinecraft.etwig.services.RememberMeService;
import net.grinecraft.etwig.services.UserRoleService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig{

	@Autowired
    private UserRoleService userRoleService;
	
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	
	@Autowired
	private RememberMeService rememberMeService;
	
	private String[] publicUrls = {
		"/static/**", 
		"/api/public/**",
		"/twig/**", 
		"/error", 
		"/assets/getPublicAsset"
	};
	
	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
          
		// Public access resources.
		http.authorizeHttpRequests((requests) -> requests
				.requestMatchers(this.publicUrls).permitAll()
				.anyRequest().authenticated()
			);
		
		// Login page.
		http.formLogin((form) -> form
				.loginPage("/user/login")
	            .loginProcessingUrl("/user/login")
				.permitAll()
				.failureUrl("/user/login?success=false")
				.successHandler(loginSuccessHandler)
			);
		
		// Remember Me
		http.rememberMe().rememberMeServices(rememberMeService);		
		
		// Logout URL.
		http.logout((logout) -> logout.logoutUrl("/user/logout"));
		
		// Disable CSRF.
		http.csrf().disable();
		
		// Allow frames from any origin
		http.headers()
        	.frameOptions().disable()
            .contentSecurityPolicy("frame-ancestors 'self' https:;");
		
		// Force enable HTTPS. (or some browsers may block the website if it's in a frame)
        http.requiresChannel(
        		(channel) -> channel.anyRequest().requiresSecure()
        );
        
        http.exceptionHandling((exception)-> exception.
        		authenticationEntryPoint(authenticationEntryPoint())
        );
        
        //http
        //	.exceptionHandling()
        //	.authenticationEntryPoint(authenticationEntryPoint())
        
		return http.build();
	}

	/**
	 * Set up a password encoder.
	 * @param auth
	 * @throws Exception
	 */
	
    @Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(userRoleService).passwordEncoder(passwordEncoder());
    }

   /**
    * Use BCrypt to encrypt the password.
    * @return BCryptPasswordEncoder;
    */
    
    public BCryptPasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }
}