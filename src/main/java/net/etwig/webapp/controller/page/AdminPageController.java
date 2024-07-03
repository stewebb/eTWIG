package net.etwig.webapp.controller.page;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminPageController {

    /**
     * Handles the GET request to display the user list view in the admin panel.
     * This method serves the page where administrators can view and manage users.
     * <p>
     * Access to this method is restricted to users with the 'ROLE_ADMIN' authority, ensuring that only authorized
     * administrators can view the user list.
     *
     * @return The path to the user list view template within the admin directory. This template renders the user
     * management interface.
     * @location /admin/userList.do
     * @permission Site administrators only.
     */

    @GetMapping("/userList.do")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String userList(){
        return "admin/user_list";
    }
}
