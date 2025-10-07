# Notification & Alerting System

A Spring Boot-based alerting and notification platform with automated reminder functionality.

## 🎯 Features

- **Admin Features**
    - Create, update, and archive alerts
    - Configure visibility (Organization, Team, or User level)
    - Set severity levels (INFO, WARNING, CRITICAL)
    - Define start and expiry times
    - View analytics dashboard

- **User Features**
    - Receive alerts based on visibility rules
    - Mark alerts as read/unread
    - Snooze alerts for the day
    - View alert history

- **Reminder System**
    - Automated reminders every 2 hours for active alerts
    - Respects user snooze preferences
    - Stops when alert expires
    - Tracks all deliveries

## 🏗️ Architecture

### Design Patterns Used

1. **Strategy Pattern** - `NotificationChannel` interface for extensible delivery channels
2. **Repository Pattern** - Clean data access layer with JPA repositories
3. **DTO Pattern** - Separation between domain models and API contracts
4. **Mapper Pattern** - Clean conversion between entities and DTOs

### Project Structure

```
src/main/java/com/ABN/NotificationSystem/
├── Controller/
│   ├── AdminController.java          # Admin API endpoints
│   ├── UserController.java           # User API endpoints
│   └── AnalyticController.java       # Analytics endpoints
├── Service/
│   ├── AlertService.java             # Alert CRUD operations
│   ├── UserAlertService.java         # User-specific alert logic
│   ├── ReminderService.java          # Core reminder logic ⭐
│   ├── AnalyticsService.java         # Analytics aggregation
│   └── notification/
│       ├── NotificationChannel.java  # Strategy interface
│       ├── InAppNotificationChannel.java
│       ├── EmailNotificationChannel.java (future)
│       └── SMSNotificationChannel.java (future)
├── Model/
│   ├── Alert.java
│   ├── User.java
│   ├── Team.java
│   ├── UserAlertPreference.java
│   └── NotificationDelivery.java
├── Repository/
│   ├── AlertRepository.java
│   ├── UserRepository.java
│   ├── TeamRepository.java
│   ├── UserAlertPreferenceRepository.java
│   └── NotificationDeliveryRepository.java
├── DTO/
│   ├── AlertRequest.java
│   ├── AlertResponse.java
│   ├── UserAlertResponse.java
│   └── AnalyticsResponse.java
├── Enum/
│   ├── Severity.java                 # INFO, WARNING, CRITICAL
│   ├── VisibilityScope.java          # ORG, TEAM, USER
│   └── Status.java                   # SENT, READ, SNOOZED
├── Scheduler/
│   └── ReminderScheduler.java        # Scheduled task (every 2h)
└── config/
    ├── DataSeeder.java               # Test data loader
    └── CorsConfig.java               # CORS configuration
```

## 🚀 Setup Instructions

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+ (or any JPA-compatible database)

### Database Setup

1. **Create PostgreSQL database:**

```sql
CREATE DATABASE notification_system;
```

2. **Configure database connection** in `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/notification_system
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Server Configuration
server.port=8080

# Logging
logging.level.com.ABN.NotificationSystem=DEBUG
logging.level.org.springframework.scheduling=DEBUG
```

### Build and Run

1. **Clone the repository**

```bash
git clone <repository-url>
cd NotificationSystem
```

2. **Build the project**

```bash
mvn clean install
```

3. **Run the application**

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

On first run, the `DataSeeder` will automatically populate the database with:
- 3 Teams (Engineering, Marketing, Sales)
- 5 Users
- 5 Sample Alerts

## 📡 API Endpoints

### Admin Endpoints

#### Create Alert
```bash
POST /api/admin/alerts
Content-Type: application/json

{
  "title": "System Maintenance",
  "message": "Server maintenance scheduled for tonight",
  "severity": "CRITICAL",
  "visibilityScope": "ORG",
  "startTime": "2025-10-06T10:00:00",
  "expiryTime": "2025-10-07T10:00:00",
  "reminderFrequency": 120
}
```

#### Get All Alerts
```bash
GET /api/admin/alerts
```

#### Get Alert by ID
```bash
GET /api/admin/alert/{id}
```

#### Update Alert
```bash
PUT /api/admin/updateAlert/{id}
Content-Type: application/json

{
  "title": "Updated Title",
  "message": "Updated message",
  "severity": "WARNING",
  "visibilityScope": "TEAM",
  "teamId": 1,
  "startTime": "2025-10-06T10:00:00",
  "expiryTime": "2025-10-07T10:00:00",
  "reminderFrequency": 120
}
```

#### Archive Alert
```bash
DELETE /api/admin/Delete/{id}
```

#### Filter by Severity
```bash
GET /api/admin/severity/CRITICAL
```

#### Get Active Alerts
```bash
GET /api/admin/active
```

#### Get Expired Alerts
```bash
GET /api/admin/expired
```

#### Filter by Visibility
```bash
GET /api/admin/visibility/TEAM
```

#### Trigger Reminders Manually (Testing)
```bash
POST /api/admin/reminders/trigger
```

#### Trigger Reminder for Specific Alert
```bash
POST /api/admin/reminders/trigger/{alertId}
```

### User Endpoints

#### Get User's Alerts
```bash
GET /api/user/{userId}/alerts
```

Response:
```json
[
  {
    "alertId": 1,
    "title": "System Maintenance Scheduled",
    "message": "Critical: System maintenance will occur tonight...",
    "severity": "CRITICAL",
    "visibilityScope": "ORG",
    "read": false,
    "snoozed": false,
    "expiryTime": "2025-10-07T10:00:00"
  }
]
```

#### Mark Alert as Read
```bash
POST /api/user/{userId}/alerts/{alertId}/read
```

#### Snooze Alert
```bash
POST /api/user/{userId}/alerts/{alertId}/snooze
```

### Analytics Endpoints

#### Get System Analytics
```bash
GET /api/admin/analytics
```

Response:
```json
{
  "totalAlerts": 5,
  "totalDelivered": 120,
  "totalRead": 45,
  "totalSnoozed": 30,
  "severityBreakdown": {
    "INFO": 2,
    "WARNING": 2,
    "CRITICAL": 1
  }
}
```

## 🧪 Testing the Reminder System

### Method 1: Wait for Scheduled Trigger

The reminder system runs automatically every 2 hours. Watch the logs for:

```
INFO  ReminderService - Starting reminder process at 2025-10-06T12:00:00
INFO  ReminderService - Found 4 active alerts
DEBUG ReminderService - Processing reminders for alert: 1 - System Maintenance Scheduled
DEBUG ReminderService - Found 5 eligible users for alert 1
INFO  InAppNotificationChannel - 📱 IN-APP NOTIFICATION
INFO  InAppNotificationChannel -    To: John Doe (ID: 1)
INFO  InAppNotificationChannel -    Alert: System Maintenance Scheduled
INFO  InAppNotificationChannel -    Severity: CRITICAL
```

### Method 2: Manual Trigger (Recommended for Testing)

Trigger reminders immediately without waiting:

```bash
# Trigger all active alerts
curl -X POST http://localhost:8080/api/admin/reminders/trigger

# Trigger specific alert
curl -X POST http://localhost:8080/api/admin/reminders/trigger/1
```

### Test Scenarios

#### Scenario 1: Organization-wide Alert
```bash
# Create org alert
curl -X POST http://localhost:8080/api/admin/alerts \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Emergency Maintenance",
    "message": "Emergency maintenance in progress",
    "severity": "CRITICAL",
    "visibilityScope": "ORG",
    "startTime": "2025-10-06T10:00:00",
    "expiryTime": "2025-10-06T22:00:00"
  }'

# Trigger reminder
curl -X POST http://localhost:8080/api/admin/reminders/trigger

# Check logs - all users should receive notification
```

#### Scenario 2: Team-specific Alert
```bash
# Create team alert (Engineering team = ID 1)
curl -X POST http://localhost:8080/api/admin/alerts \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Code Review Required",
    "message": "Please review pending PRs",
    "severity": "WARNING",
    "visibilityScope": "TEAM",
    "teamId": 1,
    "startTime": "2025-10-06T10:00:00",
    "expiryTime": "2025-10-06T18:00:00"
  }'

# Trigger reminder
curl -X POST http://localhost:8080/api/admin/reminders/trigger

# Check logs - only Engineering team members receive notification
```

#### Scenario 3: User Snooze
```bash
# User 1 gets alerts
curl http://localhost:8080/api/user/1/alerts

# User 1 snoozes alert 1
curl -X POST http://localhost:8080/api/user/1/alerts/1/snooze

# Trigger reminder again
curl -X POST http://localhost:8080/api/admin/reminders/trigger

# Check logs - User 1 should NOT receive reminder for alert 1
```

#### Scenario 4: Mark as Read
```bash
# Mark alert as read
curl -X POST http://localhost:8080/api/user/1/alerts/1/read

# Get alerts (read status should be true)
curl http://localhost:8080/api/user/1/alerts
```

## 🔍 How the Reminder System Works

### Core Logic Flow

1. **Scheduler Triggers** (every 2 hours)
   ```
   ReminderScheduler → ReminderService.sendReminders()
   ```

2. **Find Active Alerts**
   ```sql
   SELECT * FROM alert 
   WHERE start_time <= NOW() 
   AND expiry_time >= NOW() 
   AND active = true
   ```

3. **For Each Alert:**
    - Determine eligible users based on `visibilityScope`:
        - `ORG`: All users
        - `TEAM`: Users in specified team
        - `USER`: Specific user only

4. **For Each Eligible User:**
    - Check if user has snoozed alert for today
    - If not snoozed → Send notification
    - Log delivery in `notification_delivery` table

5. **Notification Delivery**
    - Uses `NotificationChannel` strategy
    - For MVP: Logs to console (In-App simulation)
    - Future: WebSocket, Email, SMS

### Snooze Logic

- When user snoozes: `snoozedUntil = TODAY's date`
- Reminder check: Skip if `snoozedUntil >= TODAY`
- Next day: `snoozedUntil < TODAY` → Reminders resume

### Database Tracking

Every reminder sent creates a record:
```java
NotificationDelivery {
  alertId: 1,
  userId: 5,
  deliveredAt: "2025-10-06T14:00:00",
  status: SENT
}
```

## 🎨 Extensibility

### Adding New Notification Channels

1. **Create new channel class:**

```java
@Component
public class SlackNotificationChannel implements NotificationChannel {
    
    @Override
    public void send(User user, Alert alert) {
        // Implement Slack API integration
    }
    
    @Override
    public String getChannelType() {
        return "SLACK";
    }
}
```

2. **Spring will auto-inject** - no changes needed to ReminderService!

### Adding New Visibility Scopes

1. Add to enum:
```java
public enum VisibilityScope {
    ORG, TEAM, USER, DEPARTMENT, LOCATION
}
```

2. Update `isUserEligible()` method in ReminderService

### Customizing Reminder Frequency

Currently defaults to 120 minutes. To make dynamic:

```java
// In ReminderScheduler
@Scheduled(fixedRateString = "${reminder.frequency.ms:7200000}")
public void triggerReminders() {
    reminderService.sendReminders();
}
```

## 📊 Monitoring & Logs

### Key Log Patterns

**Successful Reminder:**
```
INFO  ReminderService - Alert 1 - Sent: 5, Skipped: 2
```

**User Snoozed:**
```
DEBUG ReminderService - Skipped reminder for user 3 (already snoozed)
```

**Notification Sent:**
```
INFO  InAppNotificationChannel - 📱 IN-APP NOTIFICATION
```

## 🐛 Troubleshooting

### Issue: Reminders not triggering

**Solution:** Check scheduler is enabled:
```java
@EnableScheduling  // Make sure this is in NotificationSystemApplication.java
```

### Issue: No users receiving alerts

**Solution:**
1. Check alert is active: `GET /api/admin/alert/{id}`
2. Verify start_time < now < expiry_time
3. Check visibility scope matches user's team

### Issue: Database connection error

**Solution:** Verify PostgreSQL is running and credentials in `application.properties` are correct

## 🔒 Security Considerations (Future)

- Add authentication/authorization (Spring Security)
- Validate user permissions for alert operations
- Rate limit reminder triggers
- Encrypt sensitive alert messages
- Audit logging for admin actions

## 📈 Future Enhancements

- [ ] WebSocket for real-time notifications
- [ ] Email integration (SendGrid/AWS SES)
- [ ] SMS integration (Twilio)
- [ ] Push notifications (FCM)
- [ ] Alert templates
- [ ] Escalation policies
- [ ] Notification preferences per user
- [ ] Rich media in alerts (images, links)
- [ ] Alert acknowledgment tracking
- [ ] Batch notification sending


