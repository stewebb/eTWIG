package net.grinecraft.etwig.controller.api;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.grinecraft.etwig.util.type.EventTimeUnit;

@RestController
public class EventsEditAPIController {

	@RequestMapping(value = "/api/addEvent", method = RequestMethod.POST)
    public Map<String, Object> addEvent(@RequestBody Map<String, Object> eventInfo) {
        System.out.println(eventInfo);
        
        String timeUnit = eventInfo.get("timeUnit").toString();
        System.out.println(timeUnit);
        
        EventTimeUnit eventUnit = EventTimeUnit.fromString(timeUnit);
        
        System.out.println(eventUnit);
        
        return eventInfo;
    }
}
