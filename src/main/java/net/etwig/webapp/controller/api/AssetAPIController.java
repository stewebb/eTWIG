package net.etwig.webapp.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/asset/")
public class AssetAPIController {

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


}
