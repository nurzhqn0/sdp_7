package interfaces;

import enums.ProcessingEvent;

import java.util.Map;

public interface ProcessingObserver {
    void update(ProcessingEvent event, Map<String, Object> eventData);
}