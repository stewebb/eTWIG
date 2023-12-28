/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: The services for all portfolio-related options..
	 */

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
	 * @return A LinkedHashMap all portfolios
	 */
	
	public LinkedHashMap<Long, Portfolio> getPortfolioList() {
		
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
	
	/**
	 * Get a portfolio details by its Id.
	 * @param id The Id of this portfolio.
	 * @return The portfolio object with that Id, or null if no portfolio with that Id.
	 */
	
	public Portfolio getPortfolioById(long id) {
		if(portfolioRepository == null) {
			return null;
		}
		
		Optional<Portfolio> portfolioOpt = portfolioRepository.findById(id);
		return portfolioOpt.isPresent() ? portfolioOpt.get() : null;
	}
}
