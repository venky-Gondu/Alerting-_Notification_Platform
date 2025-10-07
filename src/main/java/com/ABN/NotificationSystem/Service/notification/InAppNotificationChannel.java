package com.ABN.NotificationSystem.Service.notification;


import com.ABN.NotificationSystem.Model.Alert;
import com.ABN.NotificationSystem.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class InAppNotificationChannel implements NotificationChannel {

    private static final Logger log = LoggerFactory.getLogger(InAppNotificationChannel.class);

    @Override
    public void send(User user, Alert alert) {
        // For MVP/Prototype: Log the notification
        // In production: Push to WebSocket, message queue, or notification service

        log.info("📱 IN-APP NOTIFICATION");
        log.info("   To: {} (ID: {})", user.getName(), user.getId());
        log.info("   Alert: {}", alert.getTitle());
        log.info("   Severity: {}", alert.getSeverity());
        log.info("   Message: {}", alert.getMessage());
        log.info("   ----------------------------------");

    }

    @Override
    public String getChannelType() {
        return "IN_APP";
    }
}
