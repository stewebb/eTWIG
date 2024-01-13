package net.grinecraft.etwig.dto;

import java.time.LocalDateTime;

import org.apache.commons.io.FilenameUtils;
import lombok.*;
import net.grinecraft.etwig.model.Asset;
import net.grinecraft.etwig.model.User;
import net.grinecraft.etwig.util.NameUtils;
import net.grinecraft.etwig.util.type.FileType;

@Getter
@ToString
public class AssetBasicInfoDTO {

	private Long id;
	
	private String name;
		
	private FileType type;
	
	private int size;
	
	private String uploader;
	
	private LocalDateTime lastModified;
	
	public AssetBasicInfoDTO(Asset asset) {
		
		this.id = asset.getId();
		this.name = asset.getOriginalName();
		this.type = FileType.safeValueOf(FilenameUtils.getExtension(this.name));
		this.size = asset.getFileSize();
		
		User uploader = asset.getUser();
		this.uploader = NameUtils.nameMerger(uploader.getFirstName(), uploader.getMiddleName(), uploader.getLastName());
		this.lastModified = asset.getLastModified();
	}
	
	public String getMediaType() {
		return this.type.getMediaType().toString();
	}
	
	public String getFileCategory() {
		return this.type.getFileCategory().toString();
	}
}
