package net.etwig.webapp.services;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import net.etwig.webapp.config.ConfigFile;
import net.etwig.webapp.dto.PositionDTO;
import net.etwig.webapp.model.UserRole;
import net.etwig.webapp.repository.UserRoleRepository;
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
import java.util.Set;

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



	public boolean graphicsRequestNotification(String requester, String eventName) throws Exception {
    	
    	// Get all graphics managers
    	Set<UserRole> graphicsManagers = userRoleRepository.getGraphicsManagers();
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
		//StringBuilder subject = new StringBuilder();
		//subject.append(requester).append(" ");
		//subject.append(" made a graphics request for the event ");
		//subject.append(eventName);

		String
		
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


	/**
	 * Sends an email notification about the approval status of a banner request for a specific event.
	 * The email is sent to both the portfolio and user email addresses.
	 *
	 * @param portfolioEmail The email address associated with the portfolio to which the notification will be sent.
	 * @param userEmail The email address of the user who submitted the banner request.
	 * @param isApproved Boolean indicating whether the banner request was approved or not.
     * @param eventId The ID of the event associated with the banner request.
	 * @param eventName The name of the event associated with the banner request.
	 * @param responseTime The date and time when the response was generated.
	 * @param attachmentName The name of the attachment to be included in the email if the request is approved.
	 * @param attachmentContent The content of the attachment. This can be null if there is no attachment to send.
	 * @throws Exception If there are any issues in generating or sending the email, such as template processing failures or email sending errors.
	 *
	 * <p>The method constructs the email content using a FreeMarker template and sends it with the subject line indicating
	 * the approval status. If the request is approved and the attachment content is not null and is accessible, it includes
	 * the attachment in the email.</p>
	 */

	public void bannerApprovalNotification(
			String portfolioEmail,
			String userEmail,
			boolean isApproved,
			Long eventId,
			String eventName,
			LocalDateTime responseTime,
			String attachmentName,
			Resource attachmentContent

	) throws Exception {

		// Send to portfolio email and user email
    	HashSet<String>recipients = new HashSet<>();
		recipients.add(portfolioEmail);
		//recipients.add(userEmail);

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
		model.put("eventId", eventId);
		model.put("eventName", eventName);
		model.put("responseTime", responseTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
		model.put("appUrl", config.getAppURL());
    	String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

    	sendEmail(recipients, subject, content, attachments);
    }
}