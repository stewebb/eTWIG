/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The repository for Asset model.
	*/

package net.etwig.webapp.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.etwig.webapp.dto.AssetBasicInfoDTO;
import net.etwig.webapp.model.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    //Optional<Asset> findById(long id);
    
	@Query("SELECT new net.etwig.webapp.dto.AssetBasicInfoDTO(a) FROM Asset a JOIN a.uploader u ORDER BY a.id DESC")
    Page<AssetBasicInfoDTO> findAllBasicInfo(Pageable pageable);
}