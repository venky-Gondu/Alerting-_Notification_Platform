package com.ABN.NotificationSystem.Model;

import com.ABN.NotificationSystem.Enum.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;

// model/NotificationDelivery.java
@Entity
@Table(name="notification_delivery")
public class NotificationDelivery {
    @Id
    @GeneratedValue
    private Long id;
    private Long alertId;
    private Long userId;

    private LocalDateTime deliveredAt;
    @Enumerated(EnumType.STRING)
    private Status status; // SENT, READ, SNOOZED

    public Long getAlertId() {
        return alertId;
    }

    public void setAlertId(Long alertId) {
        this.alertId = alertId;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


}

