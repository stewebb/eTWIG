package net.etwig.webapp.controller.api;

import net.etwig.webapp.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@RequestMapping("/api/position/")
public class PositionAPIController {

    @Autowired
    private UserRoleService userRoleService;

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

    /**
     * Retrieves the current position of the logged-in user.
     * <p>
     * This method responds to a GET request at the "/current" endpoint.
     * It calls the {@code getMyLoggedInPosition} method of {@code userRoleService}
     * to obtain the position data of the currently authenticated user.
     * </p>
     *
     * @return the position ID of the currently logged-in user as a {@code Long}.
     * @location /api/position/current
     * @permission All logged in users
     */

    @GetMapping("/current")
    public Long current() {
        return null;//userRoleService.getMyLoggedInPosition();
        //TODO
    }

    /**
     * Endpoint to retrieve all roles associated with the currently logged-in user.
     *
     * @return A map of role IDs to role names.
     * @throws NullPointerException if no user position data is available in the session.
     * @location /api/position/my
     * @permission All logged in users
     */

    @GetMapping("/my")
    public HashMap<Long, String> my() {
        return null;//userRoleService.getMyPositions();
        //TODO
    }
}
