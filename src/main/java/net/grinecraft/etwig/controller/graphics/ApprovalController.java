package net.grinecraft.etwig.controller.graphics;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Secured({"ROLE_GRAPHICS"})
@RequestMapping("/graphics/approval/") 
public class ApprovalController {

	//@Autowired
	//private GraphicsRequestService bannerRequestService;
	
	//@Autowired
	//private EventService eventService;
	
	@GetMapping("/list")  
	public String list(Model model) throws Exception{
		

		
		return "graphics/approval_list";
	}
}
