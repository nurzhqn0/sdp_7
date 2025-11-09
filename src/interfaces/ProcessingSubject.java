package interfaces;

import enums.ProcessingEvent;

import java.util.Map;

public interface ProcessingSubject {
    void attach(ProcessingObserver observer);
    void detach(ProcessingObserver observer);
    void notifyObservers(ProcessingEvent event, Map<String, Object> eventData);
}