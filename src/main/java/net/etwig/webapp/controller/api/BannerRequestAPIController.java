package net.etwig.webapp.controller.api;

import net.etwig.webapp.dto.BannerRequestAPIForEventPageDTO;
import net.etwig.webapp.model.GraphicsRequest;
import net.etwig.webapp.services.GraphicsRequestService;
import net.etwig.webapp.util.InvalidParameterException;
import net.etwig.webapp.util.NumberUtils;
import net.etwig.webapp.util.WebReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/bannerRequest/")
public class BannerRequestAPIController {

    @Autowired
    private GraphicsRequestService graphicsRequestService;

    @GetMapping("/add")
    public ResponseEntity<String> add(@RequestBody Map<String, Object> eventInfo) {
        // TODO Add an banner request
        return new ResponseEntity<>("Method not implemented.", HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("/approve")
    public Map<String, Object> edit(@RequestBody Map<String, Object> decisionInfo) throws Exception {
        Long requestId = NumberUtils.safeCreateLong(decisionInfo.get("id").toString());
        if(requestId == null) {
            throw new InvalidParameterException("Request ID must not be null.");
        }

        if(requestId <= 0){
            throw new InvalidParameterException("Request ID must be a positive number.");
        }

        // Check the existence
        GraphicsRequest currentRequest = graphicsRequestService.findById(requestId);
        if(currentRequest == null) {
            return WebReturn.errorMsg("The graphics request of requestId= " + requestId + " does not exist.", false);
        }

        graphicsRequestService.approveRequest(currentRequest, decisionInfo);
        return WebReturn.errorMsg(null, true);
    }

    @PostMapping("/view")
    public Map<String, Object> view(@RequestBody Map<String, Object> eventInfo) {
        return null;
    }

    @PostMapping("/remove")
    public Map<String, Object> remove(@RequestBody Map<String, Object> eventInfo) {
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
     * @location /api/request/list
     * @permission All logged in users.
     * @return {@code ResponseEntity<Map<String, Object>>} containing the paginated list of banner requests<br>
     * along with additional information like total records count, filtered count, and the client's draw count.<br>
     * This response is in the form of a JSON object structured for client-side processing.
     */

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
        Page<BannerRequestAPIForEventPageDTO> page = graphicsRequestService.findRequestsByCriteria(eventId, isApproved, pageable);

        Map<String, Object> json = new HashMap<>();
        json.put("draw", draw);
        json.put("recordsTotal", page.getTotalElements());
        json.put("recordsFiltered", page.getTotalElements());
        json.put("data", page.getContent());
        return ResponseEntity.ok(json);
    }
}
