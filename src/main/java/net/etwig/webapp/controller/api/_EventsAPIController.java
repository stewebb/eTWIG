/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The controller for all events related APIs.
 */

package net.etwig.webapp.controller.api;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.etwig.webapp.dto.events.RecurringEventBasicInfoDTO;
import net.etwig.webapp.dto.events.SingleTimeEventBasicInfoDTO;
import net.etwig.webapp.services.EventService;
import net.etwig.webapp.util.DateUtils;

@RestController
@RequestMapping("/api/private/")  
public class _EventsAPIController {

	@Autowired
	private EventService eventService;

	/**
	 * Get the event list in a monthly view, by a given date.
	 * @param date
	 * @return
	 * @throws Exception
	 */
	
	@GetMapping("/getSingleTimeEventList")  
	public LinkedHashMap<Long, SingleTimeEventBasicInfoDTO> getSingleTimeEventList(@RequestParam String date, @RequestParam int calendarView) throws Exception{
		return eventService.getSingleTimeEventsByDateRange(DateUtils.safeParseDate(date, "yyyy-MM-dd"), calendarView);
	}
	
	@GetMapping("/getAllRecurringEventList")  
	public List<RecurringEventBasicInfoDTO> getAllRecurringEventList() throws Exception{
		return eventService.getAllRecurringEvents();
	}
}
