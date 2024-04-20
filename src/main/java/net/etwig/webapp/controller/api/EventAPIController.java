package net.etwig.webapp.controller.api;

import net.etwig.webapp.services.EventService;
import net.etwig.webapp.util.ResourceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nsRest/private/")
public class EventAPIController {

    @Autowired
    private EventService eventService;

    // NS = Not Standard

    /*
    @Override
    public Object add() {
        return null;
    }

    @Override
    public Object edit() {
        return null;
    }

    @Override
    public Object view() {
        return null;
    }

    @Override
    public Object remove() {
        return null;
    }

    @GetMapping("/event")
    public Object event(@RequestParam String action, @RequestParam Long eventId){
        return switch (APIMode.fromString(action)) {
            case ADD -> add();
            case EDIT -> edit();
            case VIEW -> view();
            case REMOVE -> remove();
            default -> throw new InvalidActionException(action);
        };
    }

     */

    /**
     * Add
     * @param eventId
     * @return
     */
    @GetMapping("/event/add")
    public Object add(@RequestParam Long eventId) {
        return null;
    }

    @GetMapping("/event/edit")
    public Object edit(@RequestParam Long eventId) {
        return null;
    }

    /**
     * View the detail of an event.
     * @param eventId The ID of the event.
     * @return The event details according to the ID, if not found, return null;
     */

    @GetMapping("/event/view")
    public Object view(@RequestParam Long eventId) {
        return eventService.findById(eventId);
    }

    @GetMapping("/event/remove")
    public Object remove(@RequestParam Long eventId) {
        return null;
    }

    @GetMapping("/event/search")
    public Object search(@RequestParam Long search) {
        return null;
    }
}
