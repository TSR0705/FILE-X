package com.filex;

import com.filex.service.DatabaseService;
import com.filex.model.FileEvent;
import java.util.List;

public class CheckDatabase {
    public static void main(String[] args) {
        System.out.println("Checking database contents...");
        
        try {
            DatabaseService dbService = DatabaseService.getInstance();
            dbService.initialize();
            
            List<FileEvent> events = dbService.getAllEvents();
            System.out.println("Found " + events.size() + " events in the database");
            
            for (int i = 0; i < events.size(); i++) {
                FileEvent event = events.get(i);
                System.out.println("Event " + (i+1) + ": " + event.getFileName() + 
                                 " - " + event.getEventType() + 
                                 " at " + event.getTimestamp() +
                                 " (suspicious: " + event.isSuspicious() + ")");
            }
            
            dbService.close();
        } catch (Exception e) {
            System.err.println("Error checking database: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("Database check completed.");
    }
}