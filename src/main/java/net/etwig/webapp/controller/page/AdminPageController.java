package net.etwig.webapp.controller.page;

import net.etwig.webapp.dto.user.CurrentUserBasicInfoDTO;
import net.etwig.webapp.services.PortfolioService;
import net.etwig.webapp.services.UserRoleService;
import net.etwig.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/admin")
public class AdminPageController {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

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
    public String userList(Model model){
        model.addAttribute("portfolios", portfolioService.findAllPortfolios());
        return "admin/user_list";
    }

    /**
     * Serves the page for listing portfolios in the administrative section.
     * This method is accessible only to users with the 'ROLE_ADMIN' authority,
     * ensuring that sensitive data is managed by authorized personnel only.
     *
     * @return The path to the portfolio list view within the admin directory.
     * @location /admin/portfolioList.do
     * @permission Site administrators only.     */

    @GetMapping("/portfolioList.do")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String portfolioList(){
        return "admin/portfolio_list";
    }

    @GetMapping("/userDetails.do")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String userDetails(Model model, @RequestParam("userId") Long userId) {

        // Get user details excludes password
        CurrentUserBasicInfoDTO userBasicInfo = userService.findById(userId);
        if (userBasicInfo == null) {
            model.addAttribute("reason", "User with id=" + userId + " does not exist.");
            return "error_page";
        }

        System.out.println(userRoleService.findByUserId(userId));

        model.addAttribute("selectedUserDetails", userBasicInfo);
        model.addAttribute("selectedUserRoles", userRoleService.findByUserId(userId));
        model.addAttribute("portfolios", portfolioService.findAllPortfolios());
        return "admin/user_details";
    }

}
