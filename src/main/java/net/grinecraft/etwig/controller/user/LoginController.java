package net.grinecraft.etwig.controller.user;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import freemarker.template.Configuration;
import freemarker.template.Template;

import net.grinecraft.etwig.util.type.NavBar;

@RestController
public class LoginController {
    
	@Autowired
    Configuration configuration;
	
    @GetMapping("/user/login")
    public String login() throws Exception {
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal()))
                            ? authentication.getName()
                            : "guest";
        
        System.out.println(username);
        
        HashMap<String, Object> templateMap = new HashMap<String, Object>();
		templateMap.put("navbar", NavBar.LOGIN);
		
    	Template template = configuration.getTemplate("user/login.ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, templateMap);
    }
}