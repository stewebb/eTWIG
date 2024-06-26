package net.etwig.webapp.controller.api;

import net.etwig.webapp.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/")
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
}
