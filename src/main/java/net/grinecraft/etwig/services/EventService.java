package net.grinecraft.etwig.services;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.model.Events;
import net.grinecraft.etwig.model.SingleTimeEvents;
import net.grinecraft.etwig.repository.EventsRepository;
import net.grinecraft.etwig.repository.SingTimeEventsRepository;
import net.grinecraft.etwig.util.DataIntegrityViolationException;
import net.grinecraft.etwig.util.DateUtils;

@Service
public class EventService {
	
	@Autowired
	private SingTimeEventsRepository singleTimeEventsRepository;
	
	@Autowired
	private EventsRepository eventsRepository;
	
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
        	//System.out.println(singleTimeEvents);
        	System.out.println(Long.valueOf(singleTimeEvents.getEventID()));
        	getEventDetailsById(Long.valueOf(singleTimeEvents.getEventID()));
        }
        
        return null;
		
	}
	
	private LinkedHashMap<String, Object> getEventDetailsById(long id) {
		if(eventsRepository == null) {
			return new LinkedHashMap<String, Object>();
		}
		
		Optional<Events> eventsOpt = eventsRepository.findById(id);
		//System.out.println(eventsOpt);
		
		// The event exists
		if (eventsOpt.isPresent()){
			//return portfolioObjectToMap(portfolioOpt.get());
			Events events = eventsOpt.get();
			if(events.getPortfolio() == null) {
				throw new DataIntegrityViolationException("The portfolio of event whose id=" + events.getEventID() + " doesn't exist. PLease check the portfolio table.");
			}
		}else {
		//	return new LinkedHashMap<String, Object>();
		}
		return new LinkedHashMap<String, Object>();
	}
}
