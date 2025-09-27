package com.ABN.NotificationSystem.DTO;

import com.ABN.NotificationSystem.Enum.Severity;
import com.ABN.NotificationSystem.Enum.VisibilityScope;

import java.time.LocalDateTime;

public class UserAlertResponse {
    private Long alertId;
    private String title;
    private String message;
    private Severity severity;
    private VisibilityScope visibilityScope;
    private boolean read;
    private boolean snoozed;
    private LocalDateTime expiryTime;

    public Long getAlertId() {
        return alertId;
    }

    public void setAlertId(Long alertId) {
        this.alertId = alertId;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public boolean isSnoozed() {
        return snoozed;
    }

    public void setSnoozed(boolean snoozed) {
        this.snoozed = snoozed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public VisibilityScope getVisibilityScope() {
        return visibilityScope;
    }

    public void setVisibilityScope(VisibilityScope visibilityScope) {
        this.visibilityScope = visibilityScope;
    }
}
