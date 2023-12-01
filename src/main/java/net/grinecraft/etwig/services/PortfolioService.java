package net.grinecraft.etwig.services;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.model.Portfolio;
import net.grinecraft.etwig.repository.PortfolioRepository;

@Service
public class PortfolioService {
	
	@Autowired
	private PortfolioRepository portfolioRepository;
	
	/**
	 * Get the list of all portfolios
	 * @return JSON of list of all portfolios
	 */
	
	public String getPortfolioList() {
		
		// return an empty JSON when the object is null. 
		if(portfolioRepository == null) {
			return "{}";
		}
        List<Portfolio> portfolioList = (List<Portfolio>) portfolioRepository.findAll();
      
        // Convert to a map of map... Use LinkedHashMap to keep adding order
        LinkedHashMap<Integer, LinkedHashMap<String, String>> allPortfolios = new LinkedHashMap<>();
        for(Portfolio portfolio : portfolioList) {      	
        	allPortfolios.put(portfolio.getPortfolioID(), portfolioObjectToMap(portfolio));
        }
        
        JSONObject jo = new JSONObject(allPortfolios);
        return jo.toString() ;
    }
	
	public String getPortfolioById(long id) {
		if(portfolioRepository == null) {
			return "{}";
		}
		
		Optional<Portfolio> portfolioOpt = portfolioRepository.findById(id);
		
		//if(portfolio.isEmpty()) {
		//	return "{}";
		//}
		
		if (portfolioOpt.isPresent()){
			LinkedHashMap<String, String> foundedPortfolio = portfolioObjectToMap(portfolioOpt.get());
			return (new JSONObject(foundedPortfolio)).toString();
		}else {
			return "{}";
		}
	}
	
	/**
	 * Convert the portfolio object to a map
	 * @param portfolio portfolio object
	 * @return portfolio map
	 */
	
	private LinkedHashMap<String, String> portfolioObjectToMap(Portfolio portfolio){
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
    	//map.put("id", String.valueOf(portfolio.getPortfolioID()));
    	map.put("name", portfolio.getName());
    	map.put("color", portfolio.getColor());
		return map;
	}
}
