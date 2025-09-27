package com.ABN.NotificationSystem.DTO;

import com.ABN.NotificationSystem.Enum.Severity;
import com.ABN.NotificationSystem.Enum.VisibilityScope;

import java.time.LocalDateTime;

public class AlertResponse {
    private Long id;
    private String title;
    private String message;
    private Severity severity;
    private VisibilityScope visibilityScope;
    private Long teamId;
    private Long userId;
    private LocalDateTime startTime;
    private LocalDateTime expiryTime;
    private int reminderFrequency;
    private boolean active;

    // getters and setters


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getReminderFrequency() {
        return reminderFrequency;
    }

    public void setReminderFrequency(int reminderFrequency) {
        this.reminderFrequency = reminderFrequency;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public VisibilityScope getVisibilityScope() {
        return visibilityScope;
    }

    public void setVisibilityScope(VisibilityScope visibilityScope) {
        this.visibilityScope = visibilityScope;
    }
}