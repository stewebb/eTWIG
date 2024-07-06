/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The services for all portfolio-related options..
	*/

package net.etwig.webapp.services;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import net.etwig.webapp.model.Portfolio;
import net.etwig.webapp.repository.PortfolioRepository;

import java.util.List;

@Service
public class PortfolioService {
	
	@Autowired
	private PortfolioRepository portfolioRepository;

	/**
	 * Retrieves a paginated list of all {@link Portfolio} entities based on the specified criteria.
	 * <p>
	 * This method utilizes the {@code portfolioSpecification} to apply filtering based on whether portfolios
	 * are marked with a separated calendar, if specified. It returns a paginated result of portfolios,
	 * allowing for efficient data handling and presentation in systems where the total number of portfolios can be large.
	 * The results are sorted and filtered according to the specifications defined in {@code portfolioSpecification}.
	 * </p>
	 *
	 * @param separatedCalendar Optional {@link Boolean} filter to include only portfolios that either have
	 *                          a separated calendar or not, depending on the Boolean value. If {@code null},
	 *                          portfolios are not filtered based on their calendar separation status.
	 * @param pageable A {@link Pageable} object specifying the page request details such as page number,
	 *                 page size, and sorting order.
	 * @return A {@link Page} of {@link Portfolio} that contains the portfolios for the requested page based on
	 *         the provided filtering and paging parameters.
	 */
	
	public Page<Portfolio> findByCriteria(Boolean separatedCalendar, Pageable pageable){
		return portfolioRepository.findAll(portfolioSpecification(separatedCalendar), pageable);
	}

	/**
	 * Constructs a {@link Specification} for querying {@link Portfolio} entities, optionally filtering by their
	 * calendar separation status and always ordering by the combined length of their name and abbreviation.
	 * <p>
	 * This method creates a dynamic query specification for Portfolios. It includes an order-by clause that orders
	 * portfolios in descending order based on the sum of the lengths of their name and abbreviation. If the
	 * {@code separatedCalendar} parameter is provided, it adds a where clause that filters the results based on the
	 * Portfolio's separated calendar status.
	 * </p>
	 *
	 * @param separatedCalendar A {@link Boolean} indicating whether to filter portfolios by the separatedCalendar attribute.
	 *                          If {@code null}, no filtering is applied based on separated calendar status.
	 * @return A {@link Specification} of {@link Portfolio} that can be used to query repositories.
	 */

	public Specification<Portfolio> portfolioSpecification(Boolean separatedCalendar) {
		return (Root<Portfolio> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {

			// Order by combined length of name and abbreviation
			query.orderBy(criteriaBuilder.desc(criteriaBuilder.sum(
					criteriaBuilder.coalesce(criteriaBuilder.length(root.get("name")), 0),
					criteriaBuilder.coalesce(criteriaBuilder.length(root.get("abbreviation")), 0)
			)));

			// Filter by separated calendar
			if (separatedCalendar != null) {
				Predicate separatedCalendarPredicate = criteriaBuilder.equal(root.get("separatedCalendar"), separatedCalendar);
				query.where(separatedCalendarPredicate);
			}

			return query.getRestriction();
		};
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

	/**
	 * Retrieves all portfolios from the repository without applying any filtering
	 * criteria or pagination. This method leverages the {@code findByCriteria} method,
	 * passing {@code null} as the criteria and specifying that the retrieval should be
	 * unpaged to fetch all available records at once.
	 *
	 * @return A list of {@code Portfolio} objects representing all portfolios stored in the repository.
	 *         This list may be empty if no portfolios are found.
	 */

	public List<Portfolio> findAllPortfolios() {
		return findByCriteria(null, Pageable.unpaged()).getContent();
	}
}
