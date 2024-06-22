package net.etwig.webapp.controller.api;

import net.etwig.webapp.dto.user.CurrentUserPositionDTO;
import net.etwig.webapp.services.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/position/")
public class PositionAPIController {

    @Autowired
    private UserSessionService userSessionService;

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
    public Object list(@RequestParam Long eventId) {
        return null;
    }

    @GetMapping("/my")
    public CurrentUserPositionDTO my() {
        return userSessionService.validateSession().getPosition();
    }

    @GetMapping("/switch")
    public void switchPosition(){

    }
}
