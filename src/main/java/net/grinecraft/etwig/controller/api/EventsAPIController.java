/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The controller for all events related APIs.
 */

package net.grinecraft.etwig.controller.api;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import net.grinecraft.etwig.services.EventService;
import net.grinecraft.etwig.util.DateUtils;
import net.grinecraft.etwig.util.NumberUtils;
import net.grinecraft.etwig.util.WebReturn;
import net.grinecraft.etwig.util.type.DateRange;

@RestController
public class EventsAPIController {

	@Autowired
	EventService eventService;
	
	/**
	 * The getEventList API
	 * @param date The given specific date
	 * @param range The given date range
	 * @return
	 * @throws Exception
	 * @authentication True
	 */
	
	@RequestMapping("/api/private/getEventList")  
	public Map<String, Object> getEventList(@RequestParam String date, @RequestParam String range) throws Exception{
		
		// Check the date is well-formed of not.
		LocalDate givenDate = DateUtils.safeParseDate(date, "yyyy-MM-dd");
		if(givenDate == null) {
			return WebReturn.errorMsg("date is invalid. It must be yyyy-mm-dd.", false);
		}
		
		// Get the event list
		Map<String, Object> myReturn = WebReturn.errorMsg(null, true);
	    myReturn.put("events", eventService.findByDateRange(givenDate, DateRange.safeValueOf(range)));
		return myReturn;
	}
	
	/**
	 * Get the event information by a specific given id.
	 * @param eventId The ID of the event
	 * @param showAllDetails
	 * @return
	 * @throws Exception
	 * @authentication True
	 */
	
	@RequestMapping("/api/private/getEventById")  
	public Map<String, Object> getEventById(@RequestParam String eventId) throws Exception{
		Long eventIdNum = NumberUtils.safeCreateLong(eventId);

		if(eventIdNum == null) {
			return WebReturn.errorMsg("eventId is invalid. It must be an Integer.", false);
		} 
			
		eventService.findById(eventIdNum, false);
		
		Map<String, Object> myReturn = WebReturn.errorMsg(null, true);
	   // myReturn.putAll(eventService.findById(eventIdNum));
		
		return myReturn;
	}
	
	/**
	 * Add an event into the database.
	 * @param eventInfo The object that contains the details of new event. 
	 * @return
	 * @authentication True
	 */
	
	@RequestMapping(value = "/api/private/addEvent", method = RequestMethod.POST)
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
	
	@RequestMapping(value = "/api/private/editEvent", method = RequestMethod.POST)
    public Map<String, Object> editEvent(HttpSession session, @RequestBody Map<String, Object> eventInfo) throws Exception {
				
		// Check the permission again in the backend.
		LinkedHashMap<String, Object> event = eventService.findById(Long.parseLong(eventInfo.get("eventId").toString()), false);
		if(!eventService.eventEditPermissionCheck(event)) {
			return WebReturn.errorMsg("You don't have permission to edit this event.", false);
		} 
		
		// Then edit event in the DB.
        eventService.editEvent((LinkedHashMap<String, Object>) eventInfo);
        return WebReturn.errorMsg(null, true);
    }
}
