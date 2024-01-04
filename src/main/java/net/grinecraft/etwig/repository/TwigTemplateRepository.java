/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: The repository for TwigTemplate model.
	 */

package net.grinecraft.etwig.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.grinecraft.etwig.model.TwigTemplate;


@Repository
public interface TwigTemplateRepository extends JpaRepository<TwigTemplate, Long> {
	
    public Optional<TwigTemplate> findById(long id);
    
    @Query("SELECT e FROM TwigTemplate e WHERE e.portfolioId = :portfolioId " +
            "AND (e.availableFrom IS NULL OR :currentDate >= e.availableFrom) " +
            "AND (e.availableTo IS NULL OR :currentDate <= e.availableTo) " +
            "ORDER BY e.id DESC")
    public Optional<TwigTemplate> findByDateAndPortfolio(LocalDate currentDate, Long portfolioId);
}