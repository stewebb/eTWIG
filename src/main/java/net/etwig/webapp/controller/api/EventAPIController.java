package net.etwig.webapp.controller.api;

import net.etwig.webapp.util.InvalidActionException;
import net.etwig.webapp.util.ResourceAPI;
import net.etwig.webapp.util.type.APIMode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nsRest/private/")
public class EventAPIController implements ResourceAPI {

    // NS = Not Standard
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
}
