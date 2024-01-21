/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The controller for all events related APIs.
 */

package net.grinecraft.etwig.controller.api;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import net.grinecraft.etwig.dto.SingleTimeEventBasicInfoDTO;
import net.grinecraft.etwig.dto.EventDetailsDTO;
import net.grinecraft.etwig.dto.RecurringEventBasicInfoDTO;
import net.grinecraft.etwig.services.EventService;
import net.grinecraft.etwig.util.DateUtils;
import net.grinecraft.etwig.util.WebReturn;

@RestController
@RequestMapping("/api/private/")  
public class EventsAPIController {

	@Autowired
	EventService eventService;
	
	/**
	 * Get the event list in a monthly view, by a given date.
	 * @param date
	 * @return
	 * @throws Exception
	 */
	
	@GetMapping("/getMonthlySingleTimeEventList")  
	public LinkedHashMap<Long, SingleTimeEventBasicInfoDTO> getMonthlySingleTimeEventList(@RequestParam String date) throws Exception{
		return eventService.getMonthlySingleTimeEventsByDateRange(DateUtils.safeParseDate(date, "yyyy-MM-dd"));
	}
	
	@GetMapping("/getAllRecurringEventList")  
	public List<RecurringEventBasicInfoDTO> getAllRecurringEventList() throws Exception{
		return eventService.getAllRecurringEvents();
	}
	
	/**
	 * Get the event information by a specific given id.
	 * @param eventId
	 * @return
	 */
	
	@GetMapping("/getEventById")  
	public EventDetailsDTO getEventById(@RequestParam Long eventId){
		return eventService.findById(eventId);
	}
	
	/**
	 * Add an event into the database.
	 * @param eventInfo The object that contains the details of new event. 
	 * @return
	 * @authentication True
	 */
	
	@PostMapping(value = "/addEvent")
    public Map<String, Object> addEvent(@RequestBody Map<String, Object> eventInfo) {
        eventService.addEvent((LinkedHashMap<String, Object>) eventInfo);
        return WebReturn.errorMsg(null, true);
    }
	
	/**
	 * Edit an existing event.
	 * @param session
	 * @param eventInfo
	 * @return
	 * @throws Exception
	 * @authentication True
	 */
	
	@PostMapping(value = "/editEvent")
    public Map<String, Object> editEvent(HttpSession session, @RequestBody Map<String, Object> eventInfo) throws Exception {
				
		// Check the permission again in the back end.
		LinkedHashMap<String, Object> event = null;//eventService.findById(Long.parseLong(eventInfo.get("eventId").toString()));
		if(!eventService.eventEditPermissionCheck(event)) {
			return WebReturn.errorMsg("You don't have permission to edit this event.", false);
		} 
		
		// Then edit event in the DB.
        eventService.editEvent((LinkedHashMap<String, Object>) eventInfo);
        return WebReturn.errorMsg(null, true);
    }
}
