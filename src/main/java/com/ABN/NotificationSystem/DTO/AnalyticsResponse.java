package com.ABN.NotificationSystem.DTO;

import java.util.*;
public class AnalyticsResponse {
    private long totalAlerts;
    private long totalDelivered;
    private long totalRead;
    private long totalSnoozed;

    // severity breakdown (INFO, WARNING, CRITICAL counts)
    private Map<String, Long> severityBreakdown;


    public Map<String, Long> getSeverityBreakdown() {
        return severityBreakdown;
    }

    public void setSeverityBreakdown(Map<String, Long> severityBreakdown) {
        this.severityBreakdown = severityBreakdown;
    }

    public long getTotalAlerts() {
        return totalAlerts;
    }

    public void setTotalAlerts(long totalAlerts) {
        this.totalAlerts = totalAlerts;
    }

    public long getTotalDelivered() {
        return totalDelivered;
    }

    public void setTotalDelivered(long totalDelivered) {
        this.totalDelivered = totalDelivered;
    }

    public long getTotalRead() {
        return totalRead;
    }

    public void setTotalRead(long totalRead) {
        this.totalRead = totalRead;
    }

    public long getTotalSnoozed() {
        return totalSnoozed;
    }

    public void setTotalSnoozed(long totalSnoozed) {
        this.totalSnoozed = totalSnoozed;
    }
}