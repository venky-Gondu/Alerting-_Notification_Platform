package com.ABN.NotificationSystem.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// model/Team.java
@Entity
@Table(name="team")
public class Team {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
