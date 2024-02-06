package net.grinecraft.etwig.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
import net.grinecraft.etwig.dto.graphics.FinalizedRequestsDetailsDTO;
import net.grinecraft.etwig.dto.graphics.NewRequestEmailNotificationDTO;
import net.grinecraft.etwig.dto.user.UserDTO;
import net.grinecraft.etwig.model.Asset;
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
    private AssetService assetService;
    
    @SuppressWarnings("null")
	private void sendEmail(String to, String subject, String content, HashMap<String, Resource> attachments) throws Exception {
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
            
            // Add attachments
            if(attachments != null) {
            	for (Map.Entry<String, Resource> attachment : attachments.entrySet()) {
                    helper.addAttachment(attachment.getKey(), attachment.getValue());
                }
            }
            
            emailSender.send(message);
    }
    
    public boolean graphicsRequestNotification(NewRequestEmailNotificationDTO requestInfo) throws Exception {
    	
    	// Get all graphics managers
    	Set<PositionDTO> graphicsManagers = userRoleRepository.getGraphicsManagers();
    	if(graphicsManagers.isEmpty()) {
    		return false;    	
    	}
    	
   		// Get event info.
    	
    	
    	//Long eventId = Long.parseLong(requestInfo.get("eventId").toString());
    	//GraphicsRequestEventInfoDTO event = eventService.findEventsForGraphicsRequestById(eventId);
		//UserRole requesterRole = userRoleService.findById(Long.parseLong(requestInfo.get("requesterRole").toString()));
		//User requester = requesterRole.getUser();
		//System.out.print(event);
		
		// Generate email subject.
		StringBuilder subject = new StringBuilder();
		subject.append(requestInfo.getRequesterPosition() + " ");
		subject.append(requestInfo.getRequesterName());
		subject.append(" made a graphics request for the event ");
		//subject.append(requestInfo.getEventName());
		
		// Generate email content
		Template t = freemarkerConfig.getTemplate("_emails/graphic_request.ftl");
		HashMap<String, Object> model = new HashMap<String, Object>();
		//model.put("eventInfo", event);
		model.put("requestInfo", requestInfo);
		//model.put("organizer", new UserDTO(requester));
	    String content = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
		
		// Iterate all graphics managers
		for(PositionDTO graphicsManager : graphicsManagers) {
			sendEmail(graphicsManager.getEmail(), subject.toString(), content, null);
		}
    		
    	//System.out.println(graphicsManagers);
		return true;
    }
    
    public void graphicsApprovalNotification(FinalizedRequestsDetailsDTO approvalInfo) throws Exception {
    	
    	//System.out.println(approvalInfo);
    	
    	// Get response and event info.
    	String approvedStr = approvalInfo.isApproved() ? "approved" : "declined";
    	
    	// Get assets info and content
    	HashMap<String, Resource> attachments = new HashMap<String, Resource>();

    	// Only get asset content when a request was approved.
    	if(approvalInfo.isApproved()) {
    		Long assetId = approvalInfo.getAssetId();
        	Asset asset = assetService.getAssetDetailsById(assetId);
    		Resource resource = assetService.getAssetContent(asset);
        	String assetName = asset.getOriginalName();
        	
    		if(resource != null && (resource.exists() || resource.isReadable())) {
    			attachments.put(assetName, resource);
    		}
    	}
    	
		
    	// Generate email subject.
    	StringBuilder subject = new StringBuilder();
    	subject.append("The graphics request of event " + approvalInfo.getEventName());
    	subject.append(" has been " + approvedStr + ".");
    	
    	// Generate email content
    	Template t = freemarkerConfig.getTemplate("_emails/graphic_approval.ftl");
    	HashMap<String, Object> model = new HashMap<String, Object>();
    	model.put("approvalInfo", approvalInfo);
    	model.put("approvedStr", approvedStr);
    	String content = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

    	sendEmail(approvalInfo.getRequestRoleEmail(), subject.toString(), content, attachments);
    }
}