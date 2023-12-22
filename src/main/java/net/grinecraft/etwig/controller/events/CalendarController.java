package net.grinecraft.etwig.controller.events;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.grinecraft.etwig.services.PortfolioService;
import net.grinecraft.etwig.util.DateUtils;
import net.grinecraft.etwig.util.type.NavBar;

@Controller
public class CalendarController {

	//@Autowired
    //Configuration configuration;
	
	@Autowired
	PortfolioService portfolioService;
	
	@RequestMapping("/events/calendar")  
	public String events(Model model, @RequestParam(required = false) String month) throws Exception{
		
		// Parse month from URL parameter
		LocalDate givenMonth = DateUtils.safeParseDate(month, "yyyy-MM");
		if(givenMonth == null) {
			givenMonth = LocalDate.now();
		}
        
		// Calculate months and return to the template
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
		HashMap<String, String> dateOptions = new HashMap<String, String>();
		dateOptions.put("TODAY", LocalDate.now().format(formatter));
		dateOptions.put("THIS_MONTH", givenMonth.format(formatter));
		dateOptions.put("LAST_MONTH", DateUtils.findFirstDayOfLastMonth(givenMonth).format(formatter));
		dateOptions.put("NEXT_MONTH", DateUtils.findFirstDayOfNextMonth(givenMonth).format(formatter));
		
        model.addAttribute("navbar", NavBar.CALENDAR);
        model.addAttribute("dateOptions", dateOptions);
        //model.addAttribute("portfolio", portfolioService.getPortfolioList());
		return "events/calendar";
	}
}
