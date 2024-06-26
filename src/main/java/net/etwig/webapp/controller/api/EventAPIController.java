package net.etwig.webapp.controller.api;

import net.etwig.webapp.dto.events.EventDetailsDTO;
import net.etwig.webapp.model.Portfolio;
import net.etwig.webapp.services.EventService;
import net.etwig.webapp.services.PortfolioService;
import net.etwig.webapp.util.InvalidParameterException;
import net.etwig.webapp.util.NumberUtils;
import net.etwig.webapp.util.PortfolioMismatchException;
import net.etwig.webapp.util.WebReturn;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.HashMap;
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
     * @throws Exception if there is an error during the event addition process.
     * @location /api/event/add
     * @permission This endpoint requires users to have event management permissions.
     */

    @PostMapping("/add")
    public void add(@RequestBody Map<String, Object> eventInfo) throws Exception {
        eventService.editEvent(eventInfo, null);
    }

    /**
     /**
     * Edits an existing event with the provided event information.
     * <p>
     * This method validates the eventId, ensures the event exists, checks permissions,
     * and then updates the event with the new details provided in the request body.
     * </p>
     *
     * @param eventInfo A {@link Map} containing the new event details as key-value pairs. <br>
     *                  This map is parsed from the JSON body of the request.
     * @throws Exception if there is an error during the event modification process,
     *                   such as an invalid eventId, non-existent event, or permission issues.
     * @location /api/event/edit
     * @permission This endpoint requires users to have event management permissions.
     */

    @PostMapping("/edit")
    public void edit(@RequestBody Map<String, Object> eventInfo) throws Exception {

        // eventId check, stop proceeding when it is invalid or negative.
        Long eventId = NumberUtils.safeCreateLong(eventInfo.get("id").toString());
        if(eventId == null || eventId <= 0) {
            throw new InvalidParameterException("EventId is invalid.");
        }

        // Event search, stop proceeding when it doesn't exist.
        EventDetailsDTO event = eventService.findById(eventId);
        if(event == null) {
            throw new InvalidParameterException("The event with id=" + eventId + " does not exist.");
        }

        // Event exist, edit mode. But check permission in the backend again.
        Portfolio eventPortfolio = portfolioService.findById(event.getPortfolioId());
        if(!eventService.eventEditPermissionCheck(eventPortfolio)) {
            throw new PortfolioMismatchException(eventPortfolio.getName());
        }

        // Edit event in the DB.
        eventService.editEvent(eventInfo, event);
    }

    /**
     * Handles the HTTP GET request to view the details of an event specified by its ID. This endpoint retrieves the event details
     * from the service layer based on the provided eventId. If the event is found, its details are returned; if not, null is returned.
     * This method is accessible to all logged-in users.
     *
     * @param eventId The ID of the event whose details are to be retrieved. Must not be null.
     * @return The event details as an Object. Returns null if no event matches the given ID.
     * @location /api/event/view
     * @permission This endpoint requires the user to be logged in.
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

    @PreAuthorize("hasAuthority('ROLE_EVENTS')")
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

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list(
            @RequestParam(name = "startDate", required = false) LocalDate startDate,
            @RequestParam(name = "endDate",required = false) LocalDate endDate,
            @RequestParam(name = "recurring",required = false) Boolean recurring,
            @RequestParam(name = "portfolioId",required = false) Long portfolioId,
            @RequestParam("start") int start,
            @RequestParam("length") int length,
            @RequestParam("draw") int draw,
            @RequestParam("sortColumn") String sortColumn,
            @RequestParam("sortDirection") String sortDirection) {

        Sort.Direction dir = "asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageable = PageRequest.of(start / length, length, Sort.by(dir, sortColumn));

        // Get data as pages
        Page<EventDetailsDTO> page = eventService.findByCriteria(
                startDate, endDate, recurring, portfolioId, pageable
        );

        Map<String, Object> json = new HashMap<>();
        json.put("draw", draw);
        json.put("recordsTotal", page.getTotalElements());
        json.put("recordsFiltered", page.getTotalElements());
        json.put("data", page.getContent());
        return ResponseEntity.ok(json);
    }
}
