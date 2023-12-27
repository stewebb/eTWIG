package net.grinecraft.etwig.controller.events;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import net.grinecraft.etwig.util.type.NavBar;

@Controller
public class CalendarController {

	//@Autowired
	//PortfolioService portfolioService;
	
	@RequestMapping("/events/calendar")  
	public String events(Model model) throws Exception{
	
        model.addAttribute("navbar", NavBar.CALENDAR);
		return "events/calendar";
	}
}
