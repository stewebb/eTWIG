package net.grinecraft.etwig.controller.publicPages;

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
	
	@RequestMapping("/public/_getEventList")  
	public Map<String, Object> getEventsByWeek(@RequestParam(required = false) String dateStr, @RequestParam(required = false) String rangeStr) throws Exception{
		String msg = "";
		
		// If the dateStr is null or invalid, the returning date is today. 
		LocalDate givenDate = DateUtils.safeParseDate(dateStr, "yyyy-MM-dd");
		if(givenDate == null) {
			givenDate = LocalDate.now();
			msg = "dateStr parameter is either missing or invalid. It must be yyyy-mm-dd format. Today will be used in there.";
		}
		
		Map<String, Object> myReturn = WebReturn.errorMsg(msg, true);
	    myReturn.put("events", eventService.findByDateRange(givenDate, DateRange.safeValueOf(rangeStr)));
		
		return myReturn;
	}
	
	/**
	 * Output event information by a given id.
	 * @param eventId
	 * @param showAllDetails
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("/public/_getEventById")  
	public Map<String, Object> getEventById(@RequestParam(required = false) String eventId, @RequestParam(required = false) String showAllDetails) throws Exception{
		Long eventIdNum = NumberUtils.safeCreateLong(eventId);
		Map<String, Object> myReturn = new LinkedHashMap<String, Object>();
		
		if(eventIdNum == null) {
			myReturn.put("error", 1);
	    	myReturn.put("msg", "eventId parameter is either missing or invalid. It must be an Integer.");
		} else {
			myReturn.put("error", 0);
	    	myReturn.put("msg", "success.");
	    	myReturn.putAll(eventService.findById(eventIdNum, BooleanUtils.toBooleanNullTrue(showAllDetails)));
		}
		return myReturn;
	}
}
