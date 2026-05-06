package com.filex;

import com.filex.model.FileEvent;
import java.util.List;

/**
 * Simple test class to verify FileEvent model works correctly
 */
public class TestFileEvent {
    public static void main(String[] args) {
        System.out.println("Testing FileEvent model...");
        
        // Generate mock events
        List<FileEvent> events = FileEvent.generateMockEvents();
        
        System.out.println("Generated " + events.size() + " mock events:");
        for (FileEvent event : events) {
            System.out.println(event);
        }
        
        System.out.println("Test completed successfully!");
    }
}