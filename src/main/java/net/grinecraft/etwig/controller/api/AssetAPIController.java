package net.grinecraft.etwig.controller.api;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.grinecraft.etwig.services.AssetService;
import net.grinecraft.etwig.util.WebReturn;

@RestController
public class AssetAPIController {
	
	@Autowired
	private AssetService assetService;

	@RequestMapping(value = "/api/private/upload", method = RequestMethod.POST)
	public Map<String, Object> upload(@RequestParam("file") MultipartFile file) throws IOException {
		
		// Null check...
		if(file == null) {
			return WebReturn.errorMsg("The file is null.", false);
		} 
		
		// Copy file and add related info
		assetService.uploadFile(file);
		return WebReturn.errorMsg("", true);
    }
}
