/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The services for all option-related options.
	*/

package net.etwig.webapp.services;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.model.Option;
import net.grinecraft.etwig.repository.OptionRepository;
import net.grinecraft.etwig.util.MapUtils;

@Service
public class OptionService {
	
	@Autowired
	private OptionRepository optionRepository;
	
	/**
	 * Get the list of all options
	 * @return A LinkedHashMap all options
	 */
	
	public LinkedHashMap<Long, Option> findAll(){
		
		MapUtils mapUtils = new MapUtils();		
		return mapUtils.listToLinkedHashMap(optionRepository.findAll(), Option::getId);
	}
	
	/**
	 * Get all options, but are group by the properties.
	 * @return A map, key is the propertyId, value is a list with all options that belong to this property.
	 */
	
	public Map<String, List<Option>> findAllGroupByProperties() {
		
		List<Option> optionList = optionRepository.findAll();
		Map<String, List<Option>> optionGrouped = optionList.stream().collect(Collectors.groupingBy(w -> Long.toString(w.getBelongsToId())));		
		return optionGrouped;
        
	}
	
}
