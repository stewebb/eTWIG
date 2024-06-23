/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The services for all event-options related options..
	*/

package net.etwig.webapp.services;

import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.etwig.webapp.model.EventOption;
import net.etwig.webapp.model.Option;
import net.etwig.webapp.repository.EventOptionRepository;
import net.etwig.webapp.repository.OptionRepository;
import net.etwig.webapp.util.DataException;

@Service
public class EventOptionService {
	
    @Autowired
    private EventOptionRepository eventOptionRepository;

    @Autowired
    private OptionRepository optionRepository;
    
    /**
     * Get all options for an event.
     * @param userId
     * @return The LinkedHashMap of portfolios.
     * @throws DataException
     */
    
    public HashSet<Long> getOptionsByEvent(Long eventId) {
        List<EventOption> eventOptions = eventOptionRepository.findByIdEventId(eventId);
        
        // Only need to know the Id of the options, an set is adequate.
        HashSet<Long> optionIds = new HashSet<Long>();
        for (EventOption eventOption : eventOptions) {
        	optionIds.add(eventOption.getId().getOptionId());
        }

        //duplicateOptionCheck(optionIds);
        return optionIds;
    }

    /**
     * Check an event has multiple contradicted options in a property or not.
     * @param optionIds A HashSet with all option ids.
     * @throws DataException If an event has multiple contradicted options in a property.
     */

    /*
     // TODO duplicateOptionCheck
    
    private void duplicateOptionCheck(HashSet<Long> optionIds) throws DataException {
    	
        // Track "visited" properties by using a HashSet.
        HashSet<Long> propertyIds = new HashSet<Long>();
        
    	for (Long optionId : optionIds) {
    		
            Option option =  optionRepository.findById(optionId).orElse(null);
            if(option != null) {
            	
            	// For each event, get all options.
                Long propertyId = null;//option.getPropertyId();
                
                // For an event, each property must has 0-1 option(s). i.e., each option must has 1 property.
                // If there has multiple properties for a single option, there must has some problems.
            	if(propertyIds.contains(propertyId)) {
            		throw new DataException("Multiple options for the same property has been detected for an event.");
            	}
            	
            	// Add "visited" properties to that set.
            	propertyIds.add(propertyId);
            }
    	}

    }


     */
}
