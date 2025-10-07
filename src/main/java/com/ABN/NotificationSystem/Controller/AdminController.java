package com.ABN.NotificationSystem.Controller;

import com.ABN.NotificationSystem.DTO.AlertRequest;
import com.ABN.NotificationSystem.DTO.AlertResponse;
import com.ABN.NotificationSystem.Enum.Severity;
import com.ABN.NotificationSystem.Enum.VisibilityScope;
import com.ABN.NotificationSystem.Model.Alert;
import com.ABN.NotificationSystem.Service.AlertService;
import com.ABN.NotificationSystem.utility.AlertMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ABN.NotificationSystem.Service.ReminderService;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AlertService alertService;

    @Autowired
    private ReminderService reminderService;

    @PostMapping("/alerts")
    public AlertResponse createAlert(@RequestBody AlertRequest request) {
        Alert alert = AlertMapper.toEntity(request);
        Alert saved = alertService.createAlert(alert);
        return AlertMapper.toResponse(saved);
    }
    @GetMapping("/alerts")
    public List<AlertResponse> getAllAlerts() {
        return alertService.getAllAlerts().stream()
                .map(AlertMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/alert/{id}")
    public Alert getAlertById(@PathVariable Long Id){
        return alertService.getAlertsById(Id);

    }
    @PutMapping("/updateAlert/{id}")
    public AlertResponse updateAlert(@PathVariable Long id, @RequestBody AlertRequest request) {
        Alert updatedEntity = AlertMapper.toEntity(request);
        Alert updated = alertService.updateAlert(id, updatedEntity);
        return AlertMapper.toResponse(updated);
    }

    @DeleteMapping("/Delete/{id}")
    public void archiveAlert(@PathVariable Long Id){
        alertService.archiveAlert(Id);
    }

    @GetMapping("/severity/{severity}")
    public List<AlertResponse> getBySeverity(@PathVariable Severity severity) {
        return alertService.getAlertsBySeverity(severity).stream()
                .map(AlertMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/active")
    public List<Alert> getActiveAlerts() {
        return alertService.getActiveAlerts();
    }

    @GetMapping("/expired")
    public List<AlertResponse> getExpiredAlerts() {
        return alertService.getExpiredAlerts().stream()
                .map(AlertMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/visibility/{scope}")
    public List<AlertResponse> getByVisibility(@PathVariable VisibilityScope scope) {
        return alertService.getAlertsByVisibility(scope).stream()
                .map(AlertMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Manually trigger reminder process for all active alerts
     * Useful for testing without waiting for scheduled run
     */
    @PostMapping("/reminders/trigger")
    public ResponseEntity<String> triggerReminders() {
        try {
            reminderService.sendReminders();
            return ResponseEntity.ok("Reminder process triggered successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error triggering reminders: " + e.getMessage());
        }
    }

    /**
     * Manually trigger reminder for a specific alert
     * Useful for testing specific alert delivery
     */
    @PostMapping("/reminders/trigger/{alertId}")
    public ResponseEntity<String> triggerReminderForAlert(@PathVariable Long alertId) {
        try {
            reminderService.triggerManualReminder(alertId);
            return ResponseEntity.ok("Reminder triggered for alert: " + alertId);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error triggering reminder: " + e.getMessage());
        }
    }
}
