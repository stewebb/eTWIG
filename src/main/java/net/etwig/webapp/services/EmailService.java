package net.etwig.webapp.services;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import net.etwig.webapp.config.ConfigFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Service
public class EmailService {
	
	@Autowired
	private ConfigFile config;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private Configuration freemarkerConfig;

	/**
	 * Sends an email to multiple recipients with optional attachments.
	 * <p>
	 * This method configures and sends an email using the {@link JavaMailSender} interface.
	 * It supports HTML content and can handle multiple recipients as well as file attachments.
	 *
	 * @param to A {@link HashSet} containing the email addresses of the recipients. Must not be empty.
	 * @param subject The subject of the email. Cannot be {@code null} or empty.
	 * @param content The HTML content of the email. Cannot be {@code null} or empty.
	 * @param attachments A {@link HashMap} where the key is the attachment's filename and the value is the {@link Resource} object representing the attachment.
	 *                    Can be {@code null} if there are no attachments to include.
	 *
	 * @throws Exception If there are issues creating or sending the email, such as invalid addresses, missing subject or content, or problems with attachments.
	 */
	private void sendEmail(HashSet<String> to, String subject, String content, HashMap<String, Resource> attachments) throws Exception {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		// Set the display name and from address.
		String fromAddress = ((JavaMailSenderImpl) emailSender).getUsername();
		String displayName = config.getDisplayName();
		String from = String.format("%s <%s>", displayName, fromAddress);

		// Set email properties.
		helper.setFrom(from);
		helper.setTo(to.toArray(new String[0]));  // Convert HashSet to Array
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


	/*
	public boolean graphicsRequestNotification(NewRequestEmailNotificationDTO requestInfo) throws Exception {
    	
    	// Get all graphics managers
    	Set<PositionDTO> graphicsManagers = userRoleRepository.getGraphicsManagers();
    	if(graphicsManagers.isEmpty()) {
    		return false;    	
    	}
    	
   		// Get event info.
    	//
    	
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
	 */
    
    public void bannerApprovalNotification(
			String portfolioEmail,
			String userEmail,
			boolean isApproved,
			String eventName,
			LocalDateTime responseTime,
			String attachmentName,
			Resource attachmentContent

	) throws Exception {

		// Send to portfolio email and user email
    	HashSet<String>recipients = new HashSet<>();
		recipients.add(portfolioEmail);
		recipients.add(userEmail);

		//System.out.println(attachmentName);
		//System.out.println(attachmentContent);

		// Only put asset content if a request was approved, the attachment is readable and it is not null.
    	HashMap<String, Resource> attachments = new HashMap<>();
    	if(isApproved && attachmentContent != null && (attachmentContent.exists() || attachmentContent.isReadable())) {
			attachments.put(attachmentName, attachmentContent);
    	}

    	// Email subject.
        String subject = "The banner request of event " + eventName + " has been " + (isApproved ? "approved" : "declined") + ".";
    	
    	// Email content
    	Template template = freemarkerConfig.getTemplate("_emails/graphic_approval.ftl");
    	HashMap<String, Object> model = new HashMap<>();

		model.put("isApproved", isApproved);
		model.put("eventName", eventName);
		model.put("responseTime", responseTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    	String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

    	sendEmail(recipients, subject, content, attachments);
    }
}