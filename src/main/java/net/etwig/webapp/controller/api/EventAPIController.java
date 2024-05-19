package net.etwig.webapp.controller.api;

import net.etwig.webapp.dto.events.EventDetailsDTO;
import net.etwig.webapp.model.Portfolio;
import net.etwig.webapp.services.EventService;
import net.etwig.webapp.services.PortfolioService;
import net.etwig.webapp.util.NumberUtils;
import net.etwig.webapp.util.PortfolioMismatchException;
import net.etwig.webapp.util.WebReturn;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/event/")
public class EventAPIController {

    @Autowired
    private EventService eventService;

    @Autowired
    private PortfolioService portfolioService;

    /**
     * Handles the HTTP POST request at the "/add" endpoint.
     * This method is responsible for adding a new event with the provided event information.
     *
     * @param eventInfo a {@link Map} containing the event details as key-value pairs. <br>
     *                  This map is parsed from the JSON body of the request.
     * @return a {@link Map} representing the response to the client. <br>
     * It includes a message indicating successful addition of the event and a status flag.
     * @location /api/event/add
     * @permission Those who has event management permission.
     */

    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Map<String, Object> eventInfo) {
        eventService.editEvent(eventInfo, null);
        return WebReturn.errorMsg("Event added successfully.", true);
    }

    /**
     * Edit a current event
     * @param eventInfo The new event payload, from front-end
     * @return Success message if event modified.
     * @location /api/event/edit
     * @permission Those who has event management permission.
     */

    @PostMapping("/edit")
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
        Portfolio eventPortfolio = portfolioService.findById(event.getPortfolioId());
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
     * @location /api/event/view
     * @permission All logged in users.
     */

    @GetMapping("/view")
    public Object view(@RequestParam Long eventId) {
        return eventService.findById(eventId);
    }

    // TODO Javadoc for event removal
    @GetMapping("/remove")
    public Object remove(@RequestParam Long eventId) {
        // TODO Soft remove: Hide the event but can be restored.
        // TODO Hard remove: Remove event from DB permanently.
        return null;
    }

    /**
     * Create one or multiple events bulky by importing from a file.<br>
     * The file must be in the Microsoft Excel Spreadsheet (*.xlsx) and OpenDocument Spreadsheet (*.ods) format.
     * @param file The imported file data
     * @param role The current role of the user
     * @return Success message if event imported.
     * @location /api/event/import
     * @permission Those who has event management permission.
     */

    @PostMapping(value = "/import")
    public Map<String, Object> importEvents(@RequestParam("file") MultipartFile file, @RequestParam("role") Long role) throws Exception {

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

        Map<String, Object> webReturn = WebReturn.errorMsg("", true);
        webReturn.put("result", eventService.importEvents(file, extension, role));
        return webReturn;
    }
}
