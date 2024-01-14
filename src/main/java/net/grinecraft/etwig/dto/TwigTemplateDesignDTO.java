package net.grinecraft.etwig.dto;

import java.util.Map;

import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.model.TwigTemplate;
import net.grinecraft.etwig.util.JSONUtils;

@Getter
@ToString
public class TwigTemplateDesignDTO {

	private Long id;
	
	private Map<String, Object> background;
	
	private Map<String, Object> logo;
	
	private Map<String, Object> title;
	
	public TwigTemplateDesignDTO(TwigTemplate twigTemplate) throws Exception {
		
		this.id = twigTemplate.getId();
		
		JSONUtils jsonUtils = new JSONUtils();
		this.background = jsonUtils.jsonToMap(twigTemplate.getBackground());
		this.logo = jsonUtils.jsonToMap(twigTemplate.getLogo());
		this.title = jsonUtils.jsonToMap(twigTemplate.getTitle());
	}
}
