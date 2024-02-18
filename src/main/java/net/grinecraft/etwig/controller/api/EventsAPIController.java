/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The controller for all events related APIs.
 */

package net.grinecraft.etwig.controller.api;

import java.io.IOException;
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

import net.grinecraft.etwig.dto.events.EventDetailsDTO;
import net.grinecraft.etwig.dto.events.RecurringEventBasicInfoDTO;
import net.grinecraft.etwig.dto.events.SingleTimeEventBasicInfoDTO;
import net.grinecraft.etwig.model.Portfolio;
import net.grinecraft.etwig.services.EventOptionService;
import net.grinecraft.etwig.services.EventService;
import net.grinecraft.etwig.services.PortfolioService;
import net.grinecraft.etwig.util.DateUtils;
import net.grinecraft.etwig.util.NumberUtils;
import net.grinecraft.etwig.util.WebReturn;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/private/")  
public class EventsAPIController {

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
	
	/**
	 * Get the event information by a specific given id.
	 * @param eventId
	 * @return
	 */
	
	@GetMapping("/getEventById")  
	public EventDetailsDTO getEventById(@RequestParam Long eventId){
		return eventService.findById(eventId);
	}
	
	@GetMapping("/getSelectedOptionsByEventId")  
	public HashSet<Long> getSelectedOptionsByEventId(@RequestParam Long eventId) throws Exception {
		return eventOptionService.getOptionsByEvent(eventId);
	}
	
	/**
	 * Edit an existing event.
	 * @param eventInfo
	 * @return
	 * @throws Exception
	 * @authentication True
	 */
	
	@PostMapping(value = "/editEvent")
    public Map<String, Object> editEvent(@RequestBody Map<String, Object> eventInfo) throws Exception {
				
		// Get event type
		Long eventId = NumberUtils.safeCreateLong(eventInfo.get("id").toString());
		
		// Invalid or negative eventId, add event.
		if(eventId == null || eventId <= 0) {
			eventService.editEvent(eventInfo, null);
			return WebReturn.errorMsg(null, true);
		}
		
		// Event not exist, add mode.
		EventDetailsDTO event = eventService.findById(eventId);
		if(event == null) {
			eventService.editEvent(eventInfo, null);
			return WebReturn.errorMsg(null, true);
		}
		
		// Event exist, edit mode. But check permission again.
		Portfolio eventPortfolio = portfolioService.getPortfolioById(event.getPortfolioId());
		if(!eventService.eventEditPermissionCheck(eventPortfolio)) {
			return WebReturn.errorMsg("You don't have permission to edit event.", false);
		} 
		
		// Then edit event in the DB.
		eventService.editEvent(eventInfo, event);
        return WebReturn.errorMsg(null, true);
    }

	@PostMapping(value = "importEvents")
	public Map<String, Object> importEvents(@RequestParam("file") MultipartFile file) throws Exception {

		// Null check
		if(file == null || file.isEmpty()) {
			return WebReturn.errorMsg("The file is null.", false);
		}

		// Check and read file
		String fileName = file.getOriginalFilename();
		String extension = FilenameUtils.getExtension(fileName);
		if(!"xlsx".equalsIgnoreCase(extension) && ! "ods".equalsIgnoreCase(extension)) {
			return WebReturn.errorMsg("Only Microsoft Excel Spreadsheet (*.xlsx) and OpenDocument Spreadsheet (*.ods) format are accepted. However, the extension of the uploaded file is " + extension, false);
		}

		// Copy file and add related info
		//assetService.uploadFile(file);
		Map<String, Object> webReturn = WebReturn.errorMsg("", true);
		webReturn.put("result", eventService.importEvents(file, extension));
		return webReturn;
	}
}
