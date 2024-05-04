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
import java.util.Optional;

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
	 * Get the week information by any given date.
	 * @param givenDate
	 * @return
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
