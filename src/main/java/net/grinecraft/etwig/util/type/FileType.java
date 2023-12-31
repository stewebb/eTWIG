/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: File extension and the MINE type/Category mappings.
	 */

package net.grinecraft.etwig.util.type;

import org.springframework.http.MediaType;

public enum FileType {
	
	// Applications
	PDF(MediaType.APPLICATION_PDF, FileCategory.APPLICATION),
	XHTML(MediaType.APPLICATION_XHTML_XML, FileCategory.APPLICATION),
	JSON(MediaType.APPLICATION_JSON, FileCategory.APPLICATION),
	
	// Images
	 GIF(MediaType.IMAGE_GIF, FileCategory.IMAGE),
    JPG(MediaType.IMAGE_JPEG, FileCategory.IMAGE),
    JPEG(MediaType.IMAGE_JPEG, FileCategory.IMAGE),
    PNG(MediaType.IMAGE_PNG, FileCategory.IMAGE),
    
    // Texts
    HTML(MediaType.TEXT_HTML, FileCategory.TEXT),
    XML(MediaType.TEXT_XML, FileCategory.TEXT),
    PLAIN(MediaType.TEXT_PLAIN, FileCategory.TEXT),
    
    // Others
	OTHER(MediaType.APPLICATION_OCTET_STREAM, FileCategory.APPLICATION);

    private final MediaType mediaType;
    private final FileCategory fileCategory;
    
    private final static FileType defaultType = OTHER;
    
    FileType(MediaType mediaType, FileCategory fileCategory) {
        this.mediaType = mediaType;
		this.fileCategory = fileCategory;
	}

    public MediaType getMediaType() {
    	return this.mediaType;
    }
    
    public FileCategory getFileCategory() {
    	return this.fileCategory;
    }
    
    public static FileType safeValueOf(String str) {
    	try {
    		return valueOf(str.toUpperCase());
    	} catch (NullPointerException | IllegalArgumentException e) {
    		return defaultType;
    	}    	
    }
 
}