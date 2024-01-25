package net.grinecraft.etwig.controller.graphics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.grinecraft.etwig.dto.graphics.PendingRequestsDetailsDTO;
import net.grinecraft.etwig.services.GraphicsRequestService;

@Controller
@Secured({"ROLE_GRAPHICS"})
@RequestMapping("/graphics/approval/") 
public class ApprovalController {

	@Autowired
	private GraphicsRequestService graphicsRequestService;
	
	//@Autowired
	//private EventService eventService;
	
	@GetMapping("/list")  
	public String list(Model model) throws Exception{
		return "graphics/approval_list";
	}
	
	@GetMapping("/decide")  
	public String decide(Model model, @RequestParam @NonNull Long requestId) throws Exception{
		
		// Get request info
		PendingRequestsDetailsDTO request = graphicsRequestService.getRequestsById(requestId);
		if(request == null) {
			model.addAttribute("reason", "Graphics request with id=" + requestId + " doesn't exist.");
			return "_errors/custom_error";
		}
		
		System.out.println(request);
		model.addAttribute("requestInfo", request);
		
		return "graphics/approval_decide";
	}
}
