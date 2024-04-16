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

import net.grinecraft.etwig.config.ConfigFile;
import net.grinecraft.etwig.dto.AssetBasicInfoDTO;
import net.grinecraft.etwig.dto.user.UserDTO;
import net.grinecraft.etwig.model.Asset;
import net.grinecraft.etwig.repository.AssetRepository;
import net.grinecraft.etwig.util.FileUtils;

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
	 * Get the details an asset (but not the file content) by its id.
	 * @param id The id of this asset.
	 * @return The asset object with that Id, or null if no asset with that id.
	 */
	
	public Asset getAssetDetailsById(long id) {
		return assetRepository == null ? null : assetRepository.findById(id).orElse(null);
	}
	
	
	/**
	 * Get the content of an asset by its id.
	 * @param id
	 * @return The file content.
	 * @throws Exception
	 */
	
	@SuppressWarnings("null")
	public Resource getAssetContent(Asset asset) throws Exception {
		
		return (asset == null) ? null : new UrlResource(rootLocation.resolve(asset.getStoredName()).toUri());
		
		// Retrieve the file from the file system
		//Path file = rootLocation.resolve(asset.getStoredName());
		//System.out.println(file);
		//return new UrlResource(file.toUri());		
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
       	UserDTO user = (UserDTO) session.getAttribute("user");
       	newAsset.setUploaderId(user.getId());
       
       	// Copy file to the file system before insert the data.
       	File destFile = new File(this.rootLocation + File.separator + storedFileName);
       	FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
       
       	assetRepository.save(newAsset);
	}
	
}
