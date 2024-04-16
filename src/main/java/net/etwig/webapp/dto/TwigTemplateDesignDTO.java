package net.etwig.webapp.dto;

import java.util.Map;

import lombok.Getter;
import lombok.ToString;
import net.etwig.webapp.model.TwigTemplate;
import net.etwig.webapp.util.JSONUtils;

@Getter
@ToString
public class TwigTemplateDesignDTO {

	private Long id;
	
	private Map<String, Object> design;
	
	public TwigTemplateDesignDTO(TwigTemplate twigTemplate) throws Exception {
		
		this.id = twigTemplate.getId();
		
		JSONUtils jsonUtils = new JSONUtils();
		this.design = jsonUtils.jsonToMap(twigTemplate.getDesign());
	}
}
