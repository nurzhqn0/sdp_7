package observers;

import enums.ProcessingEvent;
import interfaces.ProcessingObserver;

import java.util.HashMap;
import java.util.Map;

public class StatisticsTracker implements ProcessingObserver {
    private final Map<ProcessingEvent, Integer> eventCounts;
    private int totalEventsProcessed;
    private int successfulOperations;
    private int failedOperations;

    public StatisticsTracker() {
        this.eventCounts = new HashMap<>();
        this.totalEventsProcessed = 0;
        this.successfulOperations = 0;
        this.failedOperations = 0;
    }

    @Override
    public void update(ProcessingEvent event, Map<String, Object> eventData) {
        eventCounts.merge(event, 1, Integer::sum);
        totalEventsProcessed++;

        if (event == ProcessingEvent.PROCESSING_COMPLETED) {
            successfulOperations++;
        } else if (event == ProcessingEvent.PROCESSING_FAILED) {
            failedOperations++;
        }
    }

    public void displayStatistics() {
        System.out.println("\n========== PROCESSING STATISTICS ==========");
        System.out.println("Total Events: " + totalEventsProcessed);
        System.out.println("Successful Operations: " + successfulOperations);
        System.out.println("Failed Operations: " + failedOperations);
        System.out.println("\nEvent Breakdown:");

        eventCounts.entrySet().stream()
                .sorted(Map.Entry.<ProcessingEvent, Integer>comparingByValue().reversed())
                .forEach(entry ->
                        System.out.printf("  %s: %d%n", entry.getKey(), entry.getValue()));

        System.out.println("==========================================\n");
    }

    public Map<ProcessingEvent, Integer> getEventCounts() {
        return new HashMap<>(eventCounts);
    }
}