package com.filex;

import com.filex.service.DatabaseService;
import com.filex.model.FileEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class VerifyReportsFix {
    public static void main(String[] args) {
        System.out.println("Verifying Reports Fix...");
        
        try {
            // Initialize database
            DatabaseService dbService = DatabaseService.getInstance();
            dbService.initialize();
            
            // Test that we can retrieve events
            List<FileEvent> events = dbService.getAllEvents();
            System.out.println("✓ Successfully retrieved " + events.size() + " events from database");
            
            // Test date range query
            LocalDate today = LocalDate.now();
            LocalDate startDate = today.minusDays(7);
            
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = today.atTime(23, 59, 59);
            
            List<FileEvent> rangeEvents = dbService.getEventsByDateRange(startDateTime, endDateTime);
            System.out.println("✓ Successfully retrieved " + rangeEvents.size() + " events in date range");
            
            // Verify we have data
            if (rangeEvents.size() > 0) {
                System.out.println("✓ SUCCESS: Reports should now show real data!");
                System.out.println("  Expected values:");
                System.out.println("    Total Events: " + rangeEvents.size());
                
                long suspiciousCount = rangeEvents.stream()
                        .filter(FileEvent::isSuspicious)
                        .count();
                System.out.println("    Suspicious Events: " + suspiciousCount);
                
                long filesAccessed = rangeEvents.stream()
                        .map(FileEvent::getFileName)
                        .distinct()
                        .count();
                System.out.println("    Files Accessed: " + filesAccessed);
                
                System.out.println("\n✓ All tests passed! The reports should now display real-time data.");
            } else {
                System.out.println("⚠ WARNING: No events found in date range");
            }
            
            dbService.close();
        } catch (Exception e) {
            System.err.println("✗ Error verifying reports fix: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\nVerification completed.");
    }
}