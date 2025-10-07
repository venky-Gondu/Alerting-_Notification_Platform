package com.ABN.NotificationSystem.Model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="user_alert_preference")
public class UserAlertPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long alertId;

    private boolean read = false;

    private LocalDate snoozedUntil; // snooze expires end of day

    // Default constructor (required by JPA)
    public UserAlertPreference() {
    }

    // Constructor for creating new preference
    public UserAlertPreference(Long userId, Long alertId) {
        this.userId = userId;
        this.alertId = alertId;
        this.read = false;
        this.snoozedUntil = null;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAlertId() {
        return alertId;
    }

    public void setAlertId(Long alertId) {
        this.alertId = alertId;
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

    /**
     * Check if alert is currently snoozed
     */
    public boolean isSnoozedToday() {
        if (snoozedUntil == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        return !snoozedUntil.isBefore(today);
    }
}