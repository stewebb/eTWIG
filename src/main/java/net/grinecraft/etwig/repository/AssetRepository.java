/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
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
	
	/**
	 * Find the asset information by its id.
	 * @param id
	 * @return
	 */
    public Optional<Asset> findById(long id);
}