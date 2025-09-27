package com.ABN.NotificationSystem.Service;

import com.ABN.NotificationSystem.DTO.AlertResponse;
import com.ABN.NotificationSystem.Enum.VisibilityScope;
import com.ABN.NotificationSystem.Model.Alert;
import com.ABN.NotificationSystem.Model.User;
import com.ABN.NotificationSystem.Model.UserAlertPreference;
import com.ABN.NotificationSystem.repository.AlertRepository;
import com.ABN.NotificationSystem.repository.UserAlertPreferenceRepository;
import com.ABN.NotificationSystem.repository.UserRepository;
import com.ABN.NotificationSystem.utility.UserAlertWithPreference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserAlertService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAlertPreferenceRepository userAlertPreferenceRepository;
    @Autowired
    private AlertRepository alertRepository;

    public List<UserAlertWithPreference> getUserAlerts(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDateTime now = LocalDateTime.now();
        List<Alert> activeAlerts = alertRepository.findByStartTimeBeforeAndExpiryTimeAfter(now, now);

        return activeAlerts.stream()
                .filter(alert ->
                        alert.getVisibilityScope() == VisibilityScope.ORG ||
                                (alert.getVisibilityScope() == VisibilityScope.TEAM &&
                                        Objects.equals(user.getTeamId(), alert.getTeamId())) ||
                                (alert.getVisibilityScope() == VisibilityScope.USER &&
                                        Objects.equals(user.getId(), alert.getUserId()))
                )
                .map(alert -> {
                    UserAlertPreference pref = userAlertPreferenceRepository.findByUserIdAndAlertId(userId, alert.getId())
                            .orElse(null);
                    return new UserAlertWithPreference(alert, pref);
                })
                .collect(Collectors.toList());
    }

    public void markRead(Long Id,Long alertId){
        UserAlertPreference pref = userAlertPreferenceRepository.findByUserIdAndAlertId(Id, alertId)
                .orElseGet(() -> new UserAlertPreference(Id, alertId));
        pref.setRead(true);
        userAlertPreferenceRepository.save(pref);

    }

    public void snooze(Long Id,Long alertId){
        UserAlertPreference pref = userAlertPreferenceRepository.findByUserIdAndAlertId(Id, alertId)
                .orElseGet(() -> new UserAlertPreference(Id, alertId));
        pref.setSnoozedUntil(LocalDate.now());
    }
}
