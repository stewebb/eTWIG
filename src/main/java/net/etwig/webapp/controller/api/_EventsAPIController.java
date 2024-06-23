/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The controller for all events related APIs.
 */

package net.etwig.webapp.controller.api;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.etwig.webapp.dto.events.EventDetailsDTO;
import net.etwig.webapp.dto.events.RecurringEventBasicInfoDTO;
import net.etwig.webapp.dto.events.SingleTimeEventBasicInfoDTO;
import net.etwig.webapp.model.Portfolio;
import net.etwig.webapp.services.EventOptionService;
import net.etwig.webapp.services.EventService;
import net.etwig.webapp.services.PortfolioService;
import net.etwig.webapp.util.DateUtils;
import net.etwig.webapp.util.NumberUtils;
import net.etwig.webapp.util.WebReturn;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/private/")  
public class _EventsAPIController {

	@Autowired
	private EventService eventService;
	
	@Autowired
	private EventOptionService eventOptionService;
	
	@Autowired
	private PortfolioService portfolioService;
	
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

	// TODO OPTIONS WITH A SEPARATE ENDPOINT
	@GetMapping("/getSelectedOptionsByEventId")  
	public HashSet<Long> getSelectedOptionsByEventId(@RequestParam Long eventId) throws Exception {
		//return eventOptionService.getOptionsByEvent(eventId);
		return null;
	}
}
