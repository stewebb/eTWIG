package net.grinecraft.etwig.controller.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import freemarker.template.Configuration;
import net.grinecraft.etwig.services.PortfolioService;
import net.grinecraft.etwig.util.type.NavBar;

@Controller
public class CalendarController {

	//@Autowired
    //Configuration configuration;
	
	@Autowired
	PortfolioService portfolioService;
	
	@RequestMapping("/events/calendar")  
	public String events(Model model) throws Exception{  
		//HashMap<String, Object> templateMap = new HashMap<String, Object>();
		//templateMap.put("navbar", NavBar.EVENT);
		//templateMap.put("portfolio", portfolioService.getPortfolioList());
		
		//Template template = configuration.getTemplate("events/calendar.ftl");
        //return FreeMarkerTemplateUtils.processTemplateIntoString(template, templateMap);
        
        model.addAttribute("navbar", NavBar.EVENT);
        model.addAttribute("portfolio", portfolioService.getPortfolioList());
		return "events/calendar";
	}
}
