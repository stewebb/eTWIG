/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The controller for public assets.
 	*/

package net.etwig.webapp.controller.pages;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.etwig.webapp.model.Asset;
import net.etwig.webapp.services.AssetService;
import net.etwig.webapp.util.type.FileType;

@Controller
@RequestMapping(value = "/assets")
public class AssetPageController {
	
	@Autowired
	private AssetService assetService;

	/**
	 * The root location, redirect to index page.
	 * @location /asset/
	 * @permission All users, including visitors
	 */

	@GetMapping("/")
	public String root(){
		return "redirect:/index.do";
	}

	/**
	 * Asset index page.
	 * @location /asset/index.do
	 * @permission All logged in users
	 */

	@GetMapping("index.do")
	public String index(Model model){

		// TODO Make an index page for events
		return null;
	}

	/**
	 * Get the content of an asset.
	 * @param assetId The id of this asset.
	 * @return The file content.
	 * @location /asset/content.do
	 * @permission All users, including visitors
	 */
	
	@SuppressWarnings("null")
	@GetMapping(value = "/content.do")
	public ResponseEntity<Resource> content(@RequestParam Long assetId, @RequestParam (required=false) String download) throws Exception {
		
		// Get asset info, content and null check.
		Asset asset = assetService.getAssetDetailsById(assetId);
		Resource resource = assetService.getAssetContent(asset);
		if(resource == null) {
			return ResponseEntity.notFound().build();
		}
				
        if (resource.exists() || resource.isReadable()) {
        	
        	// In force download mode, set the MIME type to application/octet-stream
        	FileType fileType;
        	if(BooleanUtils.toBoolean(download)) {
        		fileType = FileType.OTHER;
        	}
        	
        	// Or the MIME type is based on the file suffix.
        	else {
            	fileType = FileType.safeValueOf(FilenameUtils.getExtension(resource.getFilename()));
        	}
        	
        	return ResponseEntity.ok()
        			.contentType(fileType.getMediaType())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + asset.getOriginalName() + "\"")
        			.body(resource);
        } 
        
        // If the file not exists or has no read permission (this is managed by the file system of the server)
        // Just simply return a HTTP status 404 code.
        else {
        	return ResponseEntity.notFound().build();
        } 
    }

	/**
	 * The embedded asset selector page
	 * @location /asset/_selector.do
	 * @permission All logged in users
	 */

	@RequestMapping("_selector.do")
	public String selector(Model model) throws Exception{
		return "assets/selector";
	}

	// TODO asset admin page
}
