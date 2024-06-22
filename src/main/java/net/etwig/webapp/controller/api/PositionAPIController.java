package net.etwig.webapp.controller.api;

import net.etwig.webapp.dto.user.CurrentUserPositionDTO;
import net.etwig.webapp.services.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
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

    /**
     * Web endpoint to switch the current user's position to the specified role ID.
     * <p>
     * This GET request handles the "/switch" endpoint and allows the user to switch their position
     * by providing a role ID as a query parameter. This method acts as a wrapper, delegating the actual
     * switching logic to the {@code switchPosition} method in {@code userSessionService}.
     * </p>
     *
     * It verifies the user's session and checks if the requested role ID is associated with the user.
     * If the check is successful, it updates the user's position and returns the new position.
     * If the role ID is not valid or the user is not permitted to switch to this role, it throws an
     * {@link InvalidPositionException} and return HTTP status 403 (Forbidden).
     *
     * @param userRoleId The role ID to which the user intends to switch, passed as a query parameter.
     * @return The new position of the user as a {@code CurrentUserPositionDTO.Position} upon successful role change.
     * @throws InvalidPositionException If the role ID is invalid or the user is not permitted to switch to the
     * requested role.
     */

    @GetMapping("/switch")
    public CurrentUserPositionDTO.Position switchPosition(@RequestParam Long userRoleId){
        return userSessionService.switchPosition(userRoleId);
    }
}
