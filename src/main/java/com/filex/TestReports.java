package com.filex;

import com.filex.service.DatabaseService;
import com.filex.model.FileEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestReports {
    public static void main(String[] args) {
        System.out.println("Testing Reports functionality...");
        
        try {
            DatabaseService dbService = DatabaseService.getInstance();
            dbService.initialize();
            
            // Simulate what ReportsController does
            LocalDate today = LocalDate.now();
            LocalDate startDate = today.minusDays(7); // Default to last 7 days
            
            System.out.println("Date range: " + startDate + " to " + today);
            
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = today.atTime(23, 59, 59);
            
            System.out.println("DateTime range: " + startDateTime + " to " + endDateTime);
            
            // Get events within date range
            List<FileEvent> events = dbService.getEventsByDateRange(startDateTime, endDateTime);
            System.out.println("Found " + events.size() + " events in date range");
            
            // Calculate statistics
            long totalEvents = events.size();
            long suspiciousCount = events.stream()
                    .filter(FileEvent::isSuspicious)
                    .count();
            long filesAccessed = events.stream()
                    .map(FileEvent::getFileName)
                    .distinct()
                    .count();
            
            System.out.println("Total events: " + totalEvents);
            System.out.println("Suspicious events: " + suspiciousCount);
            System.out.println("Files accessed: " + filesAccessed);
            
            // Count events by type
            Map<String, Long> eventTypeCounts = events.stream()
                    .collect(Collectors.groupingBy(FileEvent::getEventType, Collectors.counting()));
            
            System.out.println("Event type counts: " + eventTypeCounts);
            
            dbService.close();
        } catch (Exception e) {
            System.err.println("Error testing reports: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("Reports test completed.");
    }
}