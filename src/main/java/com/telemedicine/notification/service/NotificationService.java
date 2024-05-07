package com.telemedicine.notification.service;

import com.telemedicine.notification.model.Notification;

public interface NotificationService {
    Notification sendNotification(Notification notificationRequest);
}
