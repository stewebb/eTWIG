package net.grinecraft.etwig.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import net.grinecraft.etwig.config.ConfigFile;
import net.grinecraft.etwig.dto.PositionDTO;
import net.grinecraft.etwig.dto.events.GraphicsRequestEventInfoDTO;
import net.grinecraft.etwig.dto.user.UserDTO;
import net.grinecraft.etwig.model.User;
import net.grinecraft.etwig.model.UserRole;
import net.grinecraft.etwig.repository.UserRoleRepository;

@Service
public class EmailService {
	
	@Autowired
	private ConfigFile config;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private Configuration freemarkerConfig;
    
    @Autowired
    private UserRoleRepository userRoleRepository;
    
    @Autowired
    private UserRoleService userRoleService;
    
    @Autowired
    private EventService eventService;
    
    @SuppressWarnings("null")
	private void sendEmail(String to, String subject, String content) throws Exception {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            // Set the display name and from address.
            String fromAddress = ((JavaMailSenderImpl) emailSender).getUsername();
            String displayName = config.getDisplayName();
            String from = String.format("%s <%s>", displayName, fromAddress);
            
            // Set email properties.
            helper.setFrom(from);
            helper.setTo(to);
            helper.setText(content, true);
            helper.setSubject(subject);

            emailSender.send(message);
    }
    
    public boolean graphicsRequest(Map<String, Object> requestInfo) throws Exception {

    	// Get all graphics managers
    	Set<PositionDTO> graphicsManagers = userRoleRepository.getGraphicsManagers();
    	if(graphicsManagers.isEmpty()) {
    		return false;    	
    	}
    	
   		// Get requester and event info.
    	Long eventId = Long.parseLong(requestInfo.get("eventId").toString());
    	GraphicsRequestEventInfoDTO event = eventService.findEventsForGraphicsRequestById(eventId);
		UserRole requesterRole = userRoleService.findById(Long.parseLong(requestInfo.get("requesterRole").toString()));
		User requester = requesterRole.getUser();
		System.out.print(event);
		
		// Generate email subject.
		StringBuilder subject = new StringBuilder();
		subject.append(requesterRole.getPosition() + " ");
		subject.append(requester.getFullName());
		subject.append(" made a graphics request on ");
		subject.append(event.getName());
		
		// Generate email content
		Template t = freemarkerConfig.getTemplate("_emails/graphic_request.ftl");
		HashMap<String, Object> model = new HashMap<String, Object>();
		model.put("eventInfo", event);
		model.put("requestInfo", requestInfo);
		model.put("organizer", new UserDTO(requester));
	    String content = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
		
		// Iterate all graphics managers
		for(PositionDTO graphicsManager : graphicsManagers) {
			sendEmail(graphicsManager.getEmail(), subject.toString(), content);
		}
    		
    	//System.out.println(graphicsManagers);
		return true;
    }
}