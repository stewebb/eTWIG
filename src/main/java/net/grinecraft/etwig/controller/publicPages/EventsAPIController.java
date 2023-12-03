package net.grinecraft.etwig.controller.publicPages;

import java.util.Date;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.grinecraft.etwig.util.DateUtils;

@RestController
public class EventsAPIController {

	@RequestMapping("/public/_getEventsByWeek")  
	public Map<Integer, Object> getEventsByWeek(@RequestParam(required = false) String dateStr) throws Exception{
		Date localdate = DateUtils.safeParseDate(dateStr, "yyyy-MM-dd");
		System.out.println(localdate);
		
		return null;
	}
}
