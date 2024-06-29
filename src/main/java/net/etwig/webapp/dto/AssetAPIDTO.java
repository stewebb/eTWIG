package net.etwig.webapp.dto;

import java.time.LocalDateTime;

import org.apache.commons.io.FilenameUtils;

import lombok.*;
import net.etwig.webapp.model.Asset;
import net.etwig.webapp.util.FileType;

@Getter
@ToString
public class AssetAPIDTO {

	private final Long id;
	private final String name;
	private final FileType type;
	private final long size;
	private final String uploader;
	private final LocalDateTime lastModified;
	
	public AssetAPIDTO(Asset asset) {
		this.id = asset.getId();
		this.name = asset.getOriginalName();
		this.type = FileType.safeValueOf(FilenameUtils.getExtension(this.name));
		this.size = asset.getSize();
		this.uploader = asset.getUploader().getFullName();
		this.lastModified = asset.getUploadedTime();
	}
	
	public String getMediaType() {
		return this.type.getMediaType().toString();
	}
	
	public String getFileCategory() {
		return this.type.getFileCategory().toString();
	}
}
