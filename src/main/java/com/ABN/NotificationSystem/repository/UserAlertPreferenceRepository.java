package com.ABN.NotificationSystem.repository;

import com.ABN.NotificationSystem.Model.UserAlertPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAlertPreferenceRepository extends JpaRepository<UserAlertPreference,Long> {
    Optional<UserAlertPreference> findByUserIdAndAlertId(Long id, Long alertId);
}
