package net.grinecraft.etwig.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private Configuration freemarkerConfig;

    public void sendEmail(String to, String subject, Map<String, Object> model) throws Exception {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String fromAddress = String.format("%s <%s>", "Griffin Hall eTWIG", "etwig.noreply@grinecraft.net");

            //Template t = freemarkerConfig.getTemplate("email-template.ftl");
            //String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            helper.setFrom(fromAddress);
            helper.setTo(to);
            helper.setText("aa", true);
            helper.setSubject(subject);

            emailSender.send(message);
       
    }
}