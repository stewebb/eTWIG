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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.etwig.webapp.model.Property;
import net.etwig.webapp.repository.PropertyRepository;
import net.etwig.webapp.util.MapUtils;

@Service
public class PropertyService {
	
	@Autowired
	private PropertyRepository propertyRepository;
	
	/**
	 * Get the list of all properties
	 * @return A LinkedHashMap all properties
	 */
	
	public LinkedHashMap<Long, Property> findAll(){
		MapUtils mapUtils = new MapUtils();		
		return mapUtils.listToLinkedHashMap(propertyRepository.findAll(), Property::getId);
	}
	
}
