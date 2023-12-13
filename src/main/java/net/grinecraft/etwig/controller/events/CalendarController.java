package net.grinecraft.etwig.controller.events;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import freemarker.template.Configuration;
import freemarker.template.Template;
import net.grinecraft.etwig.services.PortfolioService;

@RestController
public class CalendarController {

	@Autowired
    Configuration configuration;
	
	@Autowired
	PortfolioService portfolioService;
	
	@RequestMapping("/events/calendar")  
	public String events() throws Exception{  
		HashMap<String, Object> templateMap = new HashMap<String, Object>();
		templateMap.put("portfolio", portfolioService.getPortfolioList());
		
		Template template = configuration.getTemplate("events/calendar.ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, templateMap);
	}
}
