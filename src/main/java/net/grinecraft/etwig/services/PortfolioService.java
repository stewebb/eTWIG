package net.grinecraft.etwig.services;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
	
	public LinkedHashMap<Long, Portfolio> getPortfolioList() {
		
		// return an empty JSON when the object is null. 
		if(portfolioRepository == null) {
			return null;
		}
        List<Portfolio> portfolioList = (List<Portfolio>) portfolioRepository.findAll();
      
        // Convert to a map of map... Use LinkedHashMap to keep adding order
        LinkedHashMap<Long, Portfolio> allPortfolios = new LinkedHashMap<>();
        for(Portfolio portfolio : portfolioList) {      	
        	allPortfolios.put(portfolio.getId(), portfolio);
        }
        
        return allPortfolios;
    }
	
	public Portfolio getPortfolioById(long id) {
		if(portfolioRepository == null) {
			return null;
		}
		
		Optional<Portfolio> portfolioOpt = portfolioRepository.findById(id);
		
		if (portfolioOpt.isPresent()){
			return portfolioOpt.get();
		}else {
			return null;
		}
	}
	
	/**
	 * Convert the portfolio object to a map
	 * @param portfolio portfolio object
	 * @return portfolio map
	 */
	
	/*
	private LinkedHashMap<String, Object> portfolioObjectToMap(Portfolio portfolio){
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
    	//map.put("id", String.valueOf(portfolio.getPortfolioID()));
    	map.put("name", portfolio.getName());
    	map.put("color", portfolio.getColor());
    	map.put("abbreviation", portfolio.getAbbreviation());
    	map.put("icon", portfolio.getIcon());
    	map.put("isSeperatedCalendar", portfolio.isSeperatedCalendar());
    	map.put("parent", portfolio.getParent());
		return map;
	}
	*/
}
