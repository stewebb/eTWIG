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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.grinecraft.etwig.dto.AssetBasicInfoDTO;
import net.grinecraft.etwig.model.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
	
	/**
	 * Find the asset information by its id.
	 * @param id
	 * @return
	 */
    public Optional<Asset> findById(long id);
    
    //@Query("SELECT new net.grinecraft.etwig.dto.AssetBasicInfoDTO(a.id, a.fileName, a.fileSize, u.firstName, u.middleName, u.lastName, a.lastModified) " +
   //         "FROM Asset a JOIN a.user u")
  @Query("SELECT new net.grinecraft.etwig.dto.AssetBasicInfoDTO(a) " +
             "FROM Asset a JOIN a.user u")
    public Page<AssetBasicInfoDTO> findAllBasicInfo(Pageable pageable);
}