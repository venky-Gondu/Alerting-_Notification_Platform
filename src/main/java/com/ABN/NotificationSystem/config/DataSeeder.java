package com.ABN.NotificationSystem.config;

import com.ABN.NotificationSystem.Enum.Severity;
import com.ABN.NotificationSystem.Enum.VisibilityScope;
import com.ABN.NotificationSystem.Model.Alert;
import com.ABN.NotificationSystem.Model.Team;
import com.ABN.NotificationSystem.Model.User;
import com.ABN.NotificationSystem.repository.AlertRepository;
import com.ABN.NotificationSystem.repository.TeamRepository;
import com.ABN.NotificationSystem.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Seeds database with test data for development
 * Runs automatically on application startup
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private AlertRepository alertRepository;

    @Override
    public void run(String... args) throws Exception {
        // Only seed if database is empty
        if (userRepository.count() > 0) {
            log.info("Database already contains data. Skipping seed.");
            return;
        }

        log.info("Seeding database with test data...");

        // Create Teams
        Team engineering = new Team("Engineering");
        Team marketing = new Team("Marketing");
        Team sales = new Team("Sales");

        teamRepository.save(engineering);
        teamRepository.save(marketing);
        teamRepository.save(sales);

        log.info("Created {} teams", teamRepository.count());

        // Create Users
        User john = new User();
        john.setName("John Doe");
        john.setTeamId(engineering.getId());
        userRepository.save(john);

        User jane = new User();
        jane.setName("Jane Smith");
        jane.setTeamId(engineering.getId());
        userRepository.save(jane);

        User bob = new User();
        bob.setName("Bob Wilson");
        bob.setTeamId(marketing.getId());
        userRepository.save(bob);

        User alice = new User();
        alice.setName("Alice Brown");
        alice.setTeamId(marketing.getId());
        userRepository.save(alice);

        User charlie = new User();
        charlie.setName("Charlie Davis");
        charlie.setTeamId(sales.getId());
        userRepository.save(charlie);

        log.info("Created {} users", userRepository.count());

        // Create Sample Alerts

        // 1. Organization-wide Critical Alert
        Alert orgAlert = new Alert();
        orgAlert.setTitle("System Maintenance Scheduled");
        orgAlert.setMessage("Critical: System maintenance will occur tonight at 10 PM. Please save your work.");
        orgAlert.setSeverity(Severity.CRITICAL);
        orgAlert.setVisibilityScope(VisibilityScope.ORG);
        orgAlert.setStartTime(LocalDateTime.now().minusHours(1));
        orgAlert.setExpiryTime(LocalDateTime.now().plusDays(1));
        orgAlert.setReminderFrequency(120);
        orgAlert.setActive(true);
        alertRepository.save(orgAlert);

        // 2. Team-specific Warning (Engineering)
        Alert teamAlert = new Alert();
        teamAlert.setTitle("Code Freeze in Effect");
        teamAlert.setMessage("Warning: Code freeze is now in effect for the release. No commits to main branch.");
        teamAlert.setSeverity(Severity.WARNING);
        teamAlert.setVisibilityScope(VisibilityScope.TEAM);
        teamAlert.setTeamId(engineering.getId());
        teamAlert.setStartTime(LocalDateTime.now().minusHours(2));
        teamAlert.setExpiryTime(LocalDateTime.now().plusDays(2));
        teamAlert.setReminderFrequency(120);
        teamAlert.setActive(true);
        alertRepository.save(teamAlert);

        // 3. User-specific Info Alert
        Alert userAlert = new Alert();
        userAlert.setTitle("Training Session Reminder");
        userAlert.setMessage("Info: Your scheduled training session is tomorrow at 2 PM.");
        userAlert.setSeverity(Severity.INFO);
        userAlert.setVisibilityScope(VisibilityScope.USER);
        userAlert.setUserId(john.getId());
        userAlert.setStartTime(LocalDateTime.now().minusMinutes(30));
        userAlert.setExpiryTime(LocalDateTime.now().plusHours(24));
        userAlert.setReminderFrequency(120);
        userAlert.setActive(true);
        alertRepository.save(userAlert);

        // 4. Marketing Team Alert
        Alert marketingAlert = new Alert();
        marketingAlert.setTitle("Campaign Launch Today");
        marketingAlert.setMessage("Warning: Marketing campaign goes live in 2 hours. Final checks required.");
        marketingAlert.setSeverity(Severity.WARNING);
        marketingAlert.setVisibilityScope(VisibilityScope.TEAM);
        marketingAlert.setTeamId(marketing.getId());
        marketingAlert.setStartTime(LocalDateTime.now().minusMinutes(15));
        marketingAlert.setExpiryTime(LocalDateTime.now().plusHours(6));
        marketingAlert.setReminderFrequency(120);
        marketingAlert.setActive(true);
        alertRepository.save(marketingAlert);

        // 5. Expired Alert (for testing)
        Alert expiredAlert = new Alert();
        expiredAlert.setTitle("Old Announcement");
        expiredAlert.setMessage("This is an expired alert that should not appear.");
        expiredAlert.setSeverity(Severity.INFO);
        expiredAlert.setVisibilityScope(VisibilityScope.ORG);
        expiredAlert.setStartTime(LocalDateTime.now().minusDays(3));
        expiredAlert.setExpiryTime(LocalDateTime.now().minusDays(1));
        expiredAlert.setReminderFrequency(120);
        expiredAlert.setActive(false);
        alertRepository.save(expiredAlert);

        log.info("Created {} alerts", alertRepository.count());
        log.info("Database seeding completed successfully!");

        // Print summary
        log.info("\n=== SEED DATA SUMMARY ===");
        log.info("Teams: {}", teamRepository.count());
        log.info("Users: {}", userRepository.count());
        log.info("Alerts: {}", alertRepository.count());
        log.info("========================\n");
    }
}