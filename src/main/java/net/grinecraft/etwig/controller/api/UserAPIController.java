package net.grinecraft.etwig.controller.api;

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

import net.grinecraft.etwig.dto.PositionDTO;
import net.grinecraft.etwig.model.User;
import net.grinecraft.etwig.services.UserRoleService;
import net.grinecraft.etwig.util.WebReturn;

@RestController
@RequestMapping(value = "/api/private/")
public class UserAPIController {
	
	@Autowired
	private UserRoleService userRoleService;
	
	//@Autowired
    //private PasswordEncoder passwordEncoder;
	
	@GetMapping("/getMyPositions")
    public Set<PositionDTO> getMyPositions() throws Exception {
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
