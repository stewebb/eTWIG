/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The repository for Asset model.
	*/

package net.etwig.webapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.etwig.webapp.dto.AssetAPIDTO;
import net.etwig.webapp.model.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long>, JpaSpecificationExecutor<Asset> {

	@Query("SELECT new net.etwig.webapp.dto.AssetBasicInfoDTO(a) FROM Asset a JOIN a.uploader u ORDER BY a.id DESC")
    Page<AssetAPIDTO> findAllBasicInfo(Pageable pageable);
}