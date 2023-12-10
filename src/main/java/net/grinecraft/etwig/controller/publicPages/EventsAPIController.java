package net.grinecraft.etwig.controller.publicPages;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.grinecraft.etwig.services.EventService;
import net.grinecraft.etwig.util.DateUtils;
import net.grinecraft.etwig.util.NumberUtils;

@RestController
public class EventsAPIController {

	@Autowired
	EventService eventService;
	
	@RequestMapping("/public/_getEventsByWeek")  
	public Map<String, Object> getEventsByWeek(@RequestParam(required = false) String dateStr) throws Exception{
		LocalDate givenDate = DateUtils.safeParseDate(dateStr, "yyyy-MM-dd");
		Map<String, Object> myReturn = new LinkedHashMap<String, Object>();
		
		if(givenDate == null) {
			myReturn.put("error", 1);
	    	myReturn.put("msg", "dateStr parameter is either missing or invalid. It must be yyyy-mm-dd format.");
	    	myReturn.put("events", new LinkedHashMap<String, Object>());
		}else {
			myReturn.put("error", 0);
	    	myReturn.put("msg", "success.");
	    	System.out.println( eventService.getSingleTimeEventDetailsById(100, true));
	    	//myReturn.put("events", eventService.findByDateRange(givenDate, null));
		}
		return myReturn;
	}
	

	@RequestMapping("/public/_getEventById")  
	public Map<String, Object> getEventById(@RequestParam(required = false) String eventIdStr, @RequestParam(required = false) String showAllDetails) throws Exception{
		
		Long eventId = NumberUtils.safeCreateLong(eventIdStr);
		Map<String, Object> myReturn = new LinkedHashMap<String, Object>();
		
		if(eventId == null) {
			myReturn.put("error", 1);
	    	myReturn.put("msg", "eventId parameter is either missing or invalid. It must be an Integer.");
	    	myReturn.put("event", new LinkedHashMap<String, Object>());
		} else {
			myReturn.put("error", 0);
	    	myReturn.put("msg", "success.");
	    	System.out.println( eventService.getSingleTimeEventDetailsById(eventId, BooleanUtils.toBoolean(showAllDetails)));
	    	//myReturn.put("events", eventService.findByDateRange(givenDate, null));
		}
		return myReturn;
	}
	
}
