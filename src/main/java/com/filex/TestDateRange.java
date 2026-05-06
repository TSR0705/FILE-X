package com.filex;

import com.filex.service.DatabaseService;
import com.filex.model.FileEvent;
import java.time.LocalDateTime;
import java.util.List;

public class TestDateRange {
    public static void main(String[] args) {
        System.out.println("Testing date range functionality...");
        
        try {
            DatabaseService dbService = DatabaseService.getInstance();
            dbService.initialize();
            
            // Get all events first
            List<FileEvent> allEvents = dbService.getAllEvents();
            System.out.println("Total events: " + allEvents.size());
            
            if (!allEvents.isEmpty()) {
                // Get the date range from the first and last events
                FileEvent firstEvent = allEvents.get(0);
                FileEvent lastEvent = allEvents.get(allEvents.size() - 1);
                
                System.out.println("First event: " + firstEvent.getTimestamp());
                System.out.println("Last event: " + lastEvent.getTimestamp());
                
                // Test with a wide date range that should include all events
                LocalDateTime startDate = lastEvent.getTimestamp().minusDays(30);
                LocalDateTime endDate = firstEvent.getTimestamp().plusDays(1);
                
                System.out.println("Querying between " + startDate + " and " + endDate);
                
                List<FileEvent> rangeEvents = dbService.getEventsByDateRange(startDate, endDate);
                System.out.println("Events in range: " + rangeEvents.size());
                
                // Test with today's date
                LocalDateTime todayStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
                LocalDateTime todayEnd = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
                
                System.out.println("Querying today between " + todayStart + " and " + todayEnd);
                
                List<FileEvent> todayEvents = dbService.getEventsByDateRange(todayStart, todayEnd);
                System.out.println("Events today: " + todayEvents.size());
            }
            
            dbService.close();
        } catch (Exception e) {
            System.err.println("Error testing date range: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("Date range test completed.");
    }
}