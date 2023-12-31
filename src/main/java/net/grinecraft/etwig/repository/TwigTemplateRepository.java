/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: The repository for TwigTemplate model.
	 */

package net.grinecraft.etwig.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.grinecraft.etwig.model.TwigTemplate;


@Repository
public interface TwigTemplateRepository extends JpaRepository<TwigTemplate, Long> {
	
    public Optional<TwigTemplate> findById(long id);
}