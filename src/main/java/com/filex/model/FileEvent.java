package com.filex.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Model class representing a file system event.
 * Captures details about file operations such as creation, modification, or deletion.
 */
public class FileEvent {
    private String fileName;
    private String eventType;
    private LocalDateTime timestamp;
    private String sha256;
    private boolean suspicious;

    /**
     * Default constructor
     */
    public FileEvent() {
    }

    /**
     * Constructor with all fields
     * 
     * @param fileName The name of the file
     * @param eventType The type of event (Created, Modified, Deleted, Renamed, Copied)
     * @param timestamp The time of the event
     * @param sha256 The SHA256 hash of the file
     * @param suspicious Whether the event is flagged as suspicious
     */
    public FileEvent(String fileName, String eventType, LocalDateTime timestamp, String sha256, boolean suspicious) {
        this.fileName = fileName;
        this.eventType = eventType;
        this.timestamp = timestamp;
        this.sha256 = sha256;
        this.suspicious = suspicious;
    }

    // Getters and setters
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public boolean isSuspicious() {
        return suspicious;
    }

    public void setSuspicious(boolean suspicious) {
        this.suspicious = suspicious;
    }

    @Override
    public String toString() {
        return "FileEvent{" +
                "fileName='" + fileName + '\'' +
                ", eventType='" + eventType + '\'' +
                ", timestamp=" + timestamp +
                ", sha256='" + sha256 + '\'' +
                ", suspicious=" + suspicious +
                '}';
    }

    /**
     * Generate a list of mock file events for testing
     * 
     * @return List of mock FileEvent objects
     */
    public static List<FileEvent> generateMockEvents() {
        List<FileEvent> events = new ArrayList<>();
        Random random = new Random();
        
        String[] fileNames = {
            "confidential_report.pdf", "budget.xlsx", "meeting_notes.docx", 
            "source_code.java", "database_backup.sql", "user_data.csv",
            "api_keys.json", "config.xml", "readme.txt", "presentation.pptx",
            "image.png", "video.mp4", "archive.zip", "log.txt"
        };
        
        String[] eventTypes = {"Created", "Modified", "Deleted", "Renamed", "Copied"};
        
        // Generate 10-15 mock events
        int eventCount = 10 + random.nextInt(6);
        for (int i = 0; i < eventCount; i++) {
            String fileName = fileNames[random.nextInt(fileNames.length)];
            String eventType = eventTypes[random.nextInt(eventTypes.length)];
            LocalDateTime timestamp = LocalDateTime.now().minusMinutes(i * 5);
            String sha256 = generateMockHash();
            
            // Make some events suspicious (about 20%)
            boolean suspicious = random.nextInt(5) == 0;
            
            events.add(new FileEvent(fileName, eventType, timestamp, sha256, suspicious));
        }
        
        return events;
    }
    
    /**
     * Generate a mock SHA256 hash
     * 
     * @return Mock SHA256 hash string
     */
    private static String generateMockHash() {
        Random random = new Random();
        StringBuilder hash = new StringBuilder();
        for (int i = 0; i < 64; i++) {
            int digit = random.nextInt(16);
            hash.append(Integer.toHexString(digit));
        }
        return hash.toString();
    }
    
    /**
     * Main method for testing the FileEvent class
     */
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