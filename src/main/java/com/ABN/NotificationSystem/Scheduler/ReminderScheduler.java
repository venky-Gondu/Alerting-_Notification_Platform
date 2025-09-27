package com.ABN.NotificationSystem.Scheduler;

import com.ABN.NotificationSystem.Service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReminderScheduler {
    @Autowired
    private ReminderService reminderService;

    @Scheduled(fixedRate = 7200000) // 2 hours in ms
    public void triggerReminders() {
        reminderService.sendReminders();
    }
}

