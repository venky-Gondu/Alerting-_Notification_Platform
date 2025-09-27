package com.ABN.NotificationSystem.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

// model/UserAlertPreference.java
@Entity
@Table(name="user_alert_preference")
public class UserAlertPreference {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private Long alertId;
    private boolean read = false;
    private LocalDate snoozedUntil; // snooze expires end of day

    public UserAlertPreference(Long id, Long alertId) {
        this.id=id;
        this.alertId=alertId;
    }

    public Long getAlertId() {
        return alertId;
    }

    public void setAlertId(Long alertId) {
        this.alertId = alertId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public LocalDate getSnoozedUntil() {
        return snoozedUntil;
    }

    public void setSnoozedUntil(LocalDate snoozedUntil) {
        this.snoozedUntil = snoozedUntil;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
