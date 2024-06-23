/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The services for all portfolio-related options..
	*/

package net.etwig.webapp.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.etwig.webapp.dto.events.EventOptionsForEventPageDTO;
import net.etwig.webapp.model.EventOption;
import net.etwig.webapp.model.Option;
import net.etwig.webapp.repository.EventOptionRepository;
import net.etwig.webapp.repository.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.etwig.webapp.model.Property;
import net.etwig.webapp.repository.PropertyRepository;

@Service
public class PropertyService {
	
	@Autowired
	private PropertyRepository propertyRepository;

	@Autowired
	private OptionRepository optionRepository;

	@Autowired
	EventOptionRepository eventOptionRepository;

	/**
	 * Retrieves all properties from the repository.
	 * <p>
	 * This method utilizes the {@code findAll} method of the {@code propertyRepository} to fetch all records
	 * from the data store.
	 *
	 * @return A list of {@code Property} objects representing all properties available in the repository.
	 */
	
	public List<Property> findAll(){
		return propertyRepository.findAll();
	}

	public Map<String, List<Option>> findAllGroupByProperties() {
		List<Option> optionList = optionRepository.findAll();
        return optionList.stream().collect(Collectors.groupingBy(w -> Long.toString(w.getBelongsToId())));
	}

	public HashSet<Long> getOptionsByEvent(Long eventId) {

		// Step 1: Get all options and assign to a DTO
		List<Option> optionList = optionRepository.findAll();
		//List<EventOptionsForEventPageDTO> eventOptionsForEventPage = new ArrayList<>();

		//for (Option option : optionList) {
		//	eventOptionsForEventPage.add(new EventOptionsForEventPageDTO(option));
		//}

		List<EventOptionsForEventPageDTO> eventOptionsForEventPage = optionList.stream()
				.map(EventOptionsForEventPageDTO::new)
				.collect(Collectors.toList());

		System.out.println(eventOptionsForEventPage);
		List<EventOption> eventOptions = eventOptionRepository.findByIdEventId(eventId);

		// Only need to know the Id of the options, an set is adequate.
		HashSet<Long> optionIds = new HashSet<Long>();
		for (EventOption eventOption : eventOptions) {
			optionIds.add(eventOption.getId().getOptionId());
		}

		//duplicateOptionCheck(optionIds);
		return optionIds;
	}


}
