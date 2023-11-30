package net.grinecraft.etwig.Controller.Public;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.grinecraft.etwig.Services.PortfolioService;
import net.grinecraft.etwig.Util.NumberUtils;

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
	    	return "{\"code\": 1, \"msg\": \"portfolioID parameter is missing, or invalid.\"";
	    }
	    
	    String content = portfolioService.getPortfolioById(Long.parseLong(portfolioID));  
	    return "{\"code\": 0, \"portfolio\": " + content + "}";
	}
}
