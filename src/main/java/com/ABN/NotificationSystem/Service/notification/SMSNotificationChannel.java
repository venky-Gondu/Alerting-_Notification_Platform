// SMSNotificationChannel.java - Future implementation example
package com.ABN.NotificationSystem.Service.notification;

import com.ABN.NotificationSystem.Model.Alert;
import com.ABN.NotificationSystem.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMSNotificationChannel implements NotificationChannel {

    private static final Logger log = LoggerFactory.getLogger(SMSNotificationChannel.class);

    @Override
    public void send(User user, Alert alert) {
        log.info(" SMS NOTIFICATION (Not implemented in MVP)");
        log.info("   To: {}", user.getName());
        log.info("   Message: [{}] {}", alert.getSeverity(), alert.getTitle());

    }

    @Override
    public String getChannelType() {
        return "SMS";
    }
}