package net.grinecraft.etwig.services;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.model.SingleTimeEvents;
import net.grinecraft.etwig.repository.SingTimeEventsRepository;

@Service
public class EventService {
	
	@Autowired
	private SingTimeEventsRepository singleTimeEventsRepository;
	
	public LinkedHashMap<Integer,Object> findByDateRange(String startDateTime){
		
		if(singleTimeEventsRepository == null) {
			return new LinkedHashMap<Integer, Object>();
		}
		
        List<SingleTimeEvents> singleTimeEventsList = singleTimeEventsRepository.findByDateRange(startDateTime);
      
        //LinkedHashMap<Integer, Object> allPortfolios = new LinkedHashMap<>();
        for(SingleTimeEvents singleTimeEvents : singleTimeEventsList) {      	
        	//allPortfolios.put(portfolio.getPortfolioID(), portfolioObjectToMap(portfolio));
        	System.out.println(singleTimeEvents);
        }
        
        return null;
		
	}
}
