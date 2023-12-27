/**
 * eTWIG - The event and banner management software for residential halls and student unions.
 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
 * @license: MIT
 * @author: Steven Webb [xiaoancloud@outlook.com]
 * @website: https://etwig.grinecraft.net
 * @function: The controller for the event calendar / planner page.. 
 */

package net.grinecraft.etwig.controller.events;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import net.grinecraft.etwig.util.type.NavBar;

@Controller
public class CalendarController {

	/**
	 * The calendar page.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/events/calendar")  
	public String calendar(Model model) throws Exception{
	
        model.addAttribute("navbar", NavBar.CALENDAR);
		return "events/calendar";
	}
}
