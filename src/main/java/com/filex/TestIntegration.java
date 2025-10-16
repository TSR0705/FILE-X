package com.filex;

import com.filex.service.DatabaseService;
import com.filex.service.MonitorService;
import com.filex.model.FileEvent;
import com.filex.util.HashUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * Integration test for FileX backend components.
 */
public class TestIntegration {
    
    public static void main(String[] args) {
        System.out.println("FileX Integration Test Started");
        
        try {
            // Test 1: Database Service
            testDatabaseService();
            
            // Test 2: Hash Utility
            testHashUtil();
            
            // Test 3: Monitor Service
            testMonitorService();
            
            System.out.println("All tests completed successfully!");
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testDatabaseService() throws Exception {
        System.out.println("\n--- Testing DatabaseService ---");
        
        // Use the singleton instance
        DatabaseService dbService = DatabaseService.getInstance();
        dbService.initialize();
        
        // Create a test file event
        FileEvent event = new FileEvent();
        event.setFileName("test_document.txt");
        event.setEventType("Created");
        event.setTimestamp(LocalDateTime.now());
        event.setSha256("abcdef1234567890");
        event.setSuspicious(false);
        
        // Save the event
        dbService.saveEvent(event);
        System.out.println("File event saved successfully");
        
        // Retrieve events
        var events = dbService.getAllEvents();
        System.out.println("Retrieved " + events.size() + " events from database");
        
        // Retrieve suspicious events
        var suspiciousEvents = dbService.getSuspiciousEvents();
        System.out.println("Retrieved " + suspiciousEvents.size() + " suspicious events from database");
        
        // Don't close the database service here as it's a singleton
        System.out.println("DatabaseService test completed!");
    }
    
    private static void testHashUtil() throws Exception {
        System.out.println("\n--- Testing HashUtil ---");
        
        // Create a temporary test file
        Path tempFile = Files.createTempFile("test", ".txt");
        try (FileWriter writer = new FileWriter(tempFile.toFile())) {
            writer.write("This is a test file for hashing");
        }
        
        // Compute SHA-256 hash
        String hash = HashUtil.computeSHA256(tempFile.toString());
        System.out.println("SHA-256 hash computed: " + hash);
        
        // Clean up
        Files.deleteIfExists(tempFile);
        
        System.out.println("HashUtil test completed!");
    }
    
    private static void testMonitorService() throws Exception {
        System.out.println("\n--- Testing MonitorService ---");
        
        MonitorService monitorService = new MonitorService();
        
        // Create a temporary directory for testing
        Path tempDir = Files.createTempDirectory("filex_test");
        System.out.println("Created temporary directory: " + tempDir);
        
        // Start monitoring
        monitorService.startMonitoring(tempDir.toString());
        System.out.println("Started monitoring directory: " + tempDir);
        
        // Wait a moment
        Thread.sleep(1000);
        
        // Create a test file in the monitored directory
        Path testFile = tempDir.resolve("test_file.txt");
        try (FileWriter writer = new FileWriter(testFile.toFile())) {
            writer.write("This is a test file for monitoring");
        }
        System.out.println("Created test file: " + testFile);
        
        // Wait for the event to be detected
        Thread.sleep(2000);
        
        // Modify the test file
        try (FileWriter writer = new FileWriter(testFile.toFile(), true)) {
            writer.write("\nAdditional content");
        }
        System.out.println("Modified test file: " + testFile);
        
        // Wait for the event to be detected
        Thread.sleep(2000);
        
        // Delete the test file
        Files.deleteIfExists(testFile);
        System.out.println("Deleted test file: " + testFile);
        
        // Wait for the event to be detected
        Thread.sleep(2000);
        
        // Stop monitoring
        monitorService.stopMonitoring();
        System.out.println("Stopped monitoring");
        
        // Clean up
        Files.deleteIfExists(tempDir);
        System.out.println("Cleaned up temporary directory");
        
        monitorService.close();
        System.out.println("MonitorService test completed!");
    }
}