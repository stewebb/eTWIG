package net.grinecraft.etwig.controller.graphics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.grinecraft.etwig.dto.TwigTemplateBasicInfoDTO;
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
		model.addAttribute("portfolioSeparatedCalendar", portfolioService.getPortfolioListBySeparatedCalendar(true));		
		return "graphics/twigTemplate_edit";
	}
	
	@RequestMapping("/graphics/twigTemplate/design")  
	@PostAuthorize("hasAnyAuthority('ROLE_ADMINISTRATOR', 'ROLE_GRAPHICS_MANAGER')")
	public String design(Model model, @RequestParam(required=false) String edit, @RequestParam(required=false) String templateId) throws Exception{
		String returnTemplate = "";
		
		// Get mode (allow null)
		Boolean isEdit =  BooleanUtils.toBooleanObject(edit);
		
		// Edit mode
		if(isEdit == Boolean.TRUE) {
			
			// Get the numeric value of templateId, and null check.
			Long templateIdNum = NumberUtils.safeCreateLong(templateId);
			if(templateIdNum == null) {
				returnTemplate = addErrorInfo(model, "TemplateId is required.");
			}
			
			else {
				
				// Positive number, a specific portfolio
				if(templateIdNum >= 0) {
					TwigTemplateBasicInfoDTO templateBasicInfo = twigService.getTwigTemplateBasicInfoById(templateIdNum);
					
					//	Template not found
					if(templateBasicInfo == null) {
						returnTemplate = addErrorInfo(model, "Cannot find a TWIG Template with id=" + templateId + ".");
					}
					
					//	Template found
					else {
						model.addAttribute("template", templateBasicInfo);
						model.addAttribute("portfolioSeparatedCalendar", portfolioService.getPortfolioListBySeparatedCalendar(true));		
						System.out.println(templateBasicInfo);
						returnTemplate = "graphics/twigTemplate_design_edit";
					}
				}
				
				// Negative number, all portfolios
				else {
					returnTemplate = "graphics/twigTemplate_design_edit";
				}	
			}
		}
		
		// Add mode
		else if (isEdit == Boolean.FALSE) {
			returnTemplate = "graphics/twigTemplate_design_add";
		}
		
		// Default page (isEdit is null)
		else {
			returnTemplate = "graphics/twigTemplate_design_default";
		}
		
		return returnTemplate;
	}
	
	private String addErrorInfo(Model model, String reason) {
		model.addAttribute("embedded", false);
		model.addAttribute("reason", reason);
		return "_errors/custom_error";
	}
}
