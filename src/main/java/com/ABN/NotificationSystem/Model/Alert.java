package com.ABN.NotificationSystem.Model;

import com.ABN.NotificationSystem.Enum.Severity;
import com.ABN.NotificationSystem.Enum.VisibilityScope;
import jakarta.persistence.*;

import java.time.LocalDateTime;

// model/Alert.java
@Entity
@Table(name="alert")
public class Alert {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String message;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Severity severity; // INFO, WARNING, CRITICAL

    @Enumerated(EnumType.STRING)
    private VisibilityScope visibilityScope; // ORG, TEAM, USER

    private Long teamId;
    private  Long userId;
    private LocalDateTime startTime;

    private LocalDateTime expiryTime;
    private int reminderFrequency = 120; // minutes
    private boolean active = true;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }



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

