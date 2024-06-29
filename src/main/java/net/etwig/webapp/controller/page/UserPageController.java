/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The controller for user authentication - mainly login and logout.
	*/

package net.etwig.webapp.controller.page;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.etwig.webapp.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Base64;

@Controller
@RequestMapping("/user")
public class UserPageController {

	@Autowired
	private LoginSuccessHandler successHandler;

    /**
	 * Handles the root GET request and redirects to the index page.
	 * This method serves as a simple redirection point at the root URL ("/"). It is intended to
	 * redirect all logged-in users to the main index page of the user section upon accessing the base URL.
	 * <p>
	 * The use of redirection ensures that users are guided to a default or start page,
	 * enhancing the navigational flow of the web application.
	 *
	 * @return a redirection string that points to the index page located at "/user/index.do".
	 *         The redirection is handled internally by the framework to forward the user to the appropriate destination.
	 * @location /user/
	 * @permission All logged in users
	 */

	@GetMapping("/")
	public String root(){
		return "redirect:/user/index.do";
	}

    /**
	 * Handles the GET request for the user login page.
	 * This method maps the "/login.do" endpoint to the login view, facilitating user access to the login form.
	 * It directs the request to the login page template, which is part of the user authentication flow.
	 * <p>
	 * This straightforward approach is typical for login endpoints where the primary task is to show a form
	 * for user credentials. This method does not handle the login logic itself, which is typically managed
	 * by a separate POST request handler that processes the form submission.
	 *
	 * @return a string indicating the path to the view template for the login page,
	 *         which is resolved by the framework's view resolver to render the actual HTML page.
	 * @location /user/login.do
	 * @permission All users, including visitors
	 */

    @GetMapping("/login.do")
    public String login() {
		return "user/login";
    }

    /**
	 * Handles the GET request for the user profile page.
	 * This method is mapped to serve the profile view when the "/index.do" endpoint is accessed.
	 * It primarily directs the user to the profile page of the application.
	 * <p>
	 * The simplicity of this method suggests it might be used as part of a larger flow where
	 * user profile information is automatically loaded through other means (e.g., session data, interceptors),
	 * or it's used to handle a straightforward redirection to a view template without additional data processing.
	 *
	 * @return a string that represents the path to the view template for the user profile,
	 *         allowing the framework's view resolver to render the profile page.
	 * @location /user/index.do
	 * @permission All logged in users
	 */

	@GetMapping("/index.do")
    public String profile() {
		return "user/profile";
    }

	/**
	 * The logout page.
	 * @location /user/logout.do
	 * @permission All logged in users
	 */

	@GetMapping("/logout.do")
	public String logout() {
		//return "user/profile";
		// TODO Logout page
		return null;
	}

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/pLogin.do")
	public ResponseEntity<?> pLogin(@RequestParam String token, HttpServletRequest request, HttpServletResponse response) {

		byte[] decodedBytes = Base64.getDecoder().decode(token);
		System.out.println(new String(decodedBytes));
		/*
		try {
			// Create an authentication token
			Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
					username, null);

			// Authenticate the token using the custom provider
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			successHandler.onAuthenticationSuccess(request, response, authentication);

			// If authentication is successful, you might want to create a JWT token or similar here
			return ResponseEntity.ok().body("User authenticated successfully");
		} catch (AuthenticationException e) {
			return ResponseEntity.status(401).body("Authentication failed: " + e.getMessage());
		} catch (IOException e) {
            throw new RuntimeException(e);
        }

		 */

		return ResponseEntity.ok().body("Body");
    }
}