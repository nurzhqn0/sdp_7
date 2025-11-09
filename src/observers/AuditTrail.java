package observers;

import enums.ProcessingEvent;
import interfaces.ProcessingObserver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AuditTrail implements ProcessingObserver {
    private List<AuditEntry> auditLog;

    public AuditTrail() {
        this.auditLog = new ArrayList<>();
    }

    public void setAuditLog(List<AuditEntry> auditLog) {
        this.auditLog = auditLog;
    }

    @Override
    public void update(ProcessingEvent event, Map<String, Object> eventData) {
        AuditEntry entry = new AuditEntry(event, eventData);
        auditLog.add(entry);
    }

    public void printAuditLog() {
        System.out.println("\n========== AUDIT TRAIL ==========");
        for (int i = 0; i < auditLog.size(); i++) {
            System.out.println((i + 1) + ". " + auditLog.get(i));
        }
        System.out.println("=================================\n");
    }

    public List<AuditEntry> getAuditLog() {
        return new ArrayList<>(auditLog);
    }

    public static class AuditEntry {
        private final String timestamp;
        private final ProcessingEvent event;
        private final Map<String, Object> data;

        public AuditEntry(ProcessingEvent event, Map<String, Object> data) {
            this.timestamp = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            );
            this.event = event;
            this.data = new HashMap<>(data);
        }

        @Override
        public String toString() {
            return String.format("[%s] %s - %s", timestamp, event, data);
        }
    }
}
