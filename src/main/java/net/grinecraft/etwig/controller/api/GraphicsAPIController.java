package net.grinecraft.etwig.controller.api;

import net.grinecraft.etwig.dto.graphics.EventGraphicsListDTO;
import net.grinecraft.etwig.dto.graphics.PendingRequestsBasicInfoDTO;
import net.grinecraft.etwig.services.EventGraphicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/private/")
public class GraphicsAPIController {

    @Autowired
    private EventGraphicsService eventGraphicsService;

    @PostAuthorize("hasAuthority('ROLE_GRAPHICS')")
    @GetMapping(value = "/eventGraphicsList")
    public Page<EventGraphicsListDTO> eventGraphicsList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) throws Exception {
        return eventGraphicsService.eventGraphicsList(page, size);
    }
}
