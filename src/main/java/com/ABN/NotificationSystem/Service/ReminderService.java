package com.ABN.NotificationSystem.Service;

import com.ABN.NotificationSystem.repository.AlertRepository;
import com.ABN.NotificationSystem.repository.NotificationDeliveryRepository;
import com.ABN.NotificationSystem.repository.UserAlertPreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReminderService {
    @Autowired
    private NotificationDeliveryRepository deliveryRepository;
    @Autowired private AlertRepository alertRepository;
    @Autowired private UserAlertPreferenceRepository preferenceRepository;

    public void sendReminders() {
        // check active alerts, users not snoozed, send "in-app" reminders
    }
}
