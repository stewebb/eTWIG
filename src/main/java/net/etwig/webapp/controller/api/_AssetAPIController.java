package net.etwig.webapp.controller.api;

import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.etwig.webapp.model.Asset;
import net.etwig.webapp.services.AssetService;
import net.etwig.webapp.util.WebReturn;
import net.etwig.webapp.util.FileCategory;
import net.etwig.webapp.util.FileType;

@RestController
@RequestMapping(value = "/api/private/")
public class _AssetAPIController {
	
	@Autowired
	private AssetService assetService;

	@GetMapping("/getImageInfo")
    public Map<String, Object> getImageInfo(@RequestParam Long assetId) throws Exception {
		
		// Asset information check.
		Asset asset = assetService.getAssetDetailsById(assetId);
		if(asset == null) {
			return WebReturn.errorMsg("This asset with assetId=" + assetId + " cannot be found in the database.", false);
		}
		
		// Asset content check.
		Resource resource = assetService.getAssetContent(asset);
		if (!resource.exists() && !resource.isReadable()) {
			return WebReturn.errorMsg("This asset " + asset.getOriginalName() + " doesn't exist or eTWIG has no read access on it.", false);
		}
		
		// Image check.
		FileType fileType = FileType.safeValueOf(FilenameUtils.getExtension(resource.getFilename()));
		if(fileType.getFileCategory() != FileCategory.IMAGE) {
			return WebReturn.errorMsg(
					"This asset " + asset.getOriginalName() + " isn't an image. The MIME type of it is " + fileType.getMediaType().toString(), false);
		}
	
		// Read the image.
		BufferedImage image = ImageIO.read(resource.getInputStream());
		
		// Get and return image info.
		Map<String, Integer> imageInfo = new LinkedHashMap<String, Integer>();
		imageInfo.put("width", image.getWidth());
		imageInfo.put("height", image.getHeight());
		
		Map<String, Object> myReturn = WebReturn.errorMsg(null, true);
		myReturn.put("imageInfo", imageInfo);
		return myReturn;

    }
}
