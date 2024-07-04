package net.etwig.webapp.controller.api;

import net.etwig.webapp.dto.admin.UserListDTO;
import net.etwig.webapp.services.UserRoleService;
import net.etwig.webapp.services.UserService;
import net.etwig.webapp.services.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	 * Handles the HTTP POST request to add a new user to the system.
	 * <p>
	 * This endpoint is accessible only to site administrators. It calls the {@code addUser} method from
	 * the {@code userService} to attempt adding a new user with the information provided in {@code newUserInfo}.
	 * If a user with the same email already exists, the method will return {@code false}, indicating that the user
	 * was not added. Otherwise, it returns {@code true}, indicating successful addition of the user.
	 * </p>
	 *
	 * @param newUserInfo A {@link Map} containing the necessary user information. It should include:
	 *                    <ul>
	 *                      <li>userFullName: The full name of the user.</li>
	 *                      <li>userEmail: The email address of the user, which is used as a unique identifier.</li>
	 *                      <li>userPassword: The password for the user, which will be encoded before storage.</li>
	 *                      <li>userSystemRole: The role ID, indicating the user's level of access.</li>
	 *                      <li>userPortfolio: The portfolio ID associated with the user.</li>
	 *                      <li>userPosition: The position or title of the user within the organization.</li>
	 *                    </ul>
	 * @return {@code Boolean} indicating success ({@code true}) or failure ({@code false}) of adding the user.
	 * @location /api/user/add
	 * @permission Required: Site administrators only.
	 */

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/add")
	public Boolean add(@RequestBody Map<String, Object> newUserInfo){
		return userService.addUser(newUserInfo);
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
	 * Retrieves a paginated list of users with positions, optionally filtered by portfolio ID and role ID, and
	 * supports sorting and searching.
	 *
	 * <p>This method handles HTTP GET requests to the "/list" endpoint. It requires the caller to have the
	 * "ROLE_ADMIN" authority.
	 *
	 * @param portfolioId the ID of the portfolio to filter users by (optional).
	 * @param roleId the ID of the role to filter users by (optional).
	 * @param start the starting index of the records to be fetched.
	 * @param length the number of records to be fetched.
	 * @param draw the draw counter that this object is a response to.
	 * @param sortColumn the column to sort the results by.
	 * @param sortDirection the direction to sort the results (either "asc" for ascending or "desc" for descending).
	 * @param searchValue the value to search for in the user data (optional).
	 * @return a {@link ResponseEntity} containing a map with the pagination and user data.
	 * @location /api/user/list
	 * @permission Site administrators only.
	 */

	@GetMapping("/list")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Map<String, Object>> list(
			@RequestParam(name = "portfolioId", required = false) Long portfolioId,
			@RequestParam(name = "roleId",required = false) String roleId,
			@RequestParam("start") int start,
			@RequestParam("length") int length,
			@RequestParam("draw") int draw,
			@RequestParam("sortColumn") String sortColumn,
			@RequestParam("sortDirection") String sortDirection,
			@RequestParam(name = "search[value]", required = false) String searchValue
	) {

		if ("username".equalsIgnoreCase(sortColumn)) {
			sortColumn = "user.fullName";
		}

		Sort.Direction dir = "asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
		PageRequest pageable = PageRequest.of(start / length, length, Sort.by(dir, sortColumn));

		// Get data as pages
		Page<UserListDTO> page = userService.getAllUsersWithPositions(portfolioId, roleId, searchValue, pageable);

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
	 * @location /api/user/changepwd
	 * @permission All logged-in users.
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
