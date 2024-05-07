package com.telemedicine.notification.service.impl;

import com.telemedicine.notification.enums.EmailTemplateType;
import com.telemedicine.notification.enums.ErrorCodes;
import com.telemedicine.notification.exception.BusinessException;
import com.telemedicine.notification.model.Notification;
import com.telemedicine.notification.service.NotificationService;
import com.telemedicine.notification.util.Constants;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@Qualifier("Email")
public class MailService implements NotificationService {
    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;
    private final Validator validator;

    private final Configuration freeMarkerConfig;

    @Autowired
    public MailService(JavaMailSender mailSender, MailProperties mailProperties, Validator validator, Configuration freeMarkerConfig) {
        this.mailProperties = mailProperties;
        this.mailSender = mailSender;
        this.validator = validator;
        this.freeMarkerConfig = freeMarkerConfig;
    }


    @Override
    public Notification sendNotification(Notification notificationRequest) {
        Set<ConstraintViolation<Notification>> violations = validator.validate(notificationRequest, Notification.EmailNotification.class);
        if (!violations.isEmpty()) {
            Map<Path, String> body = violations.stream()
                    .collect(Collectors.toMap(ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
            throw new BusinessException(body, HttpStatus.BAD_REQUEST);
        }
        return sendEmail(notificationRequest);
    }

    private Notification sendEmail(Notification request) {
        String subject = request.getSubject();
        String recipientEmail = request.getTo();
        Template template = getTemplate(request.getTemplateType());

        return sendEmailTemplate(mailProperties.getUsername(), recipientEmail, subject, request.getModel(), template);
    }

    private Notification sendEmailTemplate(String from, String to, String subject, Map<String, String> model, Template template) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, StandardCharsets.UTF_8.toString());
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template,model);

            messageHelper.setSubject(subject);
            messageHelper.setText(html, true);
            messageHelper.setFrom(from);
            messageHelper.setTo(to);

            log.info("----MailServiceImpl:sendOtp:: sending mail to: {}----", to);
            mailSender.send(message);
            log.info("----MailServiceImpl:sendOtp:: mail has been sent to: {}----", to);
            return new Notification(Constants.MAIL_SENT + to);
        } catch (MessagingException e) {
            log.error("-------NotificationServiceImpl:sendOtp:: error in sending mail -> {}------",e.getMessage());
            return new Notification(Constants.ERROR_MAIL + to);
        } catch (TemplateException | IOException e) {
            log.error("-------NotificationServiceImpl:sendOtp:: error in sending mail -> {}------",e.getMessage());
            throw new BusinessException(ErrorCodes.NOTIFICATION_ERROR, HttpStatus.BAD_REQUEST);
        }
    }

    private Template getTemplate(EmailTemplateType templateType) {
        try {
            EmailTemplateType[] values = EmailTemplateType.values();
            for(EmailTemplateType templateType1: values) {
                if(templateType1.equals(templateType)) return freeMarkerConfig.getTemplate(EmailTemplateType.OTP_VERIFICATION.getValue());
            }
            throw new BusinessException(ErrorCodes.TEMPLATE_NOT_FOUND, HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            log.error("-------NotificationServiceImpl:getTemplate:: error in sending mail -> {}------",e.getMessage());
            throw new BusinessException(ErrorCodes.TEMPLATE_ERROR, HttpStatus.BAD_REQUEST);
        }
    }

}
