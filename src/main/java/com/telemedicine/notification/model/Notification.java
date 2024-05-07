package com.telemedicine.notification.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.telemedicine.notification.enums.EmailTemplateType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Notification {
    @NotNull(message = "The email address is required.", groups = EmailNotification.class)
    @Email(message = "The email address is invalid", groups = EmailNotification.class)
    private String to;
    private String message;
    private String content;
    @NotNull(groups = EmailNotification.class, message = "subject is required")
    private String subject;
    @NotNull(groups = EmailNotification.class, message = "template type is required")
    private EmailTemplateType templateType;
    @NotNull(groups = EmailNotification.class, message = "model is required")
    private Map<String, String> model;

    public Notification(String message) {
        this.message = message;
    }
    public interface EmailNotification {
    }
}
