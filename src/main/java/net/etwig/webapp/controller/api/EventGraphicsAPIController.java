package net.etwig.webapp.controller.api;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/eventGraphics/")
public class EventGraphicsAPIController {

    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Map<String, Object> eventInfo) {
        return null;
    }

    @PostMapping("/edit")
    public Map<String, Object> edit(@RequestBody Map<String, Object> eventInfo) {
        return null;
    }

    @PostMapping("/view")
    public Map<String, Object> view(@RequestParam Long eventId) {
        return null;
    }

    @PostMapping("/remove")
    public Map<String, Object> remove(@RequestBody Map<String, Object> eventInfo) {
        return null;
    }
}
