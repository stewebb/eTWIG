/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The services for all portfolio-related options..
	*/

package net.etwig.webapp.services;

import java.util.*;
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

	public Map<String, List<EventOptionsForEventPageDTO>> getOptionsByEvent(Long eventId) {

		// Step 1: Get all options and assign to a DTO
		List<Option> optionList = optionRepository.findAll();
		List<EventOptionsForEventPageDTO> eventOptionsForEventPages = optionList.stream()
				.map(EventOptionsForEventPageDTO::new)
				.collect(Collectors.toList());

		// Step 2: Set selected options
		if(eventId != null && eventId > 0) {
			List<EventOption> eventOptions = eventOptionRepository.findByIdEventId(eventId);

			// All selected options for an event
			for (EventOption eventOption : eventOptions) {

				// All options available
				for (EventOptionsForEventPageDTO eventOptionsForEventPage : eventOptionsForEventPages) {

					// Find the selected options and label them.
					if (Objects.equals(eventOptionsForEventPage.getOptionId(), eventOption.getId().getOptionId())) {
						eventOptionsForEventPage.setSelected(true);
					}
				}
			}
		}
		System.out.println(eventOptionsForEventPages);

		// Step 3: Group by property ID
		Map<String, List<EventOptionsForEventPageDTO>> groupedOptions = eventOptionsForEventPages.stream()
				.collect(Collectors.groupingBy(w -> Long.toString(w.getBelongsToPropertyId())));

		// Step 4: Add a default option indicates for not selected.
		groupedOptions.forEach((key, list) -> {

			// Check if there is any selected event option in the current list
			boolean anySelected = list.stream().anyMatch(EventOptionsForEventPageDTO::isSelected);

			// Create new event option DTO for the default option
			EventOptionsForEventPageDTO newOption = new EventOptionsForEventPageDTO(
					-1L,
					"(Not Selected)",
					Long.parseLong(key), !anySelected
			);

			// In each list, sort by optionId ASC.
			list.add(newOption);
			list.sort(Comparator.comparingLong(EventOptionsForEventPageDTO::getOptionId));
		});

		return groupedOptions;
	}
}
