package com.telemedicine.notification.service.impl;

import com.telemedicine.notification.enums.NotificationType;
import com.telemedicine.notification.factory.NotificationFactory;
import com.telemedicine.notification.model.Notification;
import com.telemedicine.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceImpl {
    private final NotificationFactory notificationFactory;

    @Autowired
    public NotificationServiceImpl(NotificationFactory notificationFactory) {
        this.notificationFactory = notificationFactory;
    }

    public Notification sendNotification(Notification request, NotificationType type) {
        NotificationService notificationService = notificationFactory.getNotificationType(type);
        return notificationService.sendNotification(request);
    }
}
