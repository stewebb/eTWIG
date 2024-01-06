/**
	 * eTWIG - The event management software for university communities.
	 * @copyright: Copyright (c) 2024 Steven Webb
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: The services for all portfolio-related options..
	 */

package net.grinecraft.etwig.services;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.model.Option;
import net.grinecraft.etwig.model.Portfolio;
import net.grinecraft.etwig.model.Property;
import net.grinecraft.etwig.repository.OptionRepository;
import net.grinecraft.etwig.repository.PortfolioRepository;
import net.grinecraft.etwig.repository.PropertyRepository;
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
	
	public void findAllGroupByProperties() {
        List<Object[]> groupedProperties = optionRepository.groupByProperty();
        
        for(Object[] obj : groupedProperties) {
        	System.out.println(Arrays.toString(obj));
        }
        
	}
	
}
