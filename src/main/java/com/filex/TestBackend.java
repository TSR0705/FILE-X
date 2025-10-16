package com.filex;

import com.filex.service.DatabaseService;
import com.filex.service.MonitorService;
import com.filex.model.FileEvent;
import java.time.LocalDateTime;

/**
 * Test class to verify backend functionality.
 */
public class TestBackend {
    
    public static void main(String[] args) {
        System.out.println("Testing FileX Backend Components...");
        
        // Test DatabaseService
        testDatabaseService();
        
        // Test MonitorService
        testMonitorService();
        
        System.out.println("Backend tests completed!");
    }
    
    private static void testDatabaseService() {
        System.out.println("Testing DatabaseService...");
        
        // Use the singleton instance
        DatabaseService dbService = DatabaseService.getInstance();
        dbService.initialize();
        
        // Create a test file event
        FileEvent event = new FileEvent();
        event.setFileName("test_file.txt");
        event.setEventType("ENTRY_CREATE");
        event.setTimestamp(LocalDateTime.now());
        event.setSha256("abcdef1234567890");
        event.setSuspicious(false);
        
        // Save the event
        dbService.saveEvent(event);
        
        // Retrieve events
        var events = dbService.getAllEvents();
        System.out.println("Retrieved " + events.size() + " events from database");
        
        // Retrieve suspicious events
        var suspiciousEvents = dbService.getSuspiciousEvents();
        System.out.println("Retrieved " + suspiciousEvents.size() + " suspicious events from database");
        
        // Don't close the database service here as it's a singleton
        System.out.println("DatabaseService test completed!");
    }
    
    private static void testMonitorService() {
        System.out.println("Testing MonitorService...");
        
        MonitorService monitorService = new MonitorService();
        
        // This would normally start monitoring a directory
        // For testing purposes, we'll just verify it initializes correctly
        System.out.println("MonitorService initialized successfully");
        
        monitorService.close();
        System.out.println("MonitorService test completed!");
    }
}