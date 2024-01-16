package net.grinecraft.etwig.controller.graphics;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Secured({"ROLE_ADMINISTRATOR", "ROLE_EVENT_MANAGER"})
@RequestMapping("/graphics/request/") 
public class GraphicsRequestController {

	@GetMapping("/event")  
	public String view(Model model) throws Exception{
		return "graphics/request_event";
	}
}
