package com.ABN.NotificationSystem.Service;

import com.ABN.NotificationSystem.Enum.Severity;
import com.ABN.NotificationSystem.Enum.VisibilityScope;
import com.ABN.NotificationSystem.Model.Alert;
import com.ABN.NotificationSystem.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertService {
    @Autowired
    private AlertRepository alertRepository;

    public Alert createAlert(Alert alert) {
        return alertRepository.save(alert);
    }

    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }


    public Alert getAlertsById(Long Id) {
        return alertRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Alert not found with id " + Id));

    }

    public Alert updateAlert(Long Id,Alert updated) {
        Alert existing = getAlertsById(Id);

        existing.setTitle(updated.getTitle());
        existing.setMessage(updated.getMessage());
        existing.setSeverity(updated.getSeverity());
        existing.setVisibilityScope(updated.getVisibilityScope());
        existing.setTeamId(updated.getTeamId());
        existing.setUserId(updated.getUserId());
        existing.setStartTime(updated.getStartTime());
        existing.setExpiryTime(updated.getExpiryTime());
        existing.setReminderFrequency(updated.getReminderFrequency());
        existing.setActive(updated.isActive());

        return alertRepository.save(existing);
    }


    public void archiveAlert(Long Id) {
        Alert alert=alertRepository.getAlertById(Id);
        alert.setActive(false);
        alertRepository.save(alert);
    }

    // Filter alerts by severity
    public List<Alert> getAlertsBySeverity(Severity severity) {
        return alertRepository.findBySeverity(severity);
    }

    public List<Alert> getActiveAlerts() {
        LocalDateTime now =LocalDateTime.now();
        return alertRepository.findByStartTimeBeforeAndExpiryTimeAfter(now,now);

    }

    public List<Alert> getExpiredAlerts() {
        LocalDateTime now = LocalDateTime.now();
        return alertRepository.findByExpiryTimeBefore(now);
    }

    public List<Alert> getAlertsByVisibility(VisibilityScope scope) {
        return alertRepository.findByVisibilityScope(scope);
    }




}
