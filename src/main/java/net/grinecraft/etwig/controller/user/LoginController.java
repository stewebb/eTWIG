package net.grinecraft.etwig.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import net.grinecraft.etwig.util.type.NavBar;

@Controller
public class LoginController {
    
	//@Autowired
    //Configuration configuration;
	
    @GetMapping("/user/login")
    public String login(Model model) throws Exception {
    	
    	//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //String username = (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal()))
        //                    ? authentication.getName()
        //                    : "guest";
        
        //System.out.println(username);
        
        //HashMap<String, Object> templateMap = new HashMap<String, Object>();
		//templateMap.put("navbar", NavBar.LOGIN);
		
    	//Template template = configuration.getTemplate("user/login.ftl");
        //return FreeMarkerTemplateUtils.processTemplateIntoString(template, templateMap);
        
        model.addAttribute("navbar", NavBar.LOGIN);
		return "user/login";
    }
}