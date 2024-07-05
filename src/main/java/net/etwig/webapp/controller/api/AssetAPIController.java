package net.etwig.webapp.controller.api;

import net.etwig.webapp.dto.AssetAPIDTO;
import net.etwig.webapp.services.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/asset/")
public class AssetAPIController {

    @Autowired
    private AssetService assetService;

    /**
     * Handles POST requests to upload one or more files. This method processes each file in the list
     * and calls a service method to handle the actual file upload. The method checks if any file in the
     * list is empty and sets the return status to false if at least one file is empty, otherwise,
     * it continues to process the upload. The method is designed to support multiple file uploads.
     *
     * @param files A list of files to be uploaded.
     * @return true if all files are uploaded successfully; false if at least one file is empty.
     * @throws IOException if there is an issue during file I/O operations.
     * @location /api/asset/list
     * @permission All logged-in users are allowed to perform this operation.
     */

    @PostMapping(value = "add")
    public boolean add(@RequestParam("files") List<MultipartFile> files) throws IOException {
        boolean status = true;
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                status = false;
                continue;
            }
            assetService.uploadFile(file);
        }
        return status;
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
            @RequestParam(name = "search[value]", required = false) String searchValue
    ) {

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
