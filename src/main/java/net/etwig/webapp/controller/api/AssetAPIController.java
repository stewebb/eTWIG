package net.etwig.webapp.controller.api;

import net.etwig.webapp.dto.AssetAPIDTO;
import net.etwig.webapp.services.AssetService;
import net.etwig.webapp.util.InvalidParameterException;
import net.etwig.webapp.util.WebReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/asset/")
public class AssetAPIController {

    @Autowired
    private AssetService assetService;

    /**
     * Handles POST requests to upload a file. This method takes a file and a Boolean flag indicating whether multiple files are expected to be uploaded.
     * The method first checks if the uploaded file is null or empty and throws an exception if it is. Currently, it is designed to handle single file uploads,
     * with placeholder logic ready for handling multiple files in the future.
     *
     * @param file The file to be uploaded.
     * @param isMultiple Boolean flag indicating if the upload involves multiple files. This functionality is not implemented yet.
     * @throws IOException if there is an issue during file I/O operations.
     * @throws InvalidParameterException if the uploaded file is null or empty.
     * @location /api/asset/add
     * @permission All logged in users.
     */

    @PostMapping(value = "add")
    public void add(@RequestParam("file") MultipartFile file, @RequestParam("isMultiple") Boolean isMultiple) throws IOException {

        // Check if the uploaded file is null or empty
        if(file == null) {
            throw new InvalidParameterException("Uploaded file is null or empty.");
        }
        // TODO: Upload multiple files.

        // Copy file and add related info
        assetService.uploadFile(file);
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
     * a page of assets based on various criteria such as the uploader user ID, a search value, pagination settings, and sorting directions.
     * The method supports dynamic sorting by column name and direction, and returns the assets in a structured JSON format suitable for
     * client-side frameworks like DataTables.
     *
     * @param uploader The ID of the user who uploaded the assets. This parameter is optional. If specified, the list will only include assets uploaded by this user.
     * @param searchValue The search value used to filter assets based on asset names. This parameter is optional and, if provided,
     *                    filters the list to include only those assets whose names contain the specified string.
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
