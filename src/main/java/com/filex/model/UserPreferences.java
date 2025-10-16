package com.filex.model;

import java.util.List;

/**
 * Model class representing user preferences.
 * Captures user configuration for the application.
 */
public class UserPreferences {
    private List<String> monitoredPaths;
    private int bulkCopyThreshold; // Number of files copied within time window to trigger alert
    private int bulkCopyTimeWindow; // Time window in minutes for bulk copy detection
    private int frequentModificationThreshold; // Number of modifications to same file within time window
    private int frequentModificationTimeWindow; // Time window in minutes for frequent modification detection
    private String startTime; // Start of off-hours in HH:mm format
    private String endTime; // End of off-hours in HH:mm format
    private String hashingAlgorithm; // Default SHA-256
    private boolean notificationsEnabled;
    private String theme; // LIGHT or DARK

    /**
     * Default constructor with default values
     */
    public UserPreferences() {
        this.bulkCopyThreshold = 5;
        this.bulkCopyTimeWindow = 10;
        this.frequentModificationThreshold = 3;
        this.frequentModificationTimeWindow = 15;
        this.startTime = "23:00";
        this.endTime = "06:00";
        this.hashingAlgorithm = "SHA-256";
        this.notificationsEnabled = true;
        this.theme = "LIGHT";
    }

    // Getters and setters
    public List<String> getMonitoredPaths() {
        return monitoredPaths;
    }

    public void setMonitoredPaths(List<String> monitoredPaths) {
        this.monitoredPaths = monitoredPaths;
    }

    public int getBulkCopyThreshold() {
        return bulkCopyThreshold;
    }

    public void setBulkCopyThreshold(int bulkCopyThreshold) {
        this.bulkCopyThreshold = bulkCopyThreshold;
    }

    public int getBulkCopyTimeWindow() {
        return bulkCopyTimeWindow;
    }

    public void setBulkCopyTimeWindow(int bulkCopyTimeWindow) {
        this.bulkCopyTimeWindow = bulkCopyTimeWindow;
    }

    public int getFrequentModificationThreshold() {
        return frequentModificationThreshold;
    }

    public void setFrequentModificationThreshold(int frequentModificationThreshold) {
        this.frequentModificationThreshold = frequentModificationThreshold;
    }

    public int getFrequentModificationTimeWindow() {
        return frequentModificationTimeWindow;
    }

    public void setFrequentModificationTimeWindow(int frequentModificationTimeWindow) {
        this.frequentModificationTimeWindow = frequentModificationTimeWindow;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getHashingAlgorithm() {
        return hashingAlgorithm;
    }

    public void setHashingAlgorithm(String hashingAlgorithm) {
        this.hashingAlgorithm = hashingAlgorithm;
    }

    public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return "UserPreferences{" +
                "monitoredPaths=" + monitoredPaths +
                ", bulkCopyThreshold=" + bulkCopyThreshold +
                ", bulkCopyTimeWindow=" + bulkCopyTimeWindow +
                ", frequentModificationThreshold=" + frequentModificationThreshold +
                ", frequentModificationTimeWindow=" + frequentModificationTimeWindow +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", hashingAlgorithm='" + hashingAlgorithm + '\'' +
                ", notificationsEnabled=" + notificationsEnabled +
                ", theme='" + theme + '\'' +
                '}';
    }
}