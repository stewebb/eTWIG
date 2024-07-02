package net.etwig.webapp.controller.api;

import net.etwig.webapp.dto.graphics.BannerRequestDetailsDTO;
import net.etwig.webapp.services.UserRoleService;
import net.etwig.webapp.services.UserService;
import net.etwig.webapp.services.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/user/")
public class UserAPIController {
	
	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private UserSessionService userSessionService;

	@Autowired
	private UserService userService;

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

	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> list(
			@RequestParam(name = "portfolioId", required = false) Long portfolioId,
			@RequestParam(name = "roleId",required = false) String roleId,
			@RequestParam("start") int start,
			@RequestParam("length") int length,
			@RequestParam("draw") int draw,
			@RequestParam("sortColumn") String sortColumn,
			@RequestParam("sortDirection") String sortDirection
	) {

		Sort.Direction dir = "asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
		PageRequest pageable = PageRequest.of(start / length, length, Sort.by(dir, sortColumn));

		// Get data as pages
		Page<BannerRequestDetailsDTO> page = bannerRequestService.findRequestsByCriteria(eventId, isApproved, pageable);

		Map<String, Object> json = new HashMap<>();
		json.put("draw", draw);
		json.put("recordsTotal", page.getTotalElements());
		json.put("recordsFiltered", page.getTotalElements());
		json.put("data", page.getContent());
		return ResponseEntity.ok(json);
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
