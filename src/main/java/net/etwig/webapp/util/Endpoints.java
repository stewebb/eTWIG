package net.etwig.webapp.util;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class Endpoints {

    // Admin
    private final String ADMIN                      = "/admin/";
    private final String ADMIN_PORTFOLIO_LIST       = "/admin/portfolioList.do";
    private final String ADMIN_USER_DETAILS         = "/admin/userDetails.do";
    private final String ADMIN_USER_LIST            = "/admin/userList.do";

    // Assets
    private final String ASSETS                     = "/assets/";
    private final String ASSETS_CONTENT             = "/assets/content.do";
    private final String ASSETS_LIST                = "/assets/list.do";
    private final String ASSETS_SELECTOR            = "assets/_selectFile.do";

    // Home
    private final String HOME                       = "/home.do";

    // Events
    private final String EVENTS                     = "/events/";
    private final String EVENTS_ADD                 = "/events/add.do";
    private final String EVENTS_BANNER_REQUEST      = "/events/bannerRequest.do";
    private final String EVENTS_CALENDAR            = "/events/calendar.do";
    private final String EVENTS_EDIT                = "/events/edit.do";
    private final String EVENTS_IMPORT              = "/events/import.do";
    private final String EVENTS_LIST                = "/events/list.do";

    // Graphics
    private final String GRAPHICS                   = "/graphics/";
    private final String GRAPHICS_APPROVAL_DETAILS  = "/graphics/approvalDetails.do";
    private final String GRAPHICS_APPROVAL_LIST     = "/graphics/approvalList.do";
    private final String GRAPHICS_SUMMARY_DETAILS   = "/graphics/summaryDetails.do";
    private final String GRAPHICS_SUMMARY_LIST      = "/graphics/summaryList.do";

    // User
    private final String USER                       = "/user/";
    private final String USER_LOGIN                 = "/user/login.do";
    private final String USER_LOGOUT                = "/user/logout.do";
    private final String USER_PROFILE               = "/user/profile.do";
    private final String USER_TOKEN_LOGIN           = "/user/tokenLogin.do";
}
