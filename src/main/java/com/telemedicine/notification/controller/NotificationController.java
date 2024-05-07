package com.telemedicine.notification.controller;

import com.telemedicine.notification.enums.NotificationType;
import com.telemedicine.notification.model.Notification;
import com.telemedicine.notification.service.impl.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/notification")
public class NotificationController {
    private final NotificationServiceImpl notificationServiceImpl;
    @Autowired
    public NotificationController(NotificationServiceImpl notificationServiceImpl) {
        this.notificationServiceImpl = notificationServiceImpl;
    }
    @PostMapping
    public ResponseEntity<Notification> sendNotification(@RequestBody Notification notificationRequest, @RequestParam(value = "type") NotificationType notificationType) {
        Notification response = notificationServiceImpl.sendNotification(notificationRequest, notificationType);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
