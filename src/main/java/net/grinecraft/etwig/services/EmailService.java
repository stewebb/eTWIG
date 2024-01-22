package net.grinecraft.etwig.services;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import net.grinecraft.etwig.config.ConfigFile;
import net.grinecraft.etwig.dto.EventDetailsDTO;
import net.grinecraft.etwig.dto.PositionDTO;
import net.grinecraft.etwig.model.User;
import net.grinecraft.etwig.model.UserRole;
import net.grinecraft.etwig.repository.UserRoleRepository;

@Service
public class EmailService {
	
    //Template t = freemarkerConfig.getTemplate("email-template.ftl");
    //String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
	
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
		UserRole requesterRole = userRoleService.findById(Long.parseLong(requestInfo.get("requesterRole").toString()));
		EventDetailsDTO event = eventService.findById(Long.parseLong(requestInfo.get("eventId").toString()));
		User requester = requesterRole.getUser();
		
		// Generate email subject and content.
		StringBuilder subject = new StringBuilder();
		subject.append(requesterRole.getPosition() + " ");
		subject.append(requester.getFullName());
		subject.append(" made a graphics request on the event ");
		subject.append(event.getName());
		subject.append(" on " + LocalDateTime.now());
		
		//String subject = requester.getFullName() + " made a ";
		// Iterate all graphics managers
		for(PositionDTO graphicsManager : graphicsManagers) {
			sendEmail(graphicsManager.getEmail(), subject.toString(), "1");
		}
    		
    	//System.out.println(graphicsManagers);
		return true;
    }
}