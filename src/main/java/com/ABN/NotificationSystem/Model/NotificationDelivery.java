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
}

