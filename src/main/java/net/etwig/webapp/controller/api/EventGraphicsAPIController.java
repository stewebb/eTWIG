package net.etwig.webapp.controller.api;

import net.etwig.webapp.dto.graphics.EventGraphicsAPIForDetailsPageDTO;
import net.etwig.webapp.dto.graphics.EventGraphicsAPIForSummaryPageDTO;
import net.etwig.webapp.services.EventGraphicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/eventGraphics/")
public class EventGraphicsAPIController {

    @Autowired
    EventGraphicsService eventGraphicsService;

    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Map<String, Object> eventInfo) {
        return null;
    }

    @PostMapping("/edit")
    public Map<String, Object> edit(@RequestBody Map<String, Object> eventInfo) {
        return null;
    }

    /**
     * Handles the HTTP GET request to view event graphics details.
     * <p>
     * This method is mapped to the "/view" endpoint and is responsible for fetching and displaying the graphics details
     * of an event identified by its unique graphics ID. The {@code graphicsId} is provided as a query parameter in the
     * request. This method leverages the {@link EventGraphicsService} to retrieve the corresponding
     * {@link EventGraphicsAPIForDetailsPageDTO} object. If found, the DTO is returned, otherwise, the response would be {@code null}.
     *
     * @param graphicsId The unique identifier of the event graphics to retrieve. This is passed as a query parameter.
     * @return An {@link EventGraphicsAPIForDetailsPageDTO} object containing the graphics details of the event,
     *         or {@code null} if no graphics details are found.
     * @location /api/eventGraphics/view
     * @permission All logged in users.
     */

    @GetMapping("/view")
    public EventGraphicsAPIForDetailsPageDTO view(@RequestParam Long graphicsId) {
        return eventGraphicsService.findById(graphicsId);
    }

    @PreAuthorize("hasAuthority('ROLE_GRAPHICS')")
    @GetMapping("/remove")
    public void remove(@RequestParam Long graphicsId) {
        eventGraphicsService.deleteById(graphicsId);
    }

    /**
     * Handles a GET request to list event graphics details with pagination and sorting options.
     * <p>
     * This method retrieves a paginated list of event graphics based on optional criteria such as event ID and whether
     * the graphic is a banner. The results are sorted based on the specified column and direction. Pagination parameters
     * are also specified by the 'start' and 'length' parameters, which determine the page index and size respectively.
     * <p>
     * The response includes the total number of records, the number of filtered records (same as total here), and the data
     * for the current page formatted as a {@code Map<String, Object>}.
     *
     * @param eventId Optional identifier for filtering graphics by event.
     * @param isBanner Optional flag to filter graphics that are banners.
     * @param start The zero-based index of the first record to return.
     * @param length The number of records to return per page.
     * @param draw The draw counter from the DataTables plugin that initiated the request.
     * @param sortColumn The name of the column to sort the results by.
     * @param sortDirection The direction of the sort ('asc' or 'desc').
     * @return A {@link ResponseEntity} containing the paginated data and status code.
     * @location /api/eventGraphics/list
     * @permission All logged in users.
     */

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list(
            @RequestParam(name = "eventId", required = false) Long eventId,
            @RequestParam(name = "isBanner",required = false) Boolean isBanner,
            @RequestParam("start") int start,
            @RequestParam("length") int length,
            @RequestParam("draw") int draw,
            @RequestParam("sortColumn") String sortColumn,
            @RequestParam("sortDirection") String sortDirection) {

        Sort.Direction dir = "asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageable = PageRequest.of(start / length, length, Sort.by(dir, sortColumn));

        // Get data as pages
        Page<EventGraphicsAPIForDetailsPageDTO> page = eventGraphicsService.findByCriteriaForDetails(eventId, isBanner, pageable);

        Map<String, Object> json = new HashMap<>();
        json.put("draw", draw);
        json.put("recordsTotal", page.getTotalElements());
        json.put("recordsFiltered", page.getTotalElements());
        json.put("data", page.getContent());
        return ResponseEntity.ok(json);
    }

    /**
     * Handles the GET request to fetch a paginated summary of event graphics, customized by sorting preferences.
     * This endpoint is protected, requiring the requester to have 'ROLE_GRAPHICS' authority.
     * <p>
     * It constructs a {@link PageRequest} object using pagination parameters from the request and retrieves a page
     * of {@link EventGraphicsAPIForSummaryPageDTO} objects using the {@code eventGraphicsService}. The response includes
     * pagination metadata and a list of event graphics data formatted according to DataTables' expected JSON structure.
     * <p>
     * The sorting direction and sorting column are customizable through request parameters, allowing the user
     * to dynamically adjust the view on the client side.
     *
     * @param start         the offset index from which to start the pagination.
     * @param length        the size of the page to retrieve.
     * @param draw          a unique identifier for the request, typically used to maintain sync with the frontend.
     * @param sortColumn    the name of the column to sort by.
     * @param sortDirection the direction of sorting, 'asc' for ascending or 'desc' for descending.
     * @return a {@link ResponseEntity} containing the structured map of paginated data including total records,
     *         filtered records, and the actual data list, all compliant with the expected format for DataTables.
     * @location /api/eventGraphics/summary
     * @permission Those who has graphic management permission.
     */

    @PreAuthorize("hasAuthority('ROLE_GRAPHICS')")
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> summary(
            @RequestParam("start") int start,
            @RequestParam("length") int length,
            @RequestParam("draw") int draw,
            @RequestParam("sortColumn") String sortColumn,
            @RequestParam("sortDirection") String sortDirection) {

        Sort.Direction dir = "asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageable = PageRequest.of(start / length, length, Sort.by(dir, sortColumn));

        // Get data as pages
        Page<EventGraphicsAPIForSummaryPageDTO> page = eventGraphicsService.findBySummary(pageable);

        Map<String, Object> json = new HashMap<>();
        json.put("draw", draw);
        json.put("recordsTotal", page.getTotalElements());
        json.put("recordsFiltered", page.getTotalElements());
        json.put("data", page.getContent());
        return ResponseEntity.ok(json);
    }
}
