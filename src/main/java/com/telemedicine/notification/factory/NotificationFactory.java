package com.telemedicine.notification.factory;

import com.telemedicine.notification.enums.ErrorCodes;
import com.telemedicine.notification.enums.NotificationType;
import com.telemedicine.notification.exception.BusinessException;
import com.telemedicine.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class NotificationFactory {
    private final NotificationService mailService;
    public NotificationFactory(@Qualifier("Email") final NotificationService mailService) {
        this.mailService = mailService;
    }

    public NotificationService getNotificationType(NotificationType type) {
        switch(type.getValue()) {
            case "EMAIL":
                return mailService;
            default:
                throw new BusinessException(ErrorCodes.NOTIFICATION_INVALID_TYPE, HttpStatus.BAD_REQUEST);
        }
    }
}
