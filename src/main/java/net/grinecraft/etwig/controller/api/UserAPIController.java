package net.grinecraft.etwig.controller.api;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.grinecraft.etwig.dto.PositionDTO;
import net.grinecraft.etwig.model.User;
import net.grinecraft.etwig.services.UserRoleService;

@RestController
@RequestMapping(value = "/api/private/")
public class UserAPIController {
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@GetMapping("/getMyPositions")
    public Set<PositionDTO> getMyPositions() throws Exception {
		return userRoleService.getMyPositions();
	}
	
	@PostMapping("/changeMyPassword")
    public Map<String, Object> changeMyPassword(@RequestBody Map<String, Object> passwordInfo) {
		
		User currentUser = userRoleService.getMyDetails();
		return null;
        
        //if (!passwordEncoder.matches(passwordChangeDto.getOldPassword(), currentUser.getPassword())) {
        //    return ResponseEntity.badRequest().body(Map.of("message", "Old password is incorrect."));
        //}

       // userService.changePassword(currentUser, passwordChangeDto.getNewPassword());
        // Update security context or re-authenticate user

        //return ResponseEntity.ok(Map.of("message", "Password successfully changed."));
    }
}
