package net.grinecraft.etwig.controller.publicPages;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import freemarker.template.Configuration;
import freemarker.template.Template;

@RestController
public class EventsController {

	@Autowired
    Configuration configuration;
	
	@RequestMapping("/public/events")  
	public String events() throws Exception{  
		Template template = configuration.getTemplate("public/events.ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, new HashMap<>());
	}
}
