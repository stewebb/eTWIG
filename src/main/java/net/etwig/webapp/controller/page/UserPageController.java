/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The controller for user authentication - mainly login and logout.
	*/

package net.etwig.webapp.controller.page;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import net.etwig.webapp.config.ConfigFile;
import net.etwig.webapp.dto.LoginToken;
import net.etwig.webapp.handler.LoginSuccessHandler;
import net.etwig.webapp.model.User;
import net.etwig.webapp.services.UserService;
import net.etwig.webapp.services.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("/user")
public class UserPageController {

	@Autowired
	private ConfigFile config;

	@Autowired
	private LoginSuccessHandler successHandler;

	@Autowired
	private UserService userService;

	@Autowired
	private UserSessionService userSessionService;

	/**
	 * Handles the root GET request and redirects to the profile page.
	 * This method serves as a simple redirection point at the root URL ("/"). It is intended to
	 * redirect all logged-in users to the main profile page of the user section upon accessing the base URL.
	 * <p>
	 * The use of redirection ensures that users are guided to a default or start page,
	 * enhancing the navigational flow of the web application.
	 *
	 * @return a redirection string that points to the profile page located at "/user/profile.do".
	 *         The redirection is handled internally by the framework to forward the user to the appropriate destination.
	 * @location /user/
	 * @permission All logged-in users
	 */

	@GetMapping("/")
	public String root(){
		return "redirect:/user/profile.do";
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
	 * This method is mapped to serve the profile view when the "/profile.do" endpoint is accessed.
	 * It primarily directs the user to the profile page of the application.
	 * <p>
	 * The simplicity of this method suggests it might be used as part of a larger flow where
	 * user profile information is automatically loaded through other means (e.g., session data, interceptors),
	 * or it's used to handle a straightforward redirection to a view template without additional data processing.
	 * </p>
	 *
	 * @return a string that represents the path to the view template for the user profile,
	 *         allowing the framework's view resolver to render the profile page.
	 * @location /user/profile.do
	 * @permission All logged-in users
	 */

	@GetMapping("/profile.do")
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


	@GetMapping("/referrerLogin.do")
	public ResponseEntity<?> referrerLogin(@RequestParam String token, HttpServletRequest request) {

		// Only allow a specific referrer
		String referrer = request.getHeader("Referer");
		if (referrer != null && referrer.startsWith(config.getTrustedReferrer())) {

			// Decode encoded String
			byte[] decodedBytes = Base64.getDecoder().decode(token);
			String decodedStr = new String(decodedBytes);

			try {
				ObjectMapper objectMapper = new ObjectMapper();
				LoginToken loginToken = objectMapper.readValue(decodedStr, LoginToken.class);

				//User user = userService.findByEmail(loginToken.getUserEmail());
				// Get user object
				userSessionService.initializeSession(loginToken.getUserEmail());

				String url = "/home.do";

				HttpHeaders headers = new HttpHeaders();
				headers.add("Location", url);
				// Optionally you can use ResponseEntity.created(URI) for 201 status codes
				return new ResponseEntity<>(headers, HttpStatus.FOUND);

			} catch (JsonProcessingException | IllegalStateException e) {
				e.printStackTrace();
				return ResponseEntity.status(401).body("Login Failed: Token is invalid or expired.");
			}
            //userService.checkByEmail(email);
			/*
			// Decode encoded String
			byte[] decodedBytes = Base64.getDecoder().decode(token);
			String decodedStr = new String(decodedBytes);
			//System.out.println(decodedStr);

			try {

				// Parse the JSON object from the decoded String.
				ObjectMapper objectMapper = new ObjectMapper();
				LoginToken loginToken = objectMapper.readValue(decodedStr, LoginToken.class);
				//System.out.println(loginToken);

				// Token expiration check
				long currentTimestamp = Instant.now().getEpochSecond();
				long timeDifference = currentTimestamp - loginToken.getTimestamp();
				System.out.println(timeDifference);
				if (timeDifference < 0 || timeDifference > 60) {
					System.out.println("Token has expired.");
					//throw new IllegalStateException("Token has expired.");
				}

				Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
						loginToken.getUserEmail(), null
				);

				Authentication authentication = authenticationManager.authenticate(authenticationToken);
				successHandler.onAuthenticationSuccess(request, response, authentication);

			} catch (JsonProcessingException | IllegalStateException | AuthenticationException e) {
				e.printStackTrace();
				return ResponseEntity.status(401).body("Login Failed: Token is invalid or expired.");
			} catch (IOException e) {
                throw new RuntimeException(e);
            }

            return ResponseEntity.ok().body("Login Successful.");

			 */
		}

		// Otherwise, return 401 Unauthorized.
		else {
			return ResponseEntity.status(401).body("Login Failed: The referrer is not allowed.");
		}

    }
}