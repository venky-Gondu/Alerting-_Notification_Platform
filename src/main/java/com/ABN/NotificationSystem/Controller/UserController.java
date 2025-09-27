package com.ABN.NotificationSystem.Controller;

import com.ABN.NotificationSystem.DTO.AlertResponse;
import com.ABN.NotificationSystem.DTO.UserAlertResponse;
import com.ABN.NotificationSystem.Model.Alert;
import com.ABN.NotificationSystem.Service.UserAlertService;
import com.ABN.NotificationSystem.utility.AlertMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserAlertService userAlertService;
    @GetMapping("/{id}/alerts")
    public List<UserAlertResponse> getUserAlerts(@PathVariable Long id) {
        return userAlertService.getUserAlerts(id).stream()
                .map(pair -> AlertMapper.toUserResponse(pair.getAlert(), pair.getPreference()))
                .collect(Collectors.toList());
    }
    @PostMapping("/{id}/alerts/{alertId}/read")
    public void markRead(@PathVariable Long id, @PathVariable Long alertId){
        userAlertService.markRead(id,alertId);

    }
    @PostMapping("/{id}/alerts/{alertId}/snooze")
    public void snoozeMessage(@PathVariable Long id,@PathVariable Long alertId){
        userAlertService.snooze(id,alertId);
    }

}
