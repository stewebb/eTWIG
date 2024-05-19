/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The controller for all portfolio related APIs.
	*/

package net.etwig.webapp.controller.api;

import java.util.List;

import net.etwig.webapp.model.Portfolio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.etwig.webapp.services.PortfolioService;

@RestController
public class _PortfolioAPIController {

	@Autowired
	PortfolioService portfolioService;
	
	/**
	 * Get the portfolio information by its id.
	 *
	 * @param portfolioId
	 * @return
	 * @throws Exception
	 */
	
	//@RequestMapping("/api/public/getPortfolioById")
	//public Portfolio getPortfolioById(@RequestParam Long portfolioId) throws Exception{
	//	return portfolioService.findById(portfolioId);
			
		//Map<String, Object> myReturn = WebReturn.errorMsg(null, true);
	    //myReturn.put("portfolio", portfolioService.getPortfolioById(portfolioId));
		//
		//return myReturn;
	//}

	@RequestMapping("/api/public/getPortfolioWithSeparateCalendar")
	public List<Portfolio> getPortfolioWithSeparateCalendar(){
		return portfolioService.getPortfolioListBySeparatedCalendar(true);
	}
}
