package com.crud.tasks.service.mail;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SimpleEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailCreatorService mailCreatorService;

    public void send(final Mail mail, final MailType mailType, final Map<String, Object> params) {
        LOGGER.info("Start email preparation...");
        try {
            javaMailSender.send(createMimeMessage(mail, mailType, params));
            LOGGER.info("Email has been sent");
        } catch (MailException e) {
            LOGGER.error("Failed to process email sending", e.getMessage(), e);
        }
    }

    private MimeMessagePreparator createMimeMessage(final Mail mail, final MailType mailType, final Map<String, Object> params) {

        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getReceiveEmail());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildMail(mail.getMessage(), mailType, params), true);
        };
    }

    private SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(mail.getReceiveEmail());
        simpleMailMessage.setSubject(mail.getSubject());
        simpleMailMessage.setText(mail.getMessage());
        if (!mail.getToCc().isEmpty()) {
            simpleMailMessage.setCc(mail.getToCc());
        }
        return simpleMailMessage;
    }
}
