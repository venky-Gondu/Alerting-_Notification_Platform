package com.ABN.NotificationSystem.repository;

import com.ABN.NotificationSystem.Enum.Severity;
import com.ABN.NotificationSystem.Enum.VisibilityScope;
import com.ABN.NotificationSystem.Model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    // Active alerts between start and expiry
    List<Alert> findByStartTimeBeforeAndExpiryTimeAfter(LocalDateTime start, LocalDateTime expiry);

    // Fetch alert by ID
    Alert getAlertById(Long id);

    // Expired alerts
    List<Alert> findByExpiryTimeBefore(LocalDateTime now);

    // Filter by visibility scope
    List<Alert> findByVisibilityScope(VisibilityScope scope);

    // Filter by severity
    List<Alert> findBySeverity(Severity severity);

    // Count by severity (for analytics)
    long countBySeverity(Severity severity);
}
