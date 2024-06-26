/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The repository for Week model.
	*/

package net.etwig.webapp.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.etwig.webapp.model.Week;

@Repository
public interface WeekRepository extends JpaRepository<Week, Long> {

	/**
	 * Retrieves an {@code Optional<Week>} containing the week information that starts on the specified Monday date.
	 * This method is useful for fetching week-based data when you have a date that corresponds to the first day of the
	 * week, i.e., Monday.
	 *
	 * @param monday the {@code LocalDate} representing Monday of the week for which information is requested.
	 *              It should not be {@code null}.
	 * @return an {@code Optional<Week>} which will be non-empty if the week information is found, or empty if no
	 * information is available for the given Monday.
	 */

    Optional<Week> findByMonday(LocalDate monday);
    
}