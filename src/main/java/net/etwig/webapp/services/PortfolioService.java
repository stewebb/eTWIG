/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The services for all portfolio-related options..
	*/

package net.etwig.webapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.etwig.webapp.model.Portfolio;
import net.etwig.webapp.repository.PortfolioRepository;

@Service
public class PortfolioService {
	
	@Autowired
	private PortfolioRepository portfolioRepository;
	
	public PortfolioService(){
		//new MapUtils();
	}
	
	/**
	 * Get the list of all portfolios.
	 * @return
	 */
	
	public List<Portfolio> getAllPortfolioList(){
		return portfolioRepository.findAllOrderByNameLengthDesc();
	}
	
	/**
	 * Get the list of portfolios by the status of separated calendar.
	 *
	 * @param isSeparatedCalendar True get the portfolios WITH separated calendar.
	 *                            False get the portfolios WITHOUT separated calendar.
	 *                            Null get all portfolios REGARDLESS OF the separated calendar option.
	 * @return A LinkedHashMap of the portfolios that meet the above requirements.
	 */
	
	public List<Portfolio> getPortfolioListBySeparatedCalendar(Boolean isSeparatedCalendar){

		if(isSeparatedCalendar == null) {
			return portfolioRepository.findAll();
		}

		// isSeparatedCalendar == true
		else if(isSeparatedCalendar) {
			return portfolioRepository.findBySeparatedCalendarTrue();
		}
		
		// isSeparatedCalendar == false
		else {
			return portfolioRepository.findBySeparatedCalendarFalse();
		}
	}
	
	/**
	 * Get a portfolio details by its Id.
	 * @param id The Id of this portfolio.
	 * @return The portfolio object with that Id, or null if no portfolio with that Id.
	 */
	
	public Portfolio getPortfolioById(long id) {
		return portfolioRepository == null ? null : portfolioRepository.findById(id).orElse(null);
	}
	
}
