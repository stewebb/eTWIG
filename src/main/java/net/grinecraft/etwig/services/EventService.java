/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The services for all event-related options.
	*/

package net.grinecraft.etwig.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import net.grinecraft.etwig.dto.events.AddEditEventDTO;
import net.grinecraft.etwig.dto.events.EventDetailsDTO;
import net.grinecraft.etwig.dto.events.GraphicsRequestEventInfoDTO;
import net.grinecraft.etwig.dto.events.RecurringEventBasicInfoDTO;
import net.grinecraft.etwig.dto.events.SingleTimeEventBasicInfoDTO;
import net.grinecraft.etwig.dto.user.UserAccessDTO;
import net.grinecraft.etwig.model.Event;
import net.grinecraft.etwig.model.EventOption;
import net.grinecraft.etwig.model.EventOptionKey;
import net.grinecraft.etwig.model.Portfolio;
import net.grinecraft.etwig.repository.EventOptionRepository;
import net.grinecraft.etwig.repository.EventRepository;
import net.grinecraft.etwig.util.DateUtils;
import net.grinecraft.etwig.util.ListUtils;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private EventOptionRepository eventOptionRepository;
	
	
	@Autowired
	private HttpSession session;
	
	/**
	 * Public entry of the event services.
	 * The access control modifiers are "public".
	 * @throws Exception 
	 */
	
	public GraphicsRequestEventInfoDTO findEventsForGraphicsRequestById(long eventId) throws Exception {
		Event event = (eventRepository == null) ? null :eventRepository.findById(eventId).orElse(null);
		return (event == null) ?  null : new GraphicsRequestEventInfoDTO(event);
	}
	
	/**
	 * Find the event detail by it's id.
	 * @param id
	 * @return EventDetailsDTO
	 */	
	
	public EventDetailsDTO findById(long id) {
		return eventRepository.findById(id).map(EventDetailsDTO::new).orElse(null);
	}
	
	public LinkedHashMap<Long, SingleTimeEventBasicInfoDTO> getMonthlySingleTimeEventsByDateRange(LocalDate givenDate) throws Exception{
		
		// Get the date boundary
		LocalDate last = DateUtils.findFirstDayOfThisMonth(givenDate);
		LocalDate next = DateUtils.findFirstDayOfNextMonth(givenDate);
        LinkedHashMap<Long, SingleTimeEventBasicInfoDTO> allEvents = new LinkedHashMap<>();

		// Get all single time events in the given date range.
		List<Event> singleTimeEventList = eventRepository.findSingleTimeEventByDateRange(last, next);
        for(Event event : singleTimeEventList) {     
        	allEvents.put(event.getId(), new SingleTimeEventBasicInfoDTO(event));
        }
        return allEvents;
	}
	
	public List<RecurringEventBasicInfoDTO> getAllRecurringEvents(){
		if(eventRepository == null) {
			return null;
		}
		return eventRepository.findByRecurringTrue().stream().map(RecurringEventBasicInfoDTO::new).collect(Collectors.toList());
	}
	
	/**
	 * Edit an event to the database
	 * @param eventInfo The event details.
	 */
	
	@SuppressWarnings("null")
	public void editEvent(Map<String, Object> eventInfo, EventDetailsDTO currentEvent) {
		
		// Add event
		AddEditEventDTO newEventDTO = new AddEditEventDTO(eventInfo, currentEvent);
		//System.out.println(newEventDTO);
		Event addedEvent = eventRepository.save(newEventDTO.toEntity());
		
		// Add options
		ArrayList<Long> optionList = ListUtils.stringArrayToLongArray(ListUtils.stringToArrayList(eventInfo.get("properties").toString()));
		updateEventOptionBulky(addedEvent.getId(), optionList);
	}
	
	/**
	 * Get and set resources, they are only used in this class.
	 * The access control modifiers are "private".
	 */
	

	
	/**
	 * Update event options bulky, by removing all all existing options first, and then add all new options.
	 * @param eventId The id of the event.
	 * @param optionIds A list with all options that associated for the event.
	 */
	
	@SuppressWarnings("null")
	@Transactional
	private void updateEventOptionBulky(Long eventId, List<Long> optionIds) {

		// Remove all existing options associations for the event.
        List<EventOption> existingEventOptions = eventOptionRepository.findByIdEventId(eventId);
        eventOptionRepository.deleteAll(existingEventOptions);

        // Add new options associations for the event.
        List<EventOption> newEventOptions = optionIds.stream()
            .map(optionId -> new EventOption(new EventOptionKey(eventId, optionId)))
            .collect(Collectors.toList());
        eventOptionRepository.saveAll(newEventOptions);

    }
	
	/**
	 * Helper methods below, they are only used in this class.
	 * The access control modifiers are "private".
	 */
	
	public boolean eventEditPermissionCheck(Portfolio portfolio) throws Exception {
			
		// Get user authority
		UserAccessDTO access = (UserAccessDTO) session.getAttribute("access");
		
		// Case 1: System administrators have edit permission, regardless of which portfolio the user has.
		if(access.isAdminAccess()) {
			return true;
		}
		
		// Case 2: This user has events access, has edit view permission depends on the portfolio.
		else if (access.isEventsAccess()) {

			// All portfolios that I have.
			Set<Portfolio> myPortfolios = userRoleService.getMyPortfolios();
			
			// Iterate my portfolios, if any of them matches the given portfolio, I have edit permission.
			for(Portfolio myPortfolio : myPortfolios) {
				if(myPortfolio.equals(portfolio)) {
					return true;
				}
			}
			
			// Otherwise I don't have edit permission.
			return false;
		}
		
		// Case 3: Other users have no edit permission.
		else {
			return false;
		}
	}

}
