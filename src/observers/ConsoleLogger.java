package observers;

import enums.ProcessingEvent;
import interfaces.ProcessingObserver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ConsoleLogger implements ProcessingObserver {
    private final String loggerName;

    public ConsoleLogger(String loggerName) {
        this.loggerName = loggerName;
    }

    @Override
    public void update(ProcessingEvent event, Map<String, Object> eventData) {
        String timestamp = getCurrentTimestamp();
        System.out.printf("[%s] [%s] Event: %s%n",
                timestamp, loggerName, event);

        if (!eventData.isEmpty()) {
            eventData.forEach((key, value) ->
                    System.out.printf("    %s: %s%n", key, value));
        }
    }

    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("HH:mm:ss")
        );
    }
}

