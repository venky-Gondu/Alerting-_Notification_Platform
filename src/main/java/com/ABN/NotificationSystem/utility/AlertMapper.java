package com.ABN.NotificationSystem.utility;


import com.ABN.NotificationSystem.DTO.AlertRequest;
import com.ABN.NotificationSystem.DTO.AlertResponse;
import com.ABN.NotificationSystem.DTO.UserAlertResponse;
import com.ABN.NotificationSystem.Model.Alert;
import com.ABN.NotificationSystem.Model.UserAlertPreference;

public class AlertMapper {

    // Convert AlertRequest -> Alert (entity)
    public static Alert toEntity(AlertRequest request) {
        Alert alert = new Alert();
        alert.setTitle(request.getTitle());
        alert.setMessage(request.getMessage());
        alert.setSeverity(request.getSeverity());
        alert.setVisibilityScope(request.getVisibilityScope());
        alert.setTeamId(request.getTeamId());
        alert.setUserId(request.getUserId());
        alert.setStartTime(request.getStartTime());
        alert.setExpiryTime(request.getExpiryTime());
        alert.setReminderFrequency(request.getReminderFrequency());
        alert.setActive(true);
        return alert;
    }

    // Convert Alert (entity) -> AlertResponse
    public static AlertResponse toResponse(Alert alert) {
        AlertResponse dto = new AlertResponse();
        dto.setId(alert.getId());
        dto.setTitle(alert.getTitle());
        dto.setMessage(alert.getMessage());
        dto.setSeverity(alert.getSeverity());
        dto.setVisibilityScope(alert.getVisibilityScope());
        dto.setTeamId(alert.getTeamId());
        dto.setUserId(alert.getUserId());
        dto.setStartTime(alert.getStartTime());
        dto.setExpiryTime(alert.getExpiryTime());
        dto.setReminderFrequency(alert.getReminderFrequency());
        dto.setActive(alert.isActive());
        return dto;
    }

    // Convert Alert + UserAlertPreference -> UserAlertResponse
    public static UserAlertResponse toUserResponse(Alert alert, UserAlertPreference pref) {
        UserAlertResponse dto = new UserAlertResponse();
        dto.setAlertId(alert.getId());
        dto.setTitle(alert.getTitle());
        dto.setMessage(alert.getMessage());
        dto.setSeverity(alert.getSeverity());
        dto.setVisibilityScope(alert.getVisibilityScope());
        dto.setExpiryTime(alert.getExpiryTime());
        dto.setRead(pref != null && pref.isRead());
        dto.setSnoozed(pref != null && pref.getSnoozedUntil() != null
                && pref.getSnoozedUntil().equals(java.time.LocalDate.now()));
        return dto;
    }
}
