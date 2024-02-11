package net.grinecraft.etwig.controller.graphics;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Secured({"hasAuthority(ROLE_GRAPHICS)"})
@RequestMapping("/graphics/events/")
public class GraphicsEventsController {

	
	@GetMapping("/list")  
	public String view(Model model) throws Exception{
		return "graphics/events_list";
	}

}
