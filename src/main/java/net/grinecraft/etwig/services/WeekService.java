package net.grinecraft.etwig.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.model.Week;
import net.grinecraft.etwig.repository.WeekRepository;
import net.grinecraft.etwig.util.DateUtils;

@Service
public class WeekService {

	@Autowired
	private WeekRepository weekRepository;
	
    public Week getWeekByDate(LocalDate givenDate) {
    	
    	if(weekRepository == null) {
			return null;
		}
    	
    	// Get Monday first
    	LocalDate monday = DateUtils.findThisMonday(givenDate);
    	
    	// Then find the week info
    	Optional<Week> weekOpt = weekRepository.findByMonday(monday);
    	return weekOpt.isPresent() ? weekOpt.get() : null;
    	
    }

}
