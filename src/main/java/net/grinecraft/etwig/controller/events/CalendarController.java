/**
	 * eTWIG - The event management software for university communities.
	 * @copyright: Copyright (c) 2024 Steven Webb
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: The controller for the event calendar / planner page.. 
 */

package net.grinecraft.etwig.controller.events;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.grinecraft.etwig.util.BooleanUtils;

@Controller
public class CalendarController {

	/**
	 * The calendar page.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("/events/calendar")  
	public String calendar(Model model, @RequestParam(required = false) String embedded) throws Exception{
		model.addAttribute("embedded", BooleanUtils.toBoolean(embedded));
		return "events/calendar";
	}
}
