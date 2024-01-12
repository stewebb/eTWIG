/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The repository for TwigTemplate model.
	*/

package net.grinecraft.etwig.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.grinecraft.etwig.dto.TwigTemplateBasicInfoDTO;
import net.grinecraft.etwig.model.TwigTemplate;


@Repository
public interface TwigTemplateRepository extends JpaRepository<TwigTemplate, Long> {
	
	/**
	 * Find a TWIG template by its id.
	 * @param id
	 * @return
	 */
	
    public Optional<TwigTemplate> findById(long id);
    
    /**
     * Find a TWIG template by a given date and portfolio
     * @param currentDate The given date
     * @param portfolioId The portfolio
     * @return
     */
    
    @Query("SELECT e FROM TwigTemplate e WHERE e.portfolioId = :portfolioId " +
            "AND (e.availableFrom IS NULL OR :currentDate >= e.availableFrom) " +
            "AND (e.availableTo IS NULL OR :currentDate <= e.availableTo) " +
            "ORDER BY e.id DESC")
    public Optional<TwigTemplate> findByDateAndPortfolio(LocalDate currentDate, Long portfolioId);
    
    
    @Query("SELECT new net.grinecraft.etwig.dto.TwigTemplateBasicInfoDTO(t.id, t.name, p.name, t.availableFrom, t.availableTo) " +
            "FROM TwigTemplate t LEFT JOIN t.portfolio p")
    public Page<TwigTemplateBasicInfoDTO> findAllTwigTemplates(Pageable pageable);
    
}