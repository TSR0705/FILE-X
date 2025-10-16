package com.filex;

import com.filex.service.DatabaseService;
import com.filex.service.MonitorService;
import com.filex.model.FileEvent;
import com.filex.util.HashUtil;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * Simple test for FileX backend components.
 */
public class TestSimple {
    
    public static void main(String[] args) {
        System.out.println("FileX Simple Test Started");
        
        try {
            // Test 1: Database Service
            testDatabaseService();
            
            // Test 2: Hash Utility
            testHashUtil();
            
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
}