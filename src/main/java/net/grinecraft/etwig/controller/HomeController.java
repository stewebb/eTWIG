package net.grinecraft.etwig.controller;

import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import net.grinecraft.etwig.util.type.NavBar;

@Controller
public class HomeController {
	
    //@Autowired
    //Configuration configuration;
	
	@RequestMapping("/")  
	public String home(Model model) throws Exception{
		
    	//HashMap<String, Object> templateMap = new HashMap<String, Object>();
		//templateMap.put("navbar", NavBar.HOME);
		
    	//Template template = configuration.getTemplate("home.ftl");
        //return FreeMarkerTemplateUtils.processTemplateIntoString(template, templateMap);
		
		model.addAttribute("navbar", NavBar.HOME);
		return "home";
	}
}
