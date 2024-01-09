/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The services for all portfolio-related options..
	*/

package net.grinecraft.etwig.services;

import java.util.LinkedHashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.model.Portfolio;
import net.grinecraft.etwig.repository.PortfolioRepository;
import net.grinecraft.etwig.util.MapUtils;

@Service
public class PortfolioService {
	
	@Autowired
	private PortfolioRepository portfolioRepository;
	
	private MapUtils mapUtils;
	
	public PortfolioService(){
		this.mapUtils = new MapUtils();
	}
	
	/**
	 * Get the list of all portfolios
	 * @return A LinkedHashMap all portfolios
	 */
	
	public LinkedHashMap<Long, Portfolio> getAllPortfolioList(){
		//return listToMap((List<Portfolio>) portfolioRepository.findAll());
		return mapUtils.listToLinkedHashMap(portfolioRepository.findAll(), Portfolio::getId);
	}
	
	/**
	 * Get the list of portfolios by the status of separated calendar.
	 * @param isSeparatedCalendar 
	 * True get the portfolios WITH separated calendar.
	 * False get the portfolios WITHOUT separated calendar.
	 * Null get all portfolios REGARDLESS the separated calendar option.
	 * @return A LinkedHashMap of the portfolios that meet the above requirements.
	 */
	
	public LinkedHashMap<Long, Portfolio> getPortfolioListBySeparatedCalendar(Boolean isSeparatedCalendar){
		
		if(isSeparatedCalendar == null) {
			return mapUtils.listToLinkedHashMap(portfolioRepository.findAll(), Portfolio::getId);
		}
		
		else if(isSeparatedCalendar == true) {
			return mapUtils.listToLinkedHashMap(portfolioRepository.findByIsSeparatedCalendarTrue(), Portfolio::getId);
		}
		
		// isSeparatedCalendar == false
		else {
			return mapUtils.listToLinkedHashMap(portfolioRepository.findByIsSeparatedCalendarFalse(), Portfolio::getId);
			
		}
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
