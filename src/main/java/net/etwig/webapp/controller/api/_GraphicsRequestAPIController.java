package net.etwig.webapp.controller.api;

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

import net.etwig.webapp.services.GraphicsRequestService;
import net.etwig.webapp.services.PortfolioService;
import net.etwig.webapp.dto.events.EventDetailsDTO;
import net.etwig.webapp.dto.graphics.FinalizedRequestsBasicInfoDTO;
import net.etwig.webapp.dto.graphics.PendingRequestsBasicInfoDTO;
import net.etwig.webapp.model.GraphicsRequest;
import net.etwig.webapp.model.Portfolio;
import net.etwig.webapp.services.EmailService;
import net.etwig.webapp.services.EventService;
import net.etwig.webapp.util.NumberUtils;
import net.etwig.webapp.util.WebReturn;

@RestController
@RequestMapping(value = "/api/private/")
//@Secured({"ROLE_ADMINISTRATOR", "ROLE_EVENT_MANAGER"})
public class _GraphicsRequestAPIController {
	
	@Autowired
	private GraphicsRequestService graphicsRequestService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private PortfolioService portfolioService;
	
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
		Portfolio eventPortfolio = portfolioService.getPortfolioById(event.getPortfolioId());
		if(!eventService.eventEditPermissionCheck(eventPortfolio)) {
			return WebReturn.errorMsg("You don't have permission to make this request.", false);
		} 
		
		// Add request info to the DB.
		Long ii = graphicsRequestService.addRequest(requestInfo);

		// Send an email to all graphics managers
		//emailService.graphicsRequestNotification(requestInfo);	
		//System.out.println(ii);
		//System.out.println(graphicsRequestService.findById(ii-1));
		//System.out.println(graphicsRequestService.findById(ii));
		//System.out.println(graphicsRequestService.findById(ii));
		//System.out.println(graphicsRequestService.findById(ii));
		//emailService.graphicsRequestNotification(new NewRequestEmailNotificationDTO(graphicsRequestService.findById(ii)));
		
		
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

}
