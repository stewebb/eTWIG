package net.grinecraft.etwig.controller;

import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Template;
import net.grinecraft.etwig.util.type.NavBar;
import freemarker.template.Configuration;

@RestController
public class HomeController {
	
    @Autowired
    Configuration configuration;
	
	@RequestMapping("/")  
	public String home() throws Exception{  
    	HashMap<String, Object> templateMap = new HashMap<String, Object>();
		templateMap.put("navbar", NavBar.HOME);
		
    	Template template = configuration.getTemplate("home.ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, templateMap);
	}
}
