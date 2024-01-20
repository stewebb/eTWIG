package net.grinecraft.etwig.controller.api;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.grinecraft.etwig.dto.PositionDTO;
import net.grinecraft.etwig.services.UserRoleService;

@RestController
@RequestMapping(value = "/api/private/")
public class UserAPIController {
	
	@Autowired
	private UserRoleService userRoleService;
	
	@GetMapping("/getMyPositions")
    public Set<PositionDTO> getMyPositions() throws Exception {
		return userRoleService.getMyPositions();
	}
}
