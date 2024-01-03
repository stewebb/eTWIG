package net.grinecraft.etwig.controller.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.grinecraft.etwig.services.PortfolioService;
import net.grinecraft.etwig.util.NumberUtils;
import net.grinecraft.etwig.util.WebReturn;

@RestController
public class PortfolioAPIController {

	@Autowired
	PortfolioService portfolioService;
	
	/**
	 * Get the portfolio information by it's id.
	 * @param portfolioId
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("/api/public/getPortfolioById")  
	public Map<String, Object> getPortfolioById(@RequestParam String portfolioId) throws Exception{
		Long portfolioIdNum = NumberUtils.safeCreateLong(portfolioId);

		if(portfolioIdNum == null) {
			return WebReturn.errorMsg("portfolioId is invalid. It must be an Integer.", false);
		} 
			
		Map<String, Object> myReturn = WebReturn.errorMsg(null, true);
	    myReturn.put("portfolio", portfolioService.getPortfolioById(portfolioIdNum));
		
		return myReturn;
	}
}
