// NotificationChannel.java - Strategy Interface
package com.ABN.NotificationSystem.Service.notification;

import com.ABN.NotificationSystem.Model.Alert;
import com.ABN.NotificationSystem.Model.User;


public interface NotificationChannel {
    void send(User user, Alert alert);
    String getChannelType();
}


