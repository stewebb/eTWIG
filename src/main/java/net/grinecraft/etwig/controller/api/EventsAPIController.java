/**
 * eTWIG - The event and banner management software for residential halls and student unions.
 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
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

import net.grinecraft.etwig.services.EventService;
import net.grinecraft.etwig.util.BooleanUtils;
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
	 * @type Private APIs
	 */
	
	@RequestMapping("/api/private/getEventList")  
	public Map<String, Object> getEventList(@RequestParam String date, @RequestParam String range) throws Exception{
		String msg = "";
		
		// If the dateStr is null or invalid, the returning date is today. 
		LocalDate givenDate = DateUtils.safeParseDate(date, "yyyy-MM-dd");
		if(givenDate == null) {
			givenDate = LocalDate.now();
			msg = "date parameter is invalid. It must be yyyy-mm-dd format. Today will be used in there.";
		}
		
		// Get the event list
		Map<String, Object> myReturn = WebReturn.errorMsg(msg, true);
	    myReturn.put("events", eventService.findByDateRange(givenDate, DateRange.safeValueOf(range)));
		return myReturn;
	}
	
	/**
	 * Get the event information by a specific given id.
	 * @param eventId The ID of the event
	 * @param showAllDetails
	 * @return
	 * @throws Exception
	 * @type Private APIs
	 */
	
	@RequestMapping("/api/getEventById")  
	public Map<String, Object> getEventById(@RequestParam String eventId, @RequestParam String showAllDetails) throws Exception{
		Long eventIdNum = NumberUtils.safeCreateLong(eventId);

		if(eventIdNum == null) {
			return WebReturn.errorMsg("eventId parameter is invalid. It must be an Integer.", false);
		} 
			
		Map<String, Object> myReturn = WebReturn.errorMsg(null, true);
	    myReturn.putAll(eventService.findById(eventIdNum, BooleanUtils.toBoolean(showAllDetails)));
		
		return myReturn;
	}
	
	@RequestMapping(value = "/api/addEvent", method = RequestMethod.POST)
    public Map<String, Object> addEvent(@RequestBody Map<String, Object> eventInfo) {
        System.out.println(eventInfo);
        
        //String timeUnit = eventInfo.get("timeUnit").toString();
        //System.out.println(timeUnit);
        
        //EventTimeUnit eventUnit = EventTimeUnit.fromString(timeUnit);
        
        //System.out.println(eventUnit);
       
        
        eventService.addEvent((LinkedHashMap<String, Object>) eventInfo);
        
        return WebReturn.errorMsg(null, true);
    }
}
