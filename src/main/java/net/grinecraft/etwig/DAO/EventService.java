package net.grinecraft.etwig.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.Model.Events;

@Service
public class EventService {
	
	@Autowired
	private EventsRepository eventsRepository;
	
	public List<Events> list() {
		if(eventsRepository == null) {
			System.out.println("null!");
			return null;
		}
        return (List<Events>) eventsRepository.findAll();
    }
}
