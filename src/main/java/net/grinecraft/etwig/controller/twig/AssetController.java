/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The controller for public assets.
 	*/

package net.grinecraft.etwig.controller.twig;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.grinecraft.etwig.services.AssetService;
import net.grinecraft.etwig.util.type.FileType;

@Controller
public class AssetController {
	
	@Autowired
	private AssetService assetService;

	/**
	 * Get the content of an asset.
	 * @param assetId The id of this asset.
	 * @return The file content.
	 * @throws Exception
	 * @Permissions PUBLIC ACCESS
	 */
	
	@RequestMapping(value = "/twig/assets")
	public ResponseEntity<Resource> assets(@RequestParam Long assetId) throws Exception {
		
		// Get asset content and null check.
		Resource asset = assetService.getAssetContentById(assetId);
		if(asset == null) {
			return ResponseEntity.notFound().build();
		}
				
        if (asset.exists() || asset.isReadable()) {
        	FileType fileType = FileType.safeValueOf(FilenameUtils.getExtension(asset.getFilename()));
        	return ResponseEntity.ok().contentType(fileType.getMediaType()).body(asset);
        } 
        
        // If the file not exists or has no read permission (this is managed by the file system of the server)
        // Just simply return a HTTP status 404 code.
        else {
        	return ResponseEntity.notFound().build();
        } 
    }
	
}
