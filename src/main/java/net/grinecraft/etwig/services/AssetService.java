/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The services for all assets-related options..
	*/

package net.grinecraft.etwig.services;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import net.grinecraft.etwig.config.ConfigFile;
import net.grinecraft.etwig.model.Asset;
import net.grinecraft.etwig.repository.AssetRepository;

@Service
public class AssetService {
	
	private final Path rootLocation;
	
	@Autowired
	private AssetRepository assetRepository;
	
	@Autowired
    public AssetService(ConfigFile config) {
        this.rootLocation = Paths.get(config.getRootLocation());
    }
	
	/**
	 * Get the details an asset (but not the file content) by its Id.
	 * @param id The Id of this asset.
	 * @return The asset object with that Id, or null if no asset with that Id.
	 */
	
	public Asset getAssetDetailsById(long id) {
		if(assetRepository == null) {
			return null;
		}
		
		Optional<Asset> assetOpt = assetRepository.findById(id);
		return assetOpt.isPresent() ? assetOpt.get() : null;
	}
	
	public Resource getAssetContentById(long id) throws Exception {
		
		// Get file info and check existence.
		Asset asset = this.getAssetDetailsById(id);
		if(asset == null) {
			return null;
		}
		
		
		// Retrieve the file from the file system
		Path file = rootLocation.resolve(asset.getFileName());
		return new UrlResource(file.toUri());		
	}
}
