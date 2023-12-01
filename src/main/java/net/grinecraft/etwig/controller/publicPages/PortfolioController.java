package net.grinecraft.etwig.controller.publicPages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.grinecraft.etwig.services.PortfolioService;
import net.grinecraft.etwig.util.NumberUtils;

@RestController
public class PortfolioController {
	
	@Autowired
	PortfolioService portfolioService;

	HttpHeaders responseHeaders = new HttpHeaders();
	
	@RequestMapping("/public/_getPortfolioList")  
	public String getPortfolioList() throws Exception{
	    responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		return portfolioService.getPortfolioList();  
	}
	
	@RequestMapping("/public/_getPortfolioById")  
	public String getPortfolioById(@RequestParam(required = false) String portfolioID) throws Exception{
	    responseHeaders.setContentType(MediaType.APPLICATION_JSON);
	    
	    // Check input first!
	    if(!NumberUtils.isLong(portfolioID)) {
	    	return "{\"code\": 1, \"msg\": \"portfolioID parameter is either missing or invalid. It must be a positive integer.\"";
	    }
	    
	    String content = portfolioService.getPortfolioById(Long.parseLong(portfolioID));  
	    return "{\"code\": 0, \"msg\": \"success\", \"portfolio\": " + content + "}";
	}
}
