/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: The services for all twig related options.
	 */

package net.grinecraft.etwig.services;

import java.util.LinkedHashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.grinecraft.etwig.model.TwigTemplate;
import net.grinecraft.etwig.repository.TwigTemplateRepository;
import net.grinecraft.etwig.util.JSONUtils;

@Service
public class TwigService {
	
	@Autowired
	private TwigTemplateRepository twigTemplateRepository;
	
	/**
	 * Get the template of a TWIG by its Id.
	 * @param id The Id of the TWIG template.
	 * @return The TwigTemplate object with that Id, or null if the template cannot be found.
	 * @throws Exception 
	 */
	
	public LinkedHashMap<String, Object> getTwigTemplateById(long id) throws Exception {
		if(twigTemplateRepository == null) {
			return null;
		}
		
		Optional<TwigTemplate> twigemplateOpt = twigTemplateRepository.findById(id);
		if(!twigemplateOpt.isPresent()) {
			return null;
		}
		
		JSONUtils jsonUtils = new JSONUtils();
		TwigTemplate twigTemplate = twigemplateOpt.get();
		LinkedHashMap<String, Object> templateMap = new LinkedHashMap<String, Object>();
		
		templateMap.put("Id", twigTemplate.getId());
		templateMap.put("portfolio", twigTemplate.getPortfolio());
		templateMap.put("background", jsonUtils.jsonToMap(twigTemplate.getBackground()));
		
		return templateMap;
	
	}
	
}
