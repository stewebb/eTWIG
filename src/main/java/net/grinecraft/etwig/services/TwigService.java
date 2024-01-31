/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The services for all twig related options.
	*/

package net.grinecraft.etwig.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.dto.TwigTemplateBasicInfoDTO;
import net.grinecraft.etwig.dto.TwigTemplateDTO;
import net.grinecraft.etwig.dto.TwigTemplateDesignDTO;
import net.grinecraft.etwig.model.TwigTemplate;
import net.grinecraft.etwig.repository.TwigTemplateRepository;

@Service
public class TwigService {
	
	@Autowired
	private TwigTemplateRepository twigTemplateRepository;
	
	/**
	 * Get the TWIG template by its Id.
	 * @param id The Id of the TWIG template.
	 * @return The TwigTemplate object with that Id, or null if the template cannot be found.
	 * @throws Exception 
	 */
	
	public TwigTemplateDesignDTO getTwigTemplateDesignById(long id) throws Exception {
		if(twigTemplateRepository == null) {
			return null;
		}
		
		Optional<TwigTemplateDesignDTO> twigTemplateopt = null;//twigTemplateRepository.findDesignById(id);
		return twigTemplateopt.isPresent() ? twigTemplateopt.get() : null;
	
	}
	
	/**
	 * Get the TWIG template by the date and portfolio.
	 * @param date
	 * @param portfolioId
	 * @return
	 * @throws Exception
	 */
	
	public TwigTemplateDTO getTwigTemplateByDateAndPortfolio(LocalDate date, Long portfolioId) throws Exception {
		TwigTemplate twigTemplate = (portfolioId != null && portfolioId > 0) ? twigTemplateRepository.findByDateAndPortfolio(date, portfolioId).orElse(null) : twigTemplateRepository.findAllPortfoliosByDateOnly(date).orElse(null);
		return (twigTemplate == null) ? null : new TwigTemplateDTO(twigTemplate);
	}
	
	public Page<TwigTemplateBasicInfoDTO> getTwigTemplateList(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		//return twigTemplateRepository.findAllTwigTemplates(pageable);
		return null;
	}
	
	public TwigTemplateBasicInfoDTO getTwigTemplateBasicInfoById(Long twigTemplateId) {
		
		Optional<TwigTemplateBasicInfoDTO> twigTemplateOpt = null;//twigTemplateRepository.findBasicInfoById(twigTemplateId);
		if(!twigTemplateOpt.isPresent()) {
			return null;
		}
		return twigTemplateOpt.get();
		
	}
}
