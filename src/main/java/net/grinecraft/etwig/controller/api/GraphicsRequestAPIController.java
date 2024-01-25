package net.grinecraft.etwig.controller.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.grinecraft.etwig.services.GraphicsRequestService;
import net.grinecraft.etwig.dto.events.EventDetailsDTO;
import net.grinecraft.etwig.dto.graphics.FinalizedRequestsBasicInfoDTO;
import net.grinecraft.etwig.dto.graphics.PendingRequestsBasicInfoDTO;
import net.grinecraft.etwig.model.GraphicsRequest;
import net.grinecraft.etwig.services.EmailService;
import net.grinecraft.etwig.services.EventService;
import net.grinecraft.etwig.util.NumberUtils;
import net.grinecraft.etwig.util.WebReturn;

@RestController
@RequestMapping(value = "/api/private/")
//@Secured({"ROLE_ADMINISTRATOR", "ROLE_EVENT_MANAGER"})
public class GraphicsRequestAPIController {
	
	@Autowired
	private GraphicsRequestService graphicsRequestService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private EmailService emailService;

	@GetMapping("/countRequestsByEventId")
    public Map<String, Object> countRequestsByEventId(@RequestParam Long eventId) throws Exception {
		
		Map<String, Object> myReturn = WebReturn.errorMsg(null, true);
		myReturn.put("count", graphicsRequestService.countByEventId(eventId));
		return myReturn;
	}
	
	@PostAuthorize("hasAuthority('ROLE_EVENTS')")
	@PostMapping(value = "/requestGraphic")
    public Map<String, Object> requestGraphic(@RequestBody Map<String, Object> requestInfo) throws Exception {
		
		// Check permission again!
		EventDetailsDTO event = eventService.findById(Long.parseLong(requestInfo.get("eventId").toString()));
		if(!eventService.eventEditPermissionCheck(event.getPosition().getPortfolio())) {
			return WebReturn.errorMsg("You don't have permission to make this request.", false);
		} 
		
		// Add request info to the DB.
		graphicsRequestService.addRequest(requestInfo);

		// Send an email to all graphics managers
		emailService.graphicsRequest(requestInfo);		
        return WebReturn.errorMsg(null, true);
    }
	
	@PostAuthorize("hasAuthority('ROLE_GRAPHICS')")
	@GetMapping(value = "/getPendingRequests")
	public Page<PendingRequestsBasicInfoDTO> getPendingRequests(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) throws Exception {
		return graphicsRequestService.getPendingRequests(page, size);
	}
	
	@PostAuthorize("hasAuthority('ROLE_GRAPHICS')")
	@GetMapping(value = "/getFinalizedRequests")
	public Page<FinalizedRequestsBasicInfoDTO> getFinalizedRequests(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) throws Exception {
		return graphicsRequestService.getFinalizedRequests(page, size);
	}
	
	@PostAuthorize("hasAuthority('ROLE_GRAPHICS')")
	@PostMapping(value = "/approveRequests")
	public Map<String, Object> approveRequests(@RequestBody Map<String, Object> decisionInfo) {
		
		// Get current request
		Long requestId = NumberUtils.safeCreateLong(decisionInfo.get("id").toString());
		
		// Invalid or negative eventId, add event.
		if(requestId == null || requestId <= 0) {
			return WebReturn.errorMsg("The requestId is invalid.", false);
		}
		
		// Check the existence
		GraphicsRequest currentRequest = graphicsRequestService.findById(requestId);
		if(currentRequest == null) {
			return WebReturn.errorMsg("The graphics request of requestId= " + requestId + " does not exist.", false);
		}
		
		graphicsRequestService.approveRequest(currentRequest, decisionInfo);
		return WebReturn.errorMsg(null, true);
	}
}
