package net.grinecraft.etwig.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Template;
import net.grinecraft.etwig.DAO.EventService;
import net.grinecraft.etwig.DAO.EventsRepository;
import net.grinecraft.etwig.Model.Events;
import freemarker.template.Configuration;

@RestController
public class IndexController {
	
    @Autowired
    Configuration configuration;
    
    @Autowired
    EventService eventService;
	
	@RequestMapping("/")  
	public String hello() throws Exception{  
		Template template = configuration.getTemplate("index.ftl");

	    // prepare data
        Map<String, String> data = new HashMap<>();
        data.put("name", "Max Mustermann");
        
        String readyParsedTemplate = FreeMarkerTemplateUtils
                .processTemplateIntoString(template, data);

        return readyParsedTemplate;
	}
	
	@RequestMapping("/events")  
	public String ev() {
		System.out.println(eventService.list());
		//for(Events e: es) {
		//	
		//}
		return "1";
		
	}
}
