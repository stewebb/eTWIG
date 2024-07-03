package net.etwig.webapp.dto;

import java.time.LocalDateTime;

import org.apache.commons.io.FilenameUtils;

import lombok.*;
import net.etwig.webapp.model.Asset;
import net.etwig.webapp.util.FileType;

/**
 * Data Transfer Object (DTO) representing essential details of an asset used in API communications.
 * This class encapsulates properties that identify and describe the asset, making it suitable for
 * transporting asset data across network or application layers.
 * <p>
 * Annotations:
 * - @Getter: Automatically generates getter methods for all fields, ensuring read-only access.
 * - @ToString: Automatically generates a custom toString method that includes all fields, aiding in debugging and logging.
 * <p>
 * Fields:
 * - id: Unique identifier for the asset.
 * - name: Original name of the file when it was uploaded.
 * - type: Enum indicating the file type, determined by the file extension.
 * - size: Size of the file in bytes.
 * - uploader: Full name of the user who uploaded the file.
 * - lastModified: Date and time when the file was last modified.
 * - canDelete: Boolean flag indicating whether the asset can be deleted, mutable via setter.
 * <p>
 * Constructor:
 * Initializes the object with the properties of an Asset, converting necessary fields and determining file type.
 * <p>
 * Methods:
 * - getMediaType(): Returns the media type associated with the file type.
 * - getFileCategory(): Returns the category of the file based on its type.
 */

@Getter
@ToString
public class AssetAPIDTO {

	private final Long id;
	private final String name;
	private final FileType type;
	private final long size;
	private final String uploader;
	private final LocalDateTime lastModified;

	@Setter
	private boolean canDelete;
	
	public AssetAPIDTO(Asset asset) {
		this.id = asset.getId();
		this.name = asset.getOriginalName();
		this.type = FileType.safeValueOf(FilenameUtils.getExtension(this.name));
		this.size = asset.getSize();
		this.uploader = asset.getUploader().getFullName();
		this.lastModified = asset.getUploadedTime();
		this.canDelete = false;
	}
	
	public String getMediaType() {
		return this.type.getMediaType().toString();
	}
	
	public String getFileCategory() {
		return this.type.getFileCategory().toString();
	}
}
