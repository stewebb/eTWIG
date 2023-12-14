package net.grinecraft.etwig.controller.user;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import freemarker.template.Configuration;
import freemarker.template.Template;


@RestController
public class LoginController {
    
	@Autowired
    Configuration configuration;
	
    @GetMapping("/user/login")
    public String login() throws Exception {
    	Template template = configuration.getTemplate("user/login.ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, new HashMap<>());
    	//return "login";
    }
}