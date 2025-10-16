package com.filex;

import com.filex.model.FileEvent;
import com.filex.model.Alert;
import com.filex.service.DatabaseService;
import com.filex.controller.AlertsController;
import java.time.LocalDateTime;

/**
 * Test class to verify alert functionality
 */
public class TestAlerts {
    
    public static void main(String[] args) {
        System.out.println("Testing Alerts Functionality...");
        
        try {
            // Initialize database service
            DatabaseService databaseService = DatabaseService.getInstance();
            databaseService.initialize();
            
            // Create a test file event
            FileEvent fileEvent = new FileEvent();
            fileEvent.setFileName("test_password_file.txt");
            fileEvent.setEventType("Created");
            fileEvent.setTimestamp(LocalDateTime.now());
            fileEvent.setSha256("test_hash");
            fileEvent.setSuspicious(true);
            
            // Save the file event
            databaseService.saveEvent(fileEvent);
            System.out.println("File event saved successfully");
            
            // Create a test alert
            Alert alert = new Alert();
            alert.setFileEventId(1);
            alert.setSeverity("HIGH");
            alert.setAcknowledged(false);
            alert.setCreatedAt(LocalDateTime.now());
            alert.setActionsTaken("Test alert for suspicious file");
            
            // Save the alert
            databaseService.saveAlert(alert);
            System.out.println("Alert saved successfully");
            
            // Retrieve alerts
            var alerts = databaseService.getAllAlerts();
            System.out.println("Retrieved " + alerts.size() + " alerts from database");
            
            // Retrieve suspicious events
            var suspiciousEvents = databaseService.getSuspiciousEvents();
            System.out.println("Retrieved " + suspiciousEvents.size() + " suspicious events from database");
            
            System.out.println("Alerts test completed successfully!");
            
        } catch (Exception e) {
            System.err.println("Error during alerts test: " + e.getMessage());
            e.printStackTrace();
        }
    }
}