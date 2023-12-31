/**
 * eTWIG - The event and banner management software for residential halls and student unions.
 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
 * @license: MIT
 * @author: Steven Webb [xiaoancloud@outlook.com]
 * @website: https://etwig.grinecraft.net
 * @function: The controller for public assets.
 */

package net.grinecraft.etwig.controller.twig;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
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

	@RequestMapping(value = "/twig/assets")
	public ResponseEntity<Resource> assets(@RequestParam Long assetId) throws Exception {
		
		// Get the asset content and null check.
		Resource asset = assetService.getAssetContentById(assetId);
		if(asset == null) {
			return ResponseEntity.notFound().build();
		}
				
        if (asset.exists() || asset.isReadable()) {
        	FileType fileType = FileType.safeValueOf(FilenameUtils.getExtension(asset.getFilename()));
        	System.out.println(fileType);
        	return ResponseEntity.ok().contentType(fileType.getMediaType()).body(asset);
        } else {
        	return ResponseEntity.notFound().build();
        } 
    }
	
}
