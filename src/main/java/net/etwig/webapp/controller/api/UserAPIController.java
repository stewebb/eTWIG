package net.etwig.webapp.controller.api;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.etwig.webapp.dto.PositionDTO;
import net.etwig.webapp.dto.PositionWithoutEmailDTO;
import net.etwig.webapp.model.User;
import net.etwig.webapp.services.UserRoleService;
import net.etwig.webapp.util.WebReturn;

@RestController
@RequestMapping(value = "/nsRest/private/")
public class UserAPIController {
	
	@Autowired
	private UserRoleService userRoleService;
	
	//@Autowired
    //private PasswordEncoder passwordEncoder;

	/**
	 * Add a user
	 * @location /nsRest/private/user/add
	 * @permission TODO
	 */

	@GetMapping("/user/add")
	public Object add(){
		// TODO add user
		return null;
	}

	/**
	 * Edit a user
	 * @location /nsRest/private/user/edit
	 * @permission TODO
	 */

	@GetMapping("/user/edit")
	public Object edit(){
		// TODO edit user
		return null;
	}

	/**
	 * View a user
	 * @location /nsRest/private/user/view
	 * @permission TODO
	 */

	@GetMapping("/user/view")
	public Object view(){
		// TODO view user
		return null;
	}

	/**
	 * Remove a user
	 * @location /nsRest/private/user/remove
	 * @permission TODO
	 */

	@GetMapping("/user/remove")
	public Object remove(){
		// TODO remove user
		return null;
	}


	/*
	@GetMapping("/getMyPositions")
    public Set<PositionWithoutEmailDTO> getMyPositions() throws Exception {
		return userRoleService.getMyPositions();
	}
	
	@PostMapping("/changeMyPassword")
    public Map<String, Object> changeMyPassword(@RequestBody Map<String, Object> passwordInfo) {
		
		// Get the user and password info
		User currentUser = userRoleService.getMyDetails();
		String currentPassword = passwordInfo.get("currentPassword").toString();
		String newPassword = passwordInfo.get("newPassword").toString();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
        if (!encoder.matches(currentPassword, currentUser.getPassword())) {
        	return WebReturn.errorMsg("You current password is incorrect.", false);
        }

        userRoleService.changePassword(currentUser, newPassword);
        return WebReturn.errorMsg(null, true);
    }

	 */
}
