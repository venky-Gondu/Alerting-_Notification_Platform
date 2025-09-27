package com.ABN.NotificationSystem.utility;

import com.ABN.NotificationSystem.Model.Alert;
import com.ABN.NotificationSystem.Model.UserAlertPreference;

public class UserAlertWithPreference {
    private final Alert alert;
    private final UserAlertPreference preference;

    public UserAlertWithPreference(Alert alert, UserAlertPreference preference) {
        this.alert = alert;
        this.preference = preference;
    }

    public Alert getAlert() { return alert; }
    public UserAlertPreference getPreference() { return preference; }
}

