/**
 	* eTWIG - The event management software for university communities.
 	* @copyright: Copyright (c) 2024 Steven Webb
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The repository for Asset model.
	*/

package net.grinecraft.etwig.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.grinecraft.etwig.model.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
	
    public Optional<Asset> findById(long id);
}