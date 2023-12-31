/**
 * eTWIG - The event and banner management software for residential halls and student unions.
 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
 * @license: MIT
 * @author: Steven Webb [xiaoancloud@outlook.com]
 * @website: https://etwig.grinecraft.net
 * @function: The controller for all public TWIG related APIs.
 */

package net.grinecraft.etwig.controller.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.grinecraft.etwig.services.TwigService;
import net.grinecraft.etwig.util.NumberUtils;
import net.grinecraft.etwig.util.WebReturn;

@RestController
public class TwigAPIController {

	@Autowired
	TwigService twigService;

	/**
	 * Get the TWIG template by a specific given id.
	 * @param eventId The ID of the template
	 * @return
	 * @throws Exception
	 * @authentication False
	 */
	
	@RequestMapping("/api/public/getTwigTemplateById")  
	public Map<String, Object> getEventById(@RequestParam String templateId) throws Exception{
		Long templateIdNum = NumberUtils.safeCreateLong(templateId);

		if(templateIdNum == null) {
			return WebReturn.errorMsg("templateId is invalid. It must be an Integer.", false);
		} 
			
		Map<String, Object> myReturn = WebReturn.errorMsg(null, true);
	    myReturn.put("template", twigService.getTwigTemplateById(templateIdNum));
		
		return myReturn;
	}

}
