package net.grinecraft.etwig.controller.graphics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.grinecraft.etwig.dto.TwigTemplateBasicInfoDTO;
import net.grinecraft.etwig.dto.TwigTemplateDesignDTO;
import net.grinecraft.etwig.services.PortfolioService;
import net.grinecraft.etwig.services.TwigService;
import net.grinecraft.etwig.util.BooleanUtils;
import net.grinecraft.etwig.util.NumberUtils;

@Controller
public class TwigTemplateController {
	
	@Autowired
	TwigService twigService;
	
	@Autowired
	PortfolioService portfolioService;
	
	@RequestMapping("/graphics/twigTemplate/view")  
	@PostAuthorize("hasAnyAuthority('ROLE_ADMINISTRATOR', 'ROLE_GRAPHICS_MANAGER')")
	public String view(Model model) throws Exception{
		return "graphics/twigTemplate_view";
	}
	
	@RequestMapping("/graphics/twigTemplate/add")  
	@PostAuthorize("hasAnyAuthority('ROLE_ADMINISTRATOR', 'ROLE_GRAPHICS_MANAGER')")
	public String add(Model model) throws Exception{
		model.addAttribute("portfolioSeparatedCalendar", portfolioService.getPortfolioListBySeparatedCalendar(true));		
		return "graphics/twigTemplate_add";
	}
	
	@RequestMapping("/graphics/twigTemplate/edit")  
	@PostAuthorize("hasAnyAuthority('ROLE_ADMINISTRATOR', 'ROLE_GRAPHICS_MANAGER')")
	public String edit(Model model, @RequestParam String templateId) throws Exception{
		
		TwigTemplateBasicInfoDTO template = getTemplate(templateId);
		if(template == null) {
			return addErrorInfo(model, "The templateId is either not presented, a negative number, or cannot be found in the database.");
		}
		
		model.addAttribute("template", template);
		model.addAttribute("portfolioSeparatedCalendar", portfolioService.getPortfolioListBySeparatedCalendar(true));		
		return "graphics/twigTemplate_edit";
	}
	
	@RequestMapping("/graphics/twigTemplate/design")  
	@PostAuthorize("hasAnyAuthority('ROLE_ADMINISTRATOR', 'ROLE_GRAPHICS_MANAGER')")
	public String design(Model model, @RequestParam(required=false) String templateId) throws Exception{
		
		TwigTemplateBasicInfoDTO template = getTemplate(templateId);
		TwigTemplateDesignDTO design = twigService.getTwigTemplateDesignById(template.getId());
		
		if(template == null || design == null) {
			return addErrorInfo(model, "The templateId is either not presented, a negative number, or cannot be found in the database.");
		}
		
		System.out.println(design);
		model.addAttribute("design", design);
		return "graphics/twigTemplate_design";
	}
	
	private String addErrorInfo(Model model, String reason) {
		model.addAttribute("embedded", false);
		model.addAttribute("reason", reason);
		return "_errors/custom_error";
	}
	
	private TwigTemplateBasicInfoDTO getTemplate(String templateId) {
		
		// Get the numeric value of templateId, and null check.
		Long templateIdNum = NumberUtils.safeCreateLong(templateId);
		if(templateIdNum == null) {
			return null;
		}
					
		// This number must positive.
		if(templateIdNum < 0) {
			return null;
		}
			
		// Find template and also null check
		TwigTemplateBasicInfoDTO templateBasicInfo = twigService.getTwigTemplateBasicInfoById(templateIdNum);
		if(templateBasicInfo == null) {
			return null;
		}
			
		return templateBasicInfo;
	}
}
