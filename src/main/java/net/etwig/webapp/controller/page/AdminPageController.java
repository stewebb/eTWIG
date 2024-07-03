package net.etwig.webapp.controller.page;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminPageController {

    @GetMapping("/userList.do")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String userList(){
        return "admin/user_list";
    }
}
