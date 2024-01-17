package net.grinecraft.etwig.controller.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.grinecraft.etwig.services.BannerRequestService;
import net.grinecraft.etwig.util.WebReturn;

@RestController
@RequestMapping(value = "/api/private/")
//@Secured({"ROLE_ADMINISTRATOR", "ROLE_EVENT_MANAGER"})
public class BannerRequestAPIController {
	
	@Autowired
	private BannerRequestService bannerRequestService;

	@GetMapping("/countRequestsByEventId")
    public Map<String, Object> countRequestsByEventId(@RequestParam Long eventId) throws Exception {
		
		Map<String, Object> myReturn = WebReturn.errorMsg(null, true);
		myReturn.put("count", bannerRequestService.countByEventId(eventId));
		return myReturn;
		
	}
	
	@PostMapping(value = "/requestGraphic")
    public Map<String, Object> requestGraphic(@RequestBody Map<String, Object> requestInfo) {
        //eventService.addEvent((LinkedHashMap<String, Object>) eventInfo);
        return WebReturn.errorMsg(null, true);
    }
}
