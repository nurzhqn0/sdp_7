import enums.ProcessingEvent;
import interfaces.ProcessingObserver;
import interfaces.ProcessingSubject;
import models.Document;

import java.util.*;

public class DocumentProcessor implements ProcessingSubject {
    private final List<ProcessingObserver> observers;

    public DocumentProcessor() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void attach(ProcessingObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("Observer attached: " + observer.getClass().getSimpleName());
        }
    }

    @Override
    public void detach(ProcessingObserver observer) {
        if (observers.remove(observer)) {
            System.out.println("Observer detached: " + observer.getClass().getSimpleName());
        }
    }

    @Override
    public void notifyObservers(ProcessingEvent event, Map<String, Object> eventData) {
        for (ProcessingObserver observer : observers) {
            observer.update(event, eventData);
        }
    }

    public void processDocument(String fileName, String format,
                                Map<String, String> metadata, String watermark) {
        try {
            notifyEvent(ProcessingEvent.PROCESSING_STARTED,
                    "fileName", fileName, "format", format);

            Document document = loadDocument(fileName, format);
            notifyEvent(ProcessingEvent.DOCUMENT_LOADED,
                    "document", document.toString());

            if (metadata != null && !metadata.isEmpty()) {
                addMetadata(document, metadata);
                notifyEvent(ProcessingEvent.METADATA_ADDED,
                        "count", metadata.size(), "fields", metadata.keySet());
            }

            if (watermark != null && !watermark.isEmpty()) {
                applyWatermark(document, watermark);
                notifyEvent(ProcessingEvent.WATERMARK_APPLIED,
                        "watermark", watermark);
            }

            validateDocument(document);
            notifyEvent(ProcessingEvent.VALIDATION_COMPLETED,
                    "status", "valid");

            notifyEvent(ProcessingEvent.PROCESSING_COMPLETED,
                    "document", document.toString(), "success", true);

        } catch (Exception e) {
            notifyEvent(ProcessingEvent.PROCESSING_FAILED,
                    "error", e.getMessage(), "fileName", fileName);
        }
    }

    private Document loadDocument(String fileName, String format) {
        Document document = new Document(fileName, format);
        document.setContent("Content of " + fileName);
        return document;
    }

    private void addMetadata(Document document, Map<String, String> metadata) {
        metadata.forEach(document::addMetadata);
    }

    private void applyWatermark(Document document, String watermark) {
        document.setWatermark(watermark);
    }

    private void validateDocument(Document document) {
        if (document.getFileName() == null || document.getFileName().isEmpty()) {
            throw new IllegalStateException("models.Document has no file name");
        }
    }

    private void notifyEvent(ProcessingEvent event, Object... keyValuePairs) {
        Map<String, Object> data = new HashMap<>();
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            data.put((String) keyValuePairs[i], keyValuePairs[i + 1]);
        }
        notifyObservers(event, data);
    }
}