package net.etwig.webapp.controller.api;

import net.etwig.webapp.dto.events.EventDetailsDTO;
import net.etwig.webapp.model.Portfolio;
import net.etwig.webapp.services.EventService;
import net.etwig.webapp.services.PortfolioService;
import net.etwig.webapp.util.NumberUtils;
import net.etwig.webapp.util.PortfolioMismatchException;
import net.etwig.webapp.util.WebReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/nsRest/private/")
public class EventAPIController {

    @Autowired
    private EventService eventService;

    @Autowired
    private PortfolioService portfolioService;

    // NS = Not Standard

    /**
     * Add an event into database
     * @param eventInfo The new event payload, from front-end
     * @return Success message if event added.
     */

    @GetMapping("/event/add")
    public Map<String, Object> add(@RequestBody Map<String, Object> eventInfo) {
        eventService.editEvent(eventInfo, null);
        return WebReturn.errorMsg("Event added successfully.", true);
    }

    @GetMapping("/event/edit")
    public Map<String, Object> edit(@RequestBody Map<String, Object> eventInfo) throws Exception {

        // eventId check, stop proceeding when it is invalid or negative.
        Long eventId = NumberUtils.safeCreateLong(eventInfo.get("id").toString());
        if(eventId == null || eventId <= 0) {
            return WebReturn.errorMsg("EventId is invalid.", false);
        }

        // Event search, stop proceeding when it doesn't exist.
        EventDetailsDTO event = eventService.findById(eventId);
        if(event == null) {
            return WebReturn.errorMsg("The event with id=" + eventId + " does not exist.", false);
        }

        // Event exist, edit mode. But check permission in the backend again.
        Portfolio eventPortfolio = portfolioService.getPortfolioById(event.getPortfolioId());
        if(!eventService.eventEditPermissionCheck(eventPortfolio)) {
            //return WebReturn.errorMsg("You don't have permission to edit event.", false);
            throw new PortfolioMismatchException(eventPortfolio.getName());
        }

        // Edit event in the DB.
        eventService.editEvent(eventInfo, event);
        return WebReturn.errorMsg(null, true);
    }

    /**
     * View the detail of an event.
     * @param eventId The ID of the event.
     * @return The event details according to the ID, if not found, return null;
     */

    @GetMapping("/event/view")
    public Object view(@RequestParam Long eventId) {
        return eventService.findById(eventId);
    }

    @GetMapping("/event/remove")
    public Object remove(@RequestParam Long eventId) {
        // TODO Soft remove: Hide the event but can be restored.
        // TODO Hard remove: Remove event from DB permanently.
        return null;
    }
}
