package com.ABN.NotificationSystem.repository;

import com.ABN.NotificationSystem.Model.Team;
import org.apache.juli.logging.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
