package com.ABN.NotificationSystem.Service;

import com.ABN.NotificationSystem.Enum.Status;
import com.ABN.NotificationSystem.Enum.VisibilityScope;
import com.ABN.NotificationSystem.Model.Alert;
import com.ABN.NotificationSystem.Model.NotificationDelivery;
import com.ABN.NotificationSystem.Model.User;
import com.ABN.NotificationSystem.Model.UserAlertPreference;
import com.ABN.NotificationSystem.Service.notification.NotificationChannel;
import com.ABN.NotificationSystem.repository.AlertRepository;
import com.ABN.NotificationSystem.repository.NotificationDeliveryRepository;
import com.ABN.NotificationSystem.repository.UserAlertPreferenceRepository;
import com.ABN.NotificationSystem.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReminderService {

    private static final Logger log = LoggerFactory.getLogger(ReminderService.class);

    @Autowired
    private NotificationDeliveryRepository deliveryRepository;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private UserAlertPreferenceRepository preferenceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationChannel notificationChannel;

    /**
     * Main method to send reminders for all active alerts
     * Called by scheduler every 2 hours
     */
    @Transactional
    public void sendReminders() {
        log.info("Starting reminder process at {}", LocalDateTime.now());

        LocalDateTime now = LocalDateTime.now();

        // Step 1: Get all active alerts
        List<Alert> activeAlerts = alertRepository.findByStartTimeBeforeAndExpiryTimeAfter(now, now);
        log.info("Found {} active alerts", activeAlerts.size());

        if (activeAlerts.isEmpty()) {
            log.info("No active alerts found. Skipping reminder process.");
            return;
        }

        // Step 2: For each alert, determine eligible users
        for (Alert alert : activeAlerts) {
            if (!alert.isActive()) {
                log.debug("Skipping inactive alert: {}", alert.getId());
                continue;
            }

            try {
                processAlertReminders(alert);
            } catch (Exception e) {
                log.error("Error processing reminders for alert {}: {}", alert.getId(), e.getMessage(), e);
            }
        }

        log.info("Reminder process completed at {}", LocalDateTime.now());
    }

    /**
     * Process reminders for a specific alert
     */
    private void processAlertReminders(Alert alert) {
        log.debug("Processing reminders for alert: {} - {}", alert.getId(), alert.getTitle());

        // Get all users who should receive this alert based on visibility
        List<User> eligibleUsers = getEligibleUsers(alert);
        log.debug("Found {} eligible users for alert {}", eligibleUsers.size(), alert.getId());

        int sentCount = 0;
        int skippedCount = 0;

        for (User user : eligibleUsers) {
            try {
                if (shouldSendReminder(user.getId(), alert.getId())) {
                    sendReminderToUser(user, alert);
                    sentCount++;
                } else {
                    skippedCount++;
                    log.debug("Skipped reminder for user {} (already snoozed)", user.getId());
                }
            } catch (Exception e) {
                log.error("Error sending reminder to user {} for alert {}: {}",
                        user.getId(), alert.getId(), e.getMessage());
            }
        }

        log.info("Alert {} - Sent: {}, Skipped: {}", alert.getId(), sentCount, skippedCount);
    }

    /**
     * Get users eligible to receive this alert based on visibility scope
     */
    private List<User> getEligibleUsers(Alert alert) {
        List<User> allUsers = userRepository.findAll();

        return allUsers.stream()
                .filter(user -> isUserEligible(user, alert))
                .collect(Collectors.toList());
    }

    /**
     * Check if a user is eligible to receive an alert based on visibility scope
     */
    private boolean isUserEligible(User user, Alert alert) {
        VisibilityScope scope = alert.getVisibilityScope();

        switch (scope) {
            case ORG:
                // All users in organization
                return true;

            case TEAM:
                // Only users in the specified team
                return alert.getTeamId() != null &&
                        Objects.equals(user.getTeamId(), alert.getTeamId());

            case USER:
                // Only the specific user
                return alert.getUserId() != null &&
                        Objects.equals(user.getId(), alert.getUserId());

            default:
                log.warn("Unknown visibility scope: {}", scope);
                return false;
        }
    }

    /**
     * Check if we should send a reminder to this user
     * Returns false if user has snoozed the alert for today
     */
    private boolean shouldSendReminder(Long userId, Long alertId) {
        UserAlertPreference preference = preferenceRepository
                .findByUserIdAndAlertId(userId, alertId)
                .orElse(null);

        if (preference == null) {
            // No preference record means user hasn't interacted with alert
            return true;
        }

        // Check if alert is snoozed for today
        LocalDate today = LocalDate.now();
        LocalDate snoozedUntil = preference.getSnoozedUntil();

        if (snoozedUntil != null && !snoozedUntil.isBefore(today)) {
            // Alert is snoozed for today
            return false;
        }

        // Alert is not snoozed (or snooze expired)
        return true;
    }

    /**
     * Send reminder notification to a specific user
     */
    private void sendReminderToUser(User user, Alert alert) {
        log.debug("Sending reminder to user {} for alert {}", user.getId(), alert.getId());

        // Use the notification channel (Strategy pattern)
        notificationChannel.send(user, alert);

        // Log the delivery
        NotificationDelivery delivery = new NotificationDelivery();
        delivery.setAlertId(alert.getId());
        delivery.setUserId(user.getId());
        delivery.setDeliveredAt(LocalDateTime.now());
        delivery.setStatus(Status.SENT);

        deliveryRepository.save(delivery);

        log.debug("Reminder sent and logged for user {} alert {}", user.getId(), alert.getId());
    }


    public void triggerManualReminder(Long alertId) {
        log.info("Manual reminder trigger for alert {}", alertId);

        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found: " + alertId));

        processAlertReminders(alert);
    }
}