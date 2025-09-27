package com.ABN.NotificationSystem.repository;

import com.ABN.NotificationSystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
