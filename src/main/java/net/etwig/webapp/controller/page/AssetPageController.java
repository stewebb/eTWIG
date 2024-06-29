/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The controller for public assets.
 	*/

package net.etwig.webapp.controller.page;

import org.apache.commons.io.FilenameUtils;
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
import net.etwig.webapp.util.FileType;

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
	 * Retrieves the content of an asset based on its ID.
	 *
	 * <p>This method fetches the details of the asset and its content. If the content is found,
	 * it will be returned with the appropriate MIME type. If the content is not found, a 404 status
	 * code will be returned.</p>
	 *
	 * <p>The content can be retrieved in two modes:
	 * <ul>
	 *   <li>Inline: The content is displayed in the browser.</li>
	 *   <li>Download: The content is forced to be downloaded as a file.</li>
	 * </ul>
	 * The mode is determined by the 'download' parameter.</p>
	 *
	 * @param assetId The unique identifier of the asset.
	 * @param download Optional parameter that forces the content to be downloaded if set.
	 * @return The ResponseEntity containing the asset content or a 404 status if not found.
	 * @throws Exception If an error occurs during the retrieval of the asset content.
	 *
	 * @location /asset/content.do
	 * @permission All users, including visitors
	 */
	
	// @SuppressWarnings("null")
	@GetMapping(value = "/content.do")
	public ResponseEntity<Resource> content(
			@RequestParam Long assetId,
			@RequestParam (required=false) Boolean download
	) throws Exception {
		
		// Get asset info, content and null check.
		Asset asset = assetService.getAssetDetailsById(assetId);
		Resource resource = assetService.getAssetContent(asset);
		if(resource == null) {
			return ResponseEntity.notFound().build();
		}
				
        if (resource.exists() || resource.isReadable()) {
        	
        	// In force download mode, set the MIME type to application/octet-stream
			// Otherwise, set the MIME type is based on the file suffix.
        	FileType fileType = (Boolean.TRUE.equals(download)) ?
					FileType.OTHER :
					FileType.safeValueOf(FilenameUtils.getExtension(resource.getFilename()));
        	
        	return ResponseEntity.ok()
        			.contentType(fileType.getMediaType())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + asset.getOriginalName() + "\"")
        			.body(resource);
        } 
        
        // If the file not exists or has no read permission (this is managed by the file system of the server)
        // Just simply return an HTTP status 404 code.
        else {
        	return ResponseEntity.notFound().build();
        } 
    }

	/**
	 * Handles the HTTP GET request mapped to "list.do". This method serves as a straightforward controller method that
	 * directs the user to the "assets/list" view. It's typically used to display a list page for assets within a web application.
	 *
	 * @return The logical name of the view that will present the list of assets, aligning with the MVC architecture of Spring.
	 * @location /asset/list.do
	 * @permission All logged in users
	 */

	@GetMapping("list.do")
	public String list(){
		return "assets/list";
	}

	@GetMapping("upload.do")
	public String upload(){
		return "assets/upload";
	}

	/**
	 * The embedded asset selector page
	 * @location /asset/_selectFile.do
	 * @permission All logged in users
	 */

	@RequestMapping("_selectFile.do")
	public String selector(Model model) throws Exception{
		return "assets/selector";
	}

	// TODO asset admin page
}
