package net.etwig.webapp.controller.api;

import net.etwig.webapp.model.User;
import net.etwig.webapp.repository.UserRepository;
import net.etwig.webapp.services.UserRoleService;
import net.etwig.webapp.services.UserSessionService;
import net.etwig.webapp.util.InvalidParameterException;
import net.etwig.webapp.util.WebReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

	@PostMapping("/changePwd")
	public boolean changePwd (
			@RequestParam("currentPassword") String currentPassword,
			@RequestParam("newPassword") String newPassword
	){

		// TODO Admin change user's password

		Long userId =  userSessionService.validateSession().getBasicInfo().getId();

		// User check
		//Optional<User> user = userRepository.findById(userId);
		//if(user.isEmpty()) {
		//	throw new InvalidParameterException("User with id=" + userId + "does not exist.");
		//}

		// Original password check
		//BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		//if (!encoder.matches(currentPassword, user.get().getPassword())) {
			//return WebReturn.errorMsg("You current password is incorrect.", false);
		//}


		//User currentUser = userRoleService.getMyDetails();
		//String currentPassword = passwordInfo.get("currentPassword").toString();
		//String newPassword = passwordInfo.get("newPassword").toString();
		//BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		//if (!encoder.matches(currentPassword, currentUser.getPassword())) {
		//	//return WebReturn.errorMsg("You current password is incorrect.", false);
		//}

		return userRoleService.changePassword(userId, currentPassword, newPassword);
	}
}
