package net.grinecraft.etwig.controller.graphics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.grinecraft.etwig.dto.graphics.PendingRequestsDetailsDTO;
import net.grinecraft.etwig.services.GraphicsRequestService;

@Controller
@PostAuthorize("hasAuthority('ROLE_GRAPHICS')")
@RequestMapping("/graphics/approval/") 
public class ApprovalController {

	@Autowired
	private GraphicsRequestService graphicsRequestService;
	
	@GetMapping("/list")  
	public String list() throws Exception{
		return "graphics/approval_list";
	}
	
	@GetMapping("/decide")  
	public String decide(Model model, @RequestParam @NonNull Long requestId) throws Exception{
		
		// Get request info
		PendingRequestsDetailsDTO request = graphicsRequestService.getPendingRequestsById(requestId);
		if(request == null) {
			model.addAttribute("reason", "Graphics request with id=" + requestId + " doesn't exist, or it has been finalized.");
			return "_errors/custom_error";
		}
		
		model.addAttribute("requestInfo", request);
		return "graphics/approval_decide";
	}
}
