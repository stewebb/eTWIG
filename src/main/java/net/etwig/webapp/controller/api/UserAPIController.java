package net.etwig.webapp.controller.api;

import net.etwig.webapp.repository.UserRepository;
import net.etwig.webapp.services.UserRoleService;
import net.etwig.webapp.services.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/user/")
public class UserAPIController {
	
	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private UserSessionService userSessionService;

	@Autowired
	private UserRepository userRepository;
	
	//@Autowired
    //private PasswordEncoder passwordEncoder;

	/**
	 * Add a user
	 * @location /nsRest/private/user/add
	 * @permission TODO
	 */

	@GetMapping("/add")
	public Object add(){
		// TODO add user
		return null;
	}

	/**
	 * Edit a user
	 * @location /nsRest/private/user/edit
	 * @permission TODO
	 */

	@GetMapping("/edit")
	public Object edit(){
		// TODO edit user
		return null;
	}

	/**
	 * View a user
	 * @location /nsRest/private/user/view
	 * @permission TODO
	 */

	@GetMapping("/view")
	public Object view(){
		// TODO view user
		return null;
	}

	/**
	 * Remove a user
	 * @location /nsRest/private/user/remove
	 * @permission TODO
	 */

	@GetMapping("/remove")
	public Object remove(){
		// TODO remove user
		return null;
	}

	/**
	 * Handles a POST request to change a user's password.
	 * This method accepts a map containing the current and new passwords, validates the user session,
	 * and attempts to update the password.
	 *
	 * <p>Expected keys in the {@code passwordInfo} map:</p>
	 * <ul>
	 *     <li>{@code currentPassword} - The user's current password.</li>
	 *     <li>{@code newPassword} - The new password to be set for the user.</li>
	 * </ul>
	 *
	 * @param passwordInfo A {@link Map} with keys 'currentPassword' and 'newPassword' providing the passwords.
	 * @return {@code true} if the password was successfully changed, {@code false} otherwise.
	 * @throws IllegalArgumentException if the necessary keys are missing in {@code passwordInfo} or if
	 *                                  password change is not allowed for the session user.
	 */

	@PostMapping("/changePwd")
	public boolean changePwd (@RequestBody Map<String, Object> passwordInfo){

		String currentPassword = passwordInfo.get("currentPassword").toString();
		String newPassword = passwordInfo.get("newPassword").toString();

		// TODO Admin change user's password
		Long userId = userSessionService.validateSession().getBasicInfo().getId();
		return userRoleService.changePassword(userId, currentPassword, newPassword);
	}
}
