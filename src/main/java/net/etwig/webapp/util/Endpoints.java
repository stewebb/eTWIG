package net.etwig.webapp.util;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class Endpoints {
    // Home
    public final String HOME            = "/home.do";

    // Events
    private final String EVENTS          = "/events/";
    private final String EVENTS_ADD      = "/events/add.do";
    private final String EVENTS_CALENDAR = "/events/calendar.do";
    private final String EVENTS_EDIT     = "/events/edit.do";
    private final String EVENTS_IMPORT   = "/events/import.do";
    private final String EVENTS_LIST     = "/events/list.do";

    // Graphics

}
