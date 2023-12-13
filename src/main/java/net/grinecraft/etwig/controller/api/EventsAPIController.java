package net.grinecraft.etwig.controller.api;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@RequestMapping("/api/getEventList")  
	public Map<String, Object> getEventsByWeek(@RequestParam String date, @RequestParam String range) throws Exception{
		String msg = "";
		
		// If the dateStr is null or invalid, the returning date is today. 
		LocalDate givenDate = DateUtils.safeParseDate(date, "yyyy-MM-dd");
		if(givenDate == null) {
			givenDate = LocalDate.now();
			msg = "date parameter is invalid. It must be yyyy-mm-dd format. Today will be used in there.";
		}
		
		Map<String, Object> myReturn = WebReturn.errorMsg(msg, true);
	    myReturn.put("events", eventService.findByDateRange(givenDate, DateRange.safeValueOf(range)));
		
		return myReturn;
	}
	
	/**
	 * Output event information by a given id.
	 * @param eventId
	 * @param showAllDetails
	 * @return
	 * @throws Exception
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
}
