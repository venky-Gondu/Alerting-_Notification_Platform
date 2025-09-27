package com.ABN.NotificationSystem.repository;

import com.ABN.NotificationSystem.Model.NotificationDelivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationDeliveryRepository extends JpaRepository<NotificationDelivery,Long> {
    long countByStatus(String read);
}
