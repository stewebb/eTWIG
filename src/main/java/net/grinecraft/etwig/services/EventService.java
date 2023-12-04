package net.grinecraft.etwig.services;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.model.SingleTimeEvents;
import net.grinecraft.etwig.repository.SingTimeEventsRepository;
import net.grinecraft.etwig.util.DateUtils;

@Service
public class EventService {
	
	@Autowired
	private SingTimeEventsRepository singleTimeEventsRepository;
	
	public LinkedHashMap<Integer,Object> findByDateRange(LocalDate givenDate){
		
		LocalDate currentMonday = DateUtils.findThisMonday(givenDate);
		LocalDate nextMonday = DateUtils.findNextMonday(givenDate);
		
		if(singleTimeEventsRepository == null) {
			return new LinkedHashMap<Integer, Object>();
		}
		
        List<SingleTimeEvents> singleTimeEventsList = singleTimeEventsRepository.findByDateRange(currentMonday, nextMonday);
      
        //LinkedHashMap<Integer, Object> allPortfolios = new LinkedHashMap<>();
        for(SingleTimeEvents singleTimeEvents : singleTimeEventsList) {      	
        	//allPortfolios.put(portfolio.getPortfolioID(), portfolioObjectToMap(portfolio));
        	System.out.println(singleTimeEvents);
        }
        
        return null;
		
	}
}
