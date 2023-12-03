package net.grinecraft.etwig.services;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
	
	public LinkedHashMap<Integer, Object> getPortfolioList() {
		
		// return an empty JSON when the object is null. 
		if(portfolioRepository == null) {
			return new LinkedHashMap<Integer, Object>();
		}
        List<Portfolio> portfolioList = (List<Portfolio>) portfolioRepository.findAll();
      
        // Convert to a map of map... Use LinkedHashMap to keep adding order
        LinkedHashMap<Integer, Object> allPortfolios = new LinkedHashMap<>();
        for(Portfolio portfolio : portfolioList) {      	
        	allPortfolios.put(portfolio.getPortfolioID(), portfolioObjectToMap(portfolio));
        }
        
        return allPortfolios;
    }
	
	public LinkedHashMap<String, Object> getPortfolioById(long id) {
		if(portfolioRepository == null) {
			return new LinkedHashMap<String, Object>();
		}
		
		Optional<Portfolio> portfolioOpt = portfolioRepository.findById(id);
		
		if (portfolioOpt.isPresent()){
			return portfolioObjectToMap(portfolioOpt.get());
		}else {
			return new LinkedHashMap<String, Object>();
		}
	}
	
	/**
	 * Convert the portfolio object to a map
	 * @param portfolio portfolio object
	 * @return portfolio map
	 */
	
	private LinkedHashMap<String, Object> portfolioObjectToMap(Portfolio portfolio){
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
    	//map.put("id", String.valueOf(portfolio.getPortfolioID()));
    	map.put("name", portfolio.getName());
    	map.put("color", portfolio.getColor());
    	map.put("abbreviation", portfolio.getAbbreviation());
		return map;
	}
}
