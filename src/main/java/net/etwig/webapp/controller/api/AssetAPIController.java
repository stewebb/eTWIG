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

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list(
            @RequestParam(name = "uploader", required = false) Long uploader,
            @RequestParam("start") int start,
            @RequestParam("length") int length,
            @RequestParam("draw") int draw,
            @RequestParam("sortColumn") String sortColumn,
            @RequestParam("sortDirection") String sortDirection) {

        Sort.Direction dir = "asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageable = PageRequest.of(start / length, length, Sort.by(dir, sortColumn));

        // Get data as pages
        Page<AssetAPIDTO> page = assetService.findAssetsByCriteria(uploader, pageable);

        Map<String, Object> json = new HashMap<>();
        json.put("draw", draw);
        json.put("recordsTotal", page.getTotalElements());
        json.put("recordsFiltered", page.getTotalElements());
        json.put("data", page.getContent());
        return ResponseEntity.ok(json);
    }

}
