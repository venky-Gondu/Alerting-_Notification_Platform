package com.ABN.NotificationSystem.Controller;

import com.ABN.NotificationSystem.DTO.AnalyticsResponse;
import com.ABN.NotificationSystem.Service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AnalyticContoller {
    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/analytics")
    public AnalyticsResponse getAnalytics() {
        return analyticsService.getAnalytics();
    }


}
