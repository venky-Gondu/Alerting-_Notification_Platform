package com.ABN.NotificationSystem.Service;

import com.ABN.NotificationSystem.DTO.AnalyticsResponse;
import com.ABN.NotificationSystem.Enum.Severity;
import com.ABN.NotificationSystem.repository.AlertRepository;
import com.ABN.NotificationSystem.repository.NotificationDeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    private final AlertRepository alertRepository;
    private final NotificationDeliveryRepository deliveryRepository;

    public AnalyticsService(AlertRepository alertRepository,
                            NotificationDeliveryRepository deliveryRepository) {
        this.alertRepository = alertRepository;
        this.deliveryRepository = deliveryRepository;
    }

    public AnalyticsResponse getAnalytics() {
        AnalyticsResponse dto = new AnalyticsResponse();

        long totalAlerts = alertRepository.count();
        long totalDelivered = deliveryRepository.count();
        long totalRead = deliveryRepository.countByStatus("READ");
        long totalSnoozed = deliveryRepository.countByStatus("SNOOZED");

        Map<String, Long> severityBreakdown = Arrays.stream(Severity.values())
                .collect(Collectors.toMap(
                        Severity::name,  // clearer than Enum::name
                        sev -> alertRepository.countBySeverity(sev)
                ));

        dto.setTotalAlerts(totalAlerts);
        dto.setTotalDelivered(totalDelivered);
        dto.setTotalRead(totalRead);
        dto.setTotalSnoozed(totalSnoozed);
        dto.setSeverityBreakdown(severityBreakdown);

        return dto;
    }
}

