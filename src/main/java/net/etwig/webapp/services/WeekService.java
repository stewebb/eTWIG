/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The services for all week related options.
	*/

package net.etwig.webapp.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.etwig.webapp.model.Week;
import net.etwig.webapp.repository.WeekRepository;
import net.etwig.webapp.util.DateUtils;

@Service
public class WeekService {

	@Autowired
	private WeekRepository weekRepository;

	/**
	 * Retrieves the {@link Week} object corresponding to the week of the specified date.
	 * This method calculates the Monday of the week for the given date and queries the {@code weekRepository}
	 * to find the {@link Week} object starting from that Monday. If the {@code weekRepository} is not initialized,
	 * or if no {@link Week} object corresponds to that Monday, the method returns null.
	 * <p>
	 * Process:
	 * 1. Checks if the {@code weekRepository} is initialized. If not, returns null.
	 * 2. Utilizes {@code DateUtils.findThisMonday} to compute the Monday of the given week for the provided date.
	 * 3. Queries the {@code weekRepository} to find the week starting from this Monday.
	 *
	 * @param givenDate the date for which to find the corresponding week. The date must not be null.
	 * @return the {@link Week} object that starts on the Monday of the week containing {@code givenDate},
	 *         or null if no such week is found or if the repository is not initialized.
	 * @throws NullPointerException if {@code givenDate} is null.
	 */
	
    public Week getWeekByDate(LocalDate givenDate) {
    	if(weekRepository == null) {
			return null;
		}
    	
    	// Get Monday first and find the week info.
    	LocalDate monday = DateUtils.findThisMonday(givenDate);
    	return weekRepository.findByMonday(monday).orElse(null);
    }

}
