package net.etwig.webapp.dto;

import java.time.LocalDateTime;

import org.apache.commons.io.FilenameUtils;

import lombok.*;
import net.etwig.webapp.model.Asset;
import net.etwig.webapp.util.type.FileType;

@Getter
@ToString
public class AssetBasicInfoDTO {

	private Long id;
	
	private String name;
		
	private FileType type;
	
	private long size;
	
	private String uploader;
	
	private LocalDateTime lastModified;
	
	public AssetBasicInfoDTO(Asset asset) {
		
		
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
