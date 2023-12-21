package net.grinecraft.etwig.controller;

import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import net.grinecraft.etwig.util.type.NavBar;

@Controller
public class DashboardController {
	
	@RequestMapping("/")  
	public String home(Model model) throws Exception{
		
		model.addAttribute("navbar", NavBar.HOME);
		return "dashboard";
	}
}
