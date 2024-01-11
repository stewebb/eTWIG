package net.grinecraft.etwig.controller.graphics;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TwigTemplateController {
	
	@RequestMapping("/graphics/twigTemplate/view")  
	@PostAuthorize("hasAnyAuthority('ROLE_ADMINISTRATOR', 'ROLE_GRAPHICS_MANAGER')")
	public String view(Model model) throws Exception{
		return "graphics/twigTemplate_view";
	}
}
