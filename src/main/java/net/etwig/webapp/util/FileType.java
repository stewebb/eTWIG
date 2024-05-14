/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: File extension and the MINE type/Category mappings.
	 */

package net.etwig.webapp.util;

import lombok.Getter;
import org.springframework.http.MediaType;

/**
 * Enumeration representing different types of file formats.
 */

@Getter
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
	TXT(MediaType.TEXT_PLAIN, FileCategory.TEXT),

	// Others
	OTHER(MediaType.APPLICATION_OCTET_STREAM, FileCategory.APPLICATION);

    /**
     * -- GETTER --
     *  Retrieves the MediaType associated with this FileType.
     */

    private final MediaType mediaType;

    /**
     * -- GETTER --
     *  Retrieves the FileCategory associated with this FileType.
     */

    private final FileCategory fileCategory;

	private static final FileType defaultType = OTHER;

	FileType(MediaType mediaType, FileCategory fileCategory) {
		this.mediaType = mediaType;
		this.fileCategory = fileCategory;
	}

    /**
	 * Safely retrieves the FileType enum constant based on the provided string.
	 * If the string is null or not a valid FileType, returns the default FileType (OTHER).
	 *
	 * @param str the string to be converted to a FileType
	 * @return the FileType enum constant associated with the provided string, or the default FileType if the string is null or invalid
	 */

	public static FileType safeValueOf(String str) {
		try {
			return valueOf(str.toUpperCase());
		} catch (NullPointerException | IllegalArgumentException e) {
			return defaultType;
		}
	}
}