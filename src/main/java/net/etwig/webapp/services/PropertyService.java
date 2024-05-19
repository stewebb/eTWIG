/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The services for all portfolio-related options..
	*/

package net.etwig.webapp.services;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.etwig.webapp.model.Option;
import net.etwig.webapp.repository.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.etwig.webapp.model.Property;
import net.etwig.webapp.repository.PropertyRepository;
import net.etwig.webapp.util.MapUtils;

@Service
public class PropertyService {
	
	@Autowired
	private PropertyRepository propertyRepository;

	@Autowired
	private OptionRepository optionRepository;
	
	/**
	 * Get the list of all properties
	 * @return A LinkedHashMap all properties
	 */
	
	public LinkedHashMap<Long, Property> findAll(){
		MapUtils mapUtils = new MapUtils();		
		return mapUtils.listToLinkedHashMap(propertyRepository.findAll(), Property::getId);
	}

	//public LinkedHashMap<Long, Option> findAll(){

	//	MapUtils mapUtils = new MapUtils();
	//	return mapUtils.listToLinkedHashMap(optionRepository.findAll(), Option::getId);
	//}

	public Map<String, List<Option>> findAllGroupByProperties() {

		List<Option> optionList = optionRepository.findAll();
        return optionList.stream().collect(Collectors.groupingBy(w -> Long.toString(w.getBelongsToId())));

	}


}
