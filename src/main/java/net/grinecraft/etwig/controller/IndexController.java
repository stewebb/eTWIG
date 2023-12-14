package net.grinecraft.etwig.controller;

import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Template;
import freemarker.template.Configuration;

@RestController
public class IndexController {
	
    @Autowired
    Configuration configuration;
    
    //@Autowired
    //EventService eventService;
	
	@RequestMapping("/")  
	public String index() throws Exception{  
		Template template = configuration.getTemplate("index.ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, new HashMap<>());
	}
}
