/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The services for all assets-related options.
	*/

package net.etwig.webapp.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.criteria.Predicate;
import net.etwig.webapp.dto.user.CurrentUserDTOWrapper;
import net.etwig.webapp.dto.user.CurrentUserPermissionDTO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import net.etwig.webapp.config.ConfigFile;
import net.etwig.webapp.dto.AssetAPIDTO;
import net.etwig.webapp.dto.user.CurrentUserBasicInfoDTO;
import net.etwig.webapp.model.Asset;
import net.etwig.webapp.repository.AssetRepository;

@Service
public class AssetService {
	
	private final Path rootLocation;
	
	@Autowired
	private AssetRepository assetRepository;
	
	@Autowired
	private HttpSession session;

	@Autowired
	private UserSessionService userSessionService;

	//@Autowired
	//private UserRoleRepository userRoleRepository;
	
	@Autowired
    public AssetService(ConfigFile config) {
        this.rootLocation = Paths.get(config.getRootLocation());
    }

	/**
	 * Retrieves the details of an asset by its ID.
	 * <p>
	 * This method attempts to fetch an asset from the {@code assetRepository} using the provided ID.
	 * If the repository is not initialized (i.e., it's {@code null}), or if no asset with the provided ID exists,
	 * the method returns {@code null}.
	 *
	 * @param id The unique identifier of the asset to be retrieved.
	 * @return The {@link Asset} with the specified ID, or {@code null} if the asset could not be found or the repository is not initialized.
	 */
	
	public Asset getAssetDetailsById(Long id) {
		return (assetRepository == null || id == null) ? null : assetRepository.findById(id).orElse(null);
	}

	/**
	 * Retrieves the content of the specified asset as a {@link Resource}.
	 * <p>
	 * This method converts the stored name of the asset into a URI and wraps it in a {@link UrlResource},
	 * providing a Spring-friendly way to access the asset's contents. If the input asset is {@code null},
	 * the method returns {@code null}.
	 *
	 * @param asset The {@link Asset} whose content is to be retrieved. Can be {@code null}.
	 * @return A {@link Resource} that represents the content of the asset, or {@code null} if the input asset is {@code null}.
	 * @throws Exception If there is an issue resolving the asset's URI or accessing the asset.
	 */

	public Resource getAssetContent(Asset asset) throws Exception {
		return (asset == null) ? null : new UrlResource(rootLocation.resolve(asset.getStoredName()).toUri());
	}

	/**
	 * Retrieves a paginated list of assets filtered by the upload user ID and a search value. This method first validates
	 * the current user's session and checks their permissions. Each asset in the list includes information about whether
	 * the current user has permission to delete the asset. Users with admin or graphics manager roles, or users who are
	 * the original uploader of the asset, are granted deletion rights.
	 *
	 * @param uploadUserId The user ID of the asset uploader to filter the assets by. If this is null, assets are not
	 *                        filtered by uploader.
	 * @param searchValue The search criterion used to further filter the asset results. If null or empty, the search
	 *                        criterion is ignored.
	 * @param pageable The pagination information and sorting criteria.
	 * @return A {@code Page<AssetAPIDTO>} containing the assets that match the criteria and pagination settings.
	 *         Each {@code AssetAPIDTO} includes asset details and a deletion permission flag specific to the current
	 *         user's roles and relation to the asset.
	 * @throws SecurityException If the user's session is invalid or expired.
	 */


	public Page<AssetAPIDTO> findAssetsByCriteria(Long uploadUserId, String searchValue, Pageable pageable) {

		// Get current user info
		CurrentUserDTOWrapper wrapper = userSessionService.validateSession();
		CurrentUserPermissionDTO permission = wrapper.getPermission();
		CurrentUserBasicInfoDTO basicInfo = wrapper.getBasicInfo();

		// The user can delete any asset if the user is an admin or a graphics manager.
		boolean globalDeletePermission = permission.isAdminAccess() || permission.isGraphicsAccess();

		// Get asset list
		Specification<Asset> spec = assetsCriteria(uploadUserId, searchValue);
		Page<Asset> assets = assetRepository.findAll(spec, pageable);
		List<AssetAPIDTO> dtos = new ArrayList<>();

		// Perform permission check on each asset
		for (Asset asset : assets) {
			AssetAPIDTO assetAPIDTO = new AssetAPIDTO(asset);

			// User can also delete asset if the user is the uploader of it.
			assetAPIDTO.setCanDelete(globalDeletePermission || basicInfo.getId().equals(asset.getUploaderId()));
			dtos.add(assetAPIDTO);
		}

		return new PageImpl<>(dtos, pageable, assets.getTotalElements());
	}

	/**
	 * Constructs a specification for querying assets based on the uploader's user ID and a search criterion. This method creates a criteria query that can
	 * be used to filter assets by the uploader ID and asset name if provided. If the `uploadUserId` is null, the specification will not apply any
	 * uploader-specific filters, potentially returning all assets. If `searchValue` is provided, it filters assets whose names contain the specified search string.
	 *
	 * @param uploadUserId The ID of the user who uploaded the assets. If specified, the query filters to include only assets uploaded by this user.
	 * @param searchValue The search criterion used to filter the assets by name. Only assets with names that contain the provided search string will be included.
	 *                    If this parameter is null or empty, it is ignored in the filtering process.
	 * @return A {@code Specification<Asset>} that can be used with JPA to generate a database query for assets, based on the provided criteria.
	 */

	public Specification<Asset> assetsCriteria(Long uploadUserId, String searchValue) {
		return (root, query, criteriaBuilder) -> {
			Predicate finalPredicate = criteriaBuilder.conjunction();

			// Add condition for uploadUserId if it is not null
			if (uploadUserId != null) {
				finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.equal(root.get("uploaderId"), uploadUserId));
			}

			// Add condition for searchValue if it is not empty or null
			if (searchValue != null && !searchValue.isEmpty()) {
				Predicate searchPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("originalName")), "%" + searchValue.toLowerCase() + "%");
				finalPredicate = criteriaBuilder.and(finalPredicate, searchPredicate);
			}
			return finalPredicate;
		};
	}

    //public Page<AssetAPIDTO> getAssetList(int page, int size) {
	//		Pageable pageable = PageRequest.of(page, size);
	//		//return assetRepository.findAllBasicInfo(pageable);
	//		return null;
	//}
	
	/**
	 * Upload the file to the server and add the related information to database.
	 * @param file
	 * @throws IOException
	 */
	
	public void uploadFile(MultipartFile file) throws IOException {
		Asset newAsset = new Asset();
		
		// The original filename
		String fileName = file.getOriginalFilename();
		newAsset.setOriginalName(fileName);
		
		// Rename the file as <UUID>.<EXTENSION>
        String extension = FilenameUtils.getExtension(fileName);
        String storedFileName = UUID.randomUUID() + "." + extension;
       	newAsset.setStoredName(storedFileName);
       
       	// Other file attributes.
       	newAsset.setSize(file.getSize());
       	newAsset.setUploadedTime(LocalDateTime.now());
       
       	// The related user info
       	//CurrentUserBasicInfoDTO user = (CurrentUserBasicInfoDTO) session.getAttribute("user");
       	newAsset.setUploaderId(userSessionService.validateSession().getBasicInfo().getId());
       
       	// Copy file to the file system before insert the data.
       	File destFile = new File(this.rootLocation + File.separator + storedFileName);
       	FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
       
       	assetRepository.save(newAsset);
	}
	
}
