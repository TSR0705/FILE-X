package com.filex.model;

import java.time.LocalDateTime;

/**
 * Model class representing an alert.
 * Captures details about suspicious file activities.
 */
public class Alert {
    private int id;
    private int fileEventId;
    private String severity; // LOW, MEDIUM, HIGH, CRITICAL
    private boolean acknowledged;
    private LocalDateTime createdAt;
    private String actionsTaken;

    /**
     * Default constructor
     */
    public Alert() {
    }

    /**
     * Constructor with all fields
     * 
     * @param id The unique identifier
     * @param fileEventId The associated file event ID
     * @param severity The alert severity
     * @param acknowledged Whether the alert has been acknowledged
     * @param createdAt The creation timestamp
     * @param actionsTaken Actions taken in response to the alert
     */
    public Alert(int id, int fileEventId, String severity, boolean acknowledged, LocalDateTime createdAt, String actionsTaken) {
        this.id = id;
        this.fileEventId = fileEventId;
        this.severity = severity;
        this.acknowledged = acknowledged;
        this.createdAt = createdAt;
        this.actionsTaken = actionsTaken;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFileEventId() {
        return fileEventId;
    }

    public void setFileEventId(int fileEventId) {
        this.fileEventId = fileEventId;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public boolean isAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(boolean acknowledged) {
        this.acknowledged = acknowledged;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getActionsTaken() {
        return actionsTaken;
    }

    public void setActionsTaken(String actionsTaken) {
        this.actionsTaken = actionsTaken;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "id=" + id +
                ", fileEventId=" + fileEventId +
                ", severity='" + severity + '\'' +
                ", acknowledged=" + acknowledged +
                ", createdAt=" + createdAt +
                ", actionsTaken='" + actionsTaken + '\'' +
                '}';
    }
}