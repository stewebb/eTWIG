package net.grinecraft.etwig.util.type;

import org.springframework.http.MediaType;

public enum FileType {
	
    JPG(MediaType.IMAGE_JPEG),
    JPEG(MediaType.IMAGE_JPEG),
    PNG(MediaType.IMAGE_PNG),
    GIF(MediaType.IMAGE_GIF),
    PDF(MediaType.APPLICATION_PDF),
    TXT(MediaType.TEXT_PLAIN),
    HTML(MediaType.TEXT_HTML),
    JSON(MediaType.APPLICATION_JSON),
    XML(MediaType.APPLICATION_XML),
	OTHER(MediaType.APPLICATION_OCTET_STREAM);

    private final MediaType mediaType;
    private final static FileType defaultType = OTHER;
    
    FileType(MediaType mediaType) {
        this.mediaType = mediaType;
	}

    public MediaType getMediaType() {
    	return this.mediaType;
    }
    
    public static FileType safeValueOf(String str) {
    	try {
    		return valueOf(str.toUpperCase());
    	} catch (NullPointerException | IllegalArgumentException e) {
    		return defaultType;
    	}    	
    }
 
}