package net.etwig.webapp.controller.api;

import net.etwig.webapp.dto.AssetAPIDTO;
import net.etwig.webapp.dto.graphics.BannerRequestDetailsDTO;
import net.etwig.webapp.services.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/asset/")
public class AssetAPIController {

    @Autowired
    private AssetService assetService;

    @GetMapping("/add")
    public Object add(@RequestParam Long eventId) {
        return null;
    }

    @GetMapping("/edit")
    public Object edit(@RequestParam Long eventId) {
        return null;
    }


    @GetMapping("/view")
    public Object view(@RequestParam Long eventId) {
        return null;
    }


    @GetMapping("/remove")
    public Object remove(@RequestParam Long eventId) {
        return null;
    }

    /**
     * Handles the HTTP GET request to list assets with pagination and sorting parameters. This endpoint allows clients to request
     * a page of assets based on various criteria such as the uploader user ID, pagination settings, and sorting directions.
     * The method supports dynamic sorting by column name and direction, and returns the assets in a structured JSON format suitable for
     * client-side frameworks like DataTables.
     *
     * @param uploader The ID of the user who uploaded the assets. This parameter is optional. If specified, the list will only include assets uploaded by this user.
     * @param start The zero-based index of the first record to return, effectively the starting point of pagination.
     * @param length The number of records to return in each page, defining the size of the pagination.
     * @param draw An integer sent by the client-side to identify the draw count, used primarily for handling asynchronous requests in DataTables.
     * @param sortColumn The name of the column to sort the results by.
     * @param sortDirection The direction of the sort, can be 'asc' for ascending or 'desc' for descending.
     * @return A {@code ResponseEntity<Map<String, Object>>} containing the draw count, total records, filtered records count, and the list of assets.
     * @location /api/asset/list
     * @permission All logged in users.
     */

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list(
            @RequestParam(name = "uploader", required = false) Long uploader,
            @RequestParam("start") int start,
            @RequestParam("length") int length,
            @RequestParam("draw") int draw,
            @RequestParam("sortColumn") String sortColumn,
            @RequestParam("sortDirection") String sortDirection,
            @RequestParam(name = "search[value]", required = false) String searchValue) {

        Sort.Direction dir = "asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageable = PageRequest.of(start / length, length, Sort.by(dir, sortColumn));

        // Get data as pages
        Page<AssetAPIDTO> page = assetService.findAssetsByCriteria(uploader, searchValue, pageable);

        Map<String, Object> json = new HashMap<>();
        json.put("draw", draw);
        json.put("recordsTotal", page.getTotalElements());
        json.put("recordsFiltered", page.getTotalElements());
        json.put("data", page.getContent());
        return ResponseEntity.ok(json);
    }

}
