package net.etwig.webapp.controller.api;

import net.etwig.webapp.dto.BannerRequestDetailsDTO;
import net.etwig.webapp.model.GraphicsRequest;
import net.etwig.webapp.services.BannerRequestService;
import net.etwig.webapp.util.InvalidParameterException;
import net.etwig.webapp.util.NumberUtils;
import net.etwig.webapp.util.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/bannerRequest/")
public class BannerRequestAPIController {

    @Autowired
    private BannerRequestService bannerRequestService;

    @GetMapping("/add")
    public ResponseEntity<String> add(@RequestBody Map<String, Object> eventInfo) {
        // TODO Add an banner request
        return new ResponseEntity<>("Method not implemented.", HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * Processes the approval of a graphics request via a POST request.
     * <p>
     * This method is secured with an authorization check, ensuring that only users
     * with the 'ROLE_GRAPHICS' authority can invoke it. It retrieves the request ID from
     * the provided decision information, validates it, and proceeds to approve the corresponding
     * graphics request if it exists.
     * </p>
     *
     * @param decisionInfo A map containing decision-related information such as the request ID.
     *                     This information is extracted from the body of the POST request.
     * @return A {@link ResponseEntity} containing a map with a success message if the operation
     *         is successful.
     * @throws Exception Throws a generic exception if the approval process encounters any issues
     *                   not handled by more specific exceptions.
     * @throws InvalidParameterException If the request ID is null or not a positive number, indicating
     *                                   an invalid or inappropriate request parameter.
     * @throws RecordNotFoundException If no graphics request corresponds to the provided request ID.
     *
     * @location /api/bannerRequest/approve
     * @permission Those who has graphic management permission.
     */

    @PostMapping("/approve")
    @PostAuthorize("hasAuthority('ROLE_GRAPHICS')")
    public ResponseEntity<Map<String, String>> approve(@RequestBody Map<String, Object> decisionInfo) throws Exception {
        Long requestId = NumberUtils.safeCreateLong(decisionInfo.get("id").toString());

        // Null and negative check
        if(requestId == null) {
            throw new InvalidParameterException("Request ID must not be null.");
        }

        if(requestId <= 0){
            throw new InvalidParameterException("Request ID must be a positive number.");
        }

        // Event existence check
        GraphicsRequest currentRequest = bannerRequestService.findById(requestId);
        if(currentRequest == null) {
            throw new RecordNotFoundException("The banner request of Request ID = " + requestId + " does not exist.");
        }

        bannerRequestService.approveRequest(currentRequest, decisionInfo);
        return ResponseEntity.ok().body(Map.of("message", "Banner approved successfully."));
    }

    /**
     * Handles the HTTP GET request to retrieve and view the details of a graphics request.
     * <p>
     * This endpoint is accessible via a GET request and expects a request parameter 'requestId'.
     * It utilizes the {@link BannerRequestService#findByIdWithDTO(Long)} method to fetch the request
     * details as a {@link BannerRequestDetailsDTO}. If the request is found, the corresponding DTO is returned,
     * otherwise, the method returns {@code null}.
     * </p>
     *
     * @param requestId The ID of the graphics request to retrieve, expected as a request parameter.
     * @return A {@link BannerRequestDetailsDTO} representing the details of the found graphics request,
     *         or {@code null} if no request is found with the given ID. The response body directly contains this DTO.
     * @location /api/bannerRequest/view
     * @permission All logged in users
     */

    @GetMapping("/view")
    public BannerRequestDetailsDTO view(@RequestParam Long requestId) {
        return bannerRequestService.findByIdWithDTO(requestId);
    }

    @PostMapping("/remove")
    public Map<String, Object> remove(@RequestBody Map<String, Object> eventInfo) {

        // TODO REMOVE A BANNER REQUEST
        return null;
    }

    /**
     * Handles the HTTP GET requests for listing event banner requests.
     * This method returns a paginated list of event banner requests based on the provided criteria.
     *
     * @param eventId Optional. The ID of the event to filter banner requests.<br>
     *                If not provided, requests for all events are considered.
     * @param isApproved Optional. Filter specifying if the returned banner requests should be approved ("true"),<br>
     *                   declined ("false"), pending ("null") or N/A ("na").<br>
     *                   If not provided, the default value is null (i.e., pending events).
     * @param start The zero-based page index of the paginated results that should be returned.<br>
     *              This parameter directly influences the {@code PageRequest} pagination offset.
     * @param length The size of the page to be returned, determining the number of records in each page.
     * @param draw An arbitrary integer sent by the client to identify each draw of data.<br>
     *             Typically used by client-side libraries like DataTables to sync server-client interactions.
     * @param sortColumn The name of the column to sort the results by.
     * @param sortDirection The direction of the sort (either "asc" for ascending or "desc" for descending).<br>
     *                      Defaults to "asc" if not correctly specified.
     * @return {@code ResponseEntity<Map<String, Object>>} containing the paginated list of banner requests<br>
     * along with additional information like total records count, filtered count, and the client's draw count.<br>
     * This response is in the form of a JSON object structured for client-side processing.
     * @location /api/bannerRequest/list
     * @permission All logged in users.
     */

    // TODO ADD A DETAILS BUTTON

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list(
            @RequestParam(name = "eventId", required = false) Long eventId,
            @RequestParam(name = "isApproved",required = false) String isApproved,
            @RequestParam("start") int start,
            @RequestParam("length") int length,
            @RequestParam("draw") int draw,
            @RequestParam("sortColumn") String sortColumn,
            @RequestParam("sortDirection") String sortDirection) {

        Sort.Direction dir = "asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageable = PageRequest.of(start / length, length, Sort.by(dir, sortColumn));

        // Get data as pages
        Page<BannerRequestDetailsDTO> page = bannerRequestService.findRequestsByCriteria(eventId, isApproved, pageable);

        Map<String, Object> json = new HashMap<>();
        json.put("draw", draw);
        json.put("recordsTotal", page.getTotalElements());
        json.put("recordsFiltered", page.getTotalElements());
        json.put("data", page.getContent());
        return ResponseEntity.ok(json);
    }

    /**
     * Retrieves the count of entities from the GraphicsRequest table based on the provided column and object value.
     *
     * @param column the name of the column in the GraphicsRequest table for counting entities
     * @param object the value to be matched in the specified column for counting entities
     * @return ResponseEntity containing a map with the count of entities
     * @throws IllegalArgumentException if the column name is null or empty, or if the object value is null
     * @location /api/bannerRequest/count
     * @permission All logged in users.
     */

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> count(@RequestParam String column, @RequestParam Object object) {
        return ResponseEntity.ok().body(Map.of("count", bannerRequestService.countByColumn(column, object)));
    }
}
