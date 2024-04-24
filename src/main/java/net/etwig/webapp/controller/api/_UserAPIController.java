package net.etwig.webapp.controller.api;

import net.etwig.webapp.dto.PositionWithoutEmailDTO;
import net.etwig.webapp.model.User;
import net.etwig.webapp.services.UserRoleService;
import net.etwig.webapp.util.WebReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/private/")
public class _UserAPIController {
	
	@Autowired
	private UserRoleService userRoleService;
	
	//@Autowired
    //private PasswordEncoder passwordEncoder;
	
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
}
