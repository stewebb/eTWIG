/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
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
	 * Get the list of all portfolios
	 * @return A LinkedHashMap all portfolios
	 */
	
	public LinkedHashMap<Long, Property> getAllProperties(){
		
		MapUtils maputils = new MapUtils();
		List<Property> properties = propertyRepository.findAll();
		
		return maputils.listToLinkedHashMap(properties, Property::getId);
	}
	
}
