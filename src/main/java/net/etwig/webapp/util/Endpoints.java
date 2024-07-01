package net.etwig.webapp.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Endpoints {
    // Home

    // Events
    EVENTS          ("/events"),
    EVENTS_ADD      ("/events/add.do"),
    EVENTS_CALENDAR ("/events/calendar.do"),
    EVENTS_EDIT     ("/events/edit.do"),
    EVENTS_IMPORT   ("/events/import.do"),
    EVENTS_LIST     ("/events/list.do");

    private final String endpoint;
}
