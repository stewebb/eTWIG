/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The services for all user-role options.
	*/

package net.grinecraft.etwig.services;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.model.EventOption;
import net.grinecraft.etwig.model.Option;
import net.grinecraft.etwig.repository.EventOptionRepository;
import net.grinecraft.etwig.repository.OptionRepository;
import net.grinecraft.etwig.util.DataException;

@Service
public class EventOptionService {
	
    @Autowired
    private EventOptionRepository eventOptionRepository;

    //@Autowired
    //private EventRepository eventRepository;

    @Autowired
    private OptionRepository optionRepository;
    
    /**
     * Get all Portfolios for a user.
     * @param userId
     * @return The LinkedHashMap of portfolios.
     * @throws DataException 
     */
    
    public LinkedHashMap<Long, Option> getOptionsByEvent(Long eventId) throws DataException {
        List<EventOption> eventOptions = eventOptionRepository.findByIdEventId(eventId);
        LinkedHashMap<Long, Option> optionsMap = new LinkedHashMap<>();

        // Track "visited" properties by using a HashSet.
        HashSet<Long> propertyIds = new HashSet<Long>();
        
        for (EventOption eventOption : eventOptions) {
            Long optionId = eventOption.getId().getOptionId();
            Option option =  optionRepository.findById(optionId).orElse(null);
            
            if(option != null) {
            	
            	// For each event, get all options
                Long propertyId = option.getPropertyId();
                
                // For an event, each property must has 0-1 option(s).
                // i.e., each option must has 1 property.
                // If there has multiple properties for a single option, there must has some problems.
            	if(propertyIds.contains(propertyId)) {
            		throw new DataException("The event with eventId=" + eventId + " has multiple options for the same property.");
            	}
            	
            	// Add "visited" properties to that set.
            	propertyIds.add(propertyId);
            }
            
            optionsMap.computeIfAbsent(optionId, id ->option);
            //System.out.print(propertyIds.get);
            //propertyIds.add(optionId);
        }

        //System.out.print(optionsMap);
        return optionsMap;
    }


}
