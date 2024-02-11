package net.grinecraft.etwig.controller.api;

import net.grinecraft.etwig.dto.events.EventDetailsDTO;
import net.grinecraft.etwig.dto.graphics.EventGraphicsListDTO;
import net.grinecraft.etwig.dto.graphics.PendingRequestsBasicInfoDTO;
import net.grinecraft.etwig.model.Event;
import net.grinecraft.etwig.model.GraphicsRequest;
import net.grinecraft.etwig.services.EventGraphicsService;
import net.grinecraft.etwig.services.EventService;
import net.grinecraft.etwig.util.NumberUtils;
import net.grinecraft.etwig.util.WebReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/private/")
public class GraphicsAPIController {

    @Autowired
    private EventGraphicsService eventGraphicsService;

    @Autowired
    private EventService eventService;

    @PostAuthorize("hasAuthority('ROLE_GRAPHICS')")
    @GetMapping(value = "/eventGraphicsList")
    public Page<EventGraphicsListDTO> eventGraphicsList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) throws Exception {
        return eventGraphicsService.eventGraphicsList(page, size);
    }

    @PostAuthorize("hasAuthority('ROLE_GRAPHICS')")
    @PostMapping(value = "/addGraphicsForEvent")
    public Map<String, Object> addGraphicsForEvent(@RequestBody Map<String, Object> newGraphicsInfo){

        // Get current request
        Long eventId = NumberUtils.safeCreateLong(newGraphicsInfo.get("eventId").toString());

        // Invalid or negative eventId, add event.
        if(eventId == null || eventId <= 0) {
            return WebReturn.errorMsg("The eventId is invalid.", false);
        }

        // Check the existence
        EventDetailsDTO event = eventService.findById(eventId);
        if(event == null) {
            return WebReturn.errorMsg("The event with id= " + eventId + " does not exist.", false);
        }

        eventGraphicsService.addGraphics(newGraphicsInfo);
        return WebReturn.errorMsg(null, true);
    }
}
