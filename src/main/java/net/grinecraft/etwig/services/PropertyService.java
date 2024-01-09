/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The services for all portfolio-related options..
	*/

package net.grinecraft.etwig.services;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.model.Portfolio;
import net.grinecraft.etwig.model.Property;
import net.grinecraft.etwig.repository.PortfolioRepository;
import net.grinecraft.etwig.repository.PropertyRepository;
import net.grinecraft.etwig.util.MapUtils;

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
