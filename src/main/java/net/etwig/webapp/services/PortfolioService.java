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

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import net.etwig.webapp.model.Portfolio;
import net.etwig.webapp.repository.PortfolioRepository;

@Service
public class PortfolioService {
	
	@Autowired
	private PortfolioRepository portfolioRepository;

	
	/**
	 * Get the list of all portfolios.
	 *
	 * @return
	 */
	
	public Page<Portfolio> getPortfolioList(Pageable pageable){
		return portfolioRepository.findAll(orderByCombinedLengthDesc(), pageable);
	}

	public static Specification<Portfolio> orderByCombinedLengthDesc() {
		return (Root<Portfolio> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			query.orderBy(criteriaBuilder.desc(criteriaBuilder.sum(
					criteriaBuilder.coalesce(criteriaBuilder.length(root.get("name")), 0),
					criteriaBuilder.coalesce(criteriaBuilder.length(root.get("abbreviation")), 0)
			)));
			return query.getRestriction();
		};
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
	 * Retrieves a {@link Portfolio} instance by its identifier.
	 * <p>
	 * This method searches for a portfolio using the provided ID. If the portfolioRepository is not initialized,
	 * or if no portfolio with the given ID is found, the method returns null.
	 *
	 * @param id The unique identifier of the portfolio to be retrieved.
	 * @return The {@link Portfolio} instance with the specified ID, or null if not found or if the repository is not initialized.
	 */
	
	public Portfolio findById(long id) {
		return portfolioRepository == null ? null : portfolioRepository.findById(id).orElse(null);
	}
	
}
