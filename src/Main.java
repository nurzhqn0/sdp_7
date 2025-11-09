import enums.ProcessingEvent;
import observers.AuditTrail;
import observers.ConsoleLogger;
import observers.StatisticsTracker;
import services.NotificationService;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== models.Document Processing System with Observer Pattern ===\n");

        // create subject processor
        DocumentProcessor processor = new DocumentProcessor();

        // create observer
        ConsoleLogger consoleLogger = new ConsoleLogger("MainLogger");
        StatisticsTracker statistics = new StatisticsTracker();
        NotificationService notifications = new NotificationService(
                ProcessingEvent.PROCESSING_FAILED,
                ProcessingEvent.WATERMARK_APPLIED
        );
        AuditTrail auditTrail = new AuditTrail();

        // attach observers to processor
        System.out.println("--- Attaching Observers ---");
        processor.attach(consoleLogger);
        processor.attach(statistics);
        processor.attach(notifications);
        processor.attach(auditTrail);

        // processing 1 document
        System.out.println("\n--- Processing models.Document 1 ---");
        Map<String, String> metadata1 = new HashMap<>();
        metadata1.put("author", "John Doe");
        metadata1.put("department", "Engineering");
        metadata1.put("classification", "Confidential");

        processor.processDocument("contract.pdf", "PDF", metadata1, "CONFIDENTIAL");

        // processing 2 document
        System.out.println("\n--- Processing models.Document 2 ---");
        Map<String, String> metadata2 = new HashMap<>();
        metadata2.put("author", "Jane Smith");
        metadata2.put("department", "Marketing");

        processor.processDocument("proposal.docx", "DOCX", metadata2, "DRAFT");

        // detaching console observer
        System.out.println("\n--- Detaching Console Logger ---");
        processor.detach(consoleLogger);

        // processing 3 document
        System.out.println("\n--- Processing models.Document 3 ---");
        processor.processDocument("report.pdf", "PDF",
                Map.of("author", "System"), null);

        // display all stats
        statistics.displayStatistics();
        auditTrail.printAuditLog();
    }
}