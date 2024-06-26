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
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import net.etwig.webapp.config.ConfigFile;
import net.etwig.webapp.dto.AssetBasicInfoDTO;
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
	 * Get the list of assets with pages.
	 * @param page
	 * @param size
	 * @return
	 */
	
	public Page<AssetBasicInfoDTO> getAssetList(int page, int size) {
			Pageable pageable = PageRequest.of(page, size);
			return assetRepository.findAllBasicInfo(pageable);
	}
	
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
        String storedFileName = UUID.randomUUID().toString() + "." + extension;
       	newAsset.setStoredName(storedFileName);
       
       	// Other file attributes.
       	newAsset.setSize(file.getSize());
       	newAsset.setUploadedTime(LocalDateTime.now());
       
       	// The related user info
       	CurrentUserBasicInfoDTO user = (CurrentUserBasicInfoDTO) session.getAttribute("user");
       	newAsset.setUploaderId(user.getId());
       
       	// Copy file to the file system before insert the data.
       	File destFile = new File(this.rootLocation + File.separator + storedFileName);
       	FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
       
       	assetRepository.save(newAsset);
	}
	
}
