package com.filex;

import com.filex.service.DatabaseService;
import com.filex.model.FileEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestReportsControllerLogic {
    public static void main(String[] args) {
        System.out.println("Testing ReportsController logic...");
        
        try {
            DatabaseService dbService = DatabaseService.getInstance();
            dbService.initialize();
            
            // Simulate the ReportsController logic
            simulateReportsController(dbService);
            
            dbService.close();
        } catch (Exception e) {
            System.err.println("Error testing ReportsController logic: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("ReportsController logic test completed.");
    }
    
    private static void simulateReportsController(DatabaseService databaseService) {
        // Simulate date selection
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(7); // Default to last 7 days
        
        System.out.println("Simulating date range: " + startDate + " to " + today);
        
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = today.atTime(23, 59, 59);
        
        // Get events (similar to getEventsWithinDateRange)
        List<FileEvent> allEvents = databaseService.getEventsByDateRange(startDateTime, endDateTime);
        System.out.println("Retrieved " + allEvents.size() + " events");
        
        // Update summary cards (similar to generateReportData)
        String totalEventsText = String.valueOf(allEvents.size());
        long suspiciousCount = allEvents.stream()
                .filter(FileEvent::isSuspicious)
                .count();
        String suspiciousEventsText = String.valueOf(suspiciousCount);
        
        long filesAccessed = allEvents.stream()
                .map(FileEvent::getFileName)
                .distinct()
                .count();
        String filesAccessedText = String.valueOf(filesAccessed);
        
        System.out.println("Summary cards would show:");
        System.out.println("  Total Events: " + totalEventsText);
        System.out.println("  Suspicious Events: " + suspiciousEventsText);
        System.out.println("  Files Accessed: " + filesAccessedText);
        
        // Update bar chart (similar to updateBarChart)
        Map<String, Long> eventTypeCounts = allEvents.stream()
                .collect(Collectors.groupingBy(FileEvent::getEventType, Collectors.counting()));
        
        System.out.println("Bar chart data:");
        eventTypeCounts.forEach((eventType, count) -> 
            System.out.println("  " + eventType + ": " + count));
        
        // Update pie chart (similar to updatePieChart)
        long normalCount = allEvents.size() - suspiciousCount;
        System.out.println("Pie chart data:");
        System.out.println("  Normal: " + normalCount);
        System.out.println("  Suspicious: " + suspiciousCount);
        
        // Update table (similar to updateReportTable)
        System.out.println("Table would show " + allEvents.size() + " items");
        if (!allEvents.isEmpty()) {
            FileEvent firstEvent = allEvents.get(0);
            System.out.println("  First item: " + firstEvent.getFileName() + 
                             " - " + firstEvent.getEventType() + 
                             " (" + (firstEvent.isSuspicious() ? "Suspicious" : "Normal") + ")");
        }
    }
}