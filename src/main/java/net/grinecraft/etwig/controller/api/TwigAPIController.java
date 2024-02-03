/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The controller for all public TWIG related APIs.
 	*/

package net.grinecraft.etwig.controller.api;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.grinecraft.etwig.dto.AssetBasicInfoDTO;
import net.grinecraft.etwig.dto.TwigTemplateBasicInfoDTO;
import net.grinecraft.etwig.dto.TwigTemplateDTO;
import net.grinecraft.etwig.services.AssetService;
import net.grinecraft.etwig.services.TwigService;
import net.grinecraft.etwig.services.WeekService;
import net.grinecraft.etwig.util.DateUtils;
import net.grinecraft.etwig.util.NumberUtils;
import net.grinecraft.etwig.util.WebReturn;

@RestController
public class TwigAPIController {

	@Autowired
	TwigService twigService;
	
	@Autowired
	WeekService weekService;
	
	@Autowired
	AssetService assetService;

	/**
	 * Get the TWIG template by a specific given id.
	 * @param eventId The ID of the template
	 * @return
	 * @throws Exception
	 * @authentication False
	 */
	
	@RequestMapping("/api/public/getTwigTemplateById")  
	public Map<String, Object> getTwigTemplateById(@RequestParam String templateId) throws Exception{
		Long templateIdNum = NumberUtils.safeCreateLong(templateId);

		if(templateIdNum == null) {
			return WebReturn.errorMsg("templateId is invalid. It must be an Integer.", false);
		} 
			
		Map<String, Object> myReturn = WebReturn.errorMsg(null, true);
	    myReturn.put("template", twigService.getTwigTemplateDesignById(templateIdNum));
		
		return myReturn;
	}

	/**
	 * Get the "week" information by a given date.
	 * @param date The given date in yyyy-mm-dd format.
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("/api/public/getWeekByDate")  
	public Map<String, Object> getWeekByDate(@RequestParam String date) throws Exception{
		
		LocalDate givenDate = DateUtils.safeParseDate(date, "yyyy-MM-dd");
		if(givenDate == null) {
			return WebReturn.errorMsg("date is invalid. It must be yyyy-mm-dd.", false);
		}
		
		Map<String, Object> myReturn = WebReturn.errorMsg(null, true);
	    myReturn.put("week", weekService.getWeekByDate(givenDate));
		return myReturn;
	}
	
	@RequestMapping("/api/public/getTwigTemplateByPortfolioAndDate")  
	public TwigTemplateDTO getTwigTemplateByPortfolioAndDate(@RequestParam(required=false) Long portfolioId, @RequestParam String date) throws Exception{
		return twigService.getTwigTemplateByDateAndPortfolio(DateUtils.safeParseDate(date, "yyyy-MM-dd"), portfolioId);
	}
	
	@RequestMapping("/api/private/getTwigTemplateList")  
    public Page<TwigTemplateBasicInfoDTO> getTwigTemplateList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return twigService.getTwigTemplateList(page, size);
    }
	

}
