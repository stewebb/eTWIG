/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The services for all assets-related options.
	*/

package net.grinecraft.etwig.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;
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
import net.grinecraft.etwig.model.Asset;
import net.grinecraft.etwig.model.User;
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
		if(assetRepository == null) {
			return null;
		}
		
		Optional<Asset> assetOpt = assetRepository.findById(id);
		return assetOpt.isPresent() ? assetOpt.get() : null;
	}
	
	/**
	 * Get the content of an asset by its id.
	 * @param id
	 * @return The file content.
	 * @throws Exception
	 */
	
	public Resource getAssetContentById(long id) throws Exception {
		
		// Get file info and check existence.
		Asset asset = this.getAssetDetailsById(id);
		if(asset == null) {
			return null;
		}
		
		// Retrieve the file from the file system
		Path file = rootLocation.resolve(asset.getStoredName());
		return new UrlResource(file.toUri());		
	}
	
	public Page<AssetBasicInfoDTO> getAssetList(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return assetRepository.findAllBasicInfo(pageable);
	}
	
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
        newAsset.setFileSize(file.getSize());
        newAsset.setLastModified(LocalDateTime.now());
        
        // The related user info
        User user = (User) session.getAttribute("user");
        newAsset.setEditor(user.getId());
        
        // Copy file to the file system before insert the data.
        File destFile = new File(this.rootLocation + File.separator + storedFileName);
        FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
        
        assetRepository.save(newAsset);
	}
	

}
