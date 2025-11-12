package observers;

import enums.ProcessingEvent;
import interfaces.ProcessingObserver;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class NotificationService implements ProcessingObserver {
    private final Set<ProcessingEvent> criticalEvents;
    private final List<String> notifications;

    public NotificationService(ProcessingEvent... events) {
        this.criticalEvents = new HashSet<>(Arrays.asList(events));
        this.notifications = new ArrayList<>();
    }

    @Override
    public void update(ProcessingEvent event, Map<String, Object> eventData) {
        if (criticalEvents.contains(event)) {
            String notification = createNotification(event, eventData);
            notifications.add(notification);
            displayNotification(notification);
        }
    }

    private String createNotification(ProcessingEvent event, Map<String, Object> data) {
        String timestamp = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );
        return String.format("[%s] ALERT: %s - %s", timestamp, event, data);
    }

    private void displayNotification(String notification) {
        System.out.println("🔔 " + notification);
    }

    public List<String> getAllNotifications() {
        return new ArrayList<>(notifications);
    }
}
