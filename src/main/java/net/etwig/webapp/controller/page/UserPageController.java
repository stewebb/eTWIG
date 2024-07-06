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
import net.etwig.webapp.services.UserService;
import net.etwig.webapp.services.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpHeaders;

import java.util.Base64;

@Controller
@RequestMapping("/user")
public class UserPageController {

	@Autowired
	private ConfigFile config;

	//@Autowired
	//private LoginSuccessHandler successHandler;

	//@Autowired
	//private UserService userService;

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

	//@Autowired
	//private AuthenticationManager authenticationManager;


	// Include the warning msg into javadoc
	// THIS METHOD HAS POTENTIAL SECURITY RISK AND DO NOT PUT IT IN THE PRODUCTION ENVIRONMENT.

	/**
	 * Performs a login based on a token provided as a URL parameter, validating the referrer header to enhance security.
	 * This method decodes the Base64 encoded token, attempts to deserialize it to a {@link LoginToken}, and initializes a session
	 * if the user's email from the token is verified. The method redirects to the main page upon successful login.
	 * <p>
	 * WARNING: THIS METHOD HAS POTENTIAL SECURITY RISKS AND SHOULD NOT BE PUT IN A PRODUCTION ENVIRONMENT.
	 * Ensure to review and modify the security mechanisms according to the latest standards before deploying.
	 * Use SSO solutions including AzureAD/LDAP/OAUTH/SAML/CAS/OPENID/JWT/... if possible!
	 *
	 * @param token The Base64 encoded login token passed as a URL parameter.
	 * @param request The HttpServletRequest, used to extract the 'Referer' header for validation.
	 * @return ResponseEntity containing either redirect instructions on success or an error message.
	 * @throws IllegalStateException If the user session cannot be initialized.
	 */

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

				// Check user info
				userSessionService.initializeSession(loginToken.getUserEmail());

				// Redirect to mainpage
				HttpHeaders headers = new HttpHeaders();
				headers.add("Location", "/home.do");
				return new ResponseEntity<>(headers, HttpStatus.FOUND);

			}

			// JSON failed to parse or user cannot be found.
			catch (JsonProcessingException | IllegalStateException e) {
				e.printStackTrace();
				return ResponseEntity.status(401).body("Login Failed: Token is invalid or expired.");
			}
		}

		// Otherwise, return 401 Unauthorized.
		else {
			return ResponseEntity.status(401).body("Login Failed: The referrer is not allowed.");
		}

    }
}