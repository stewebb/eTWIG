package net.etwig.webapp.dto;

import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.model.TwigTemplate;

@Getter
@ToString
public class TwigTemplateDTO {

	private Long id;
	private String design;
	
	public TwigTemplateDTO(TwigTemplate twigTemplate) {
		this.id = twigTemplate.getId();
		this.design = twigTemplate.getDesign();
	}
}



