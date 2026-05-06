package com.filex.service;

import com.filex.util.FXUtil;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.prefs.Preferences;

/**
 * Service class for managing application preferences.
 * Handles saving and loading user settings.
 * 
 * Future integration points:
 * - DatabaseService: Will provide persistent storage for preferences
 * - CloudSyncService: Will handle synchronization of preferences across devices
 */
public class PreferencesService {
    private static final String PREF_MONITORED_FOLDER = "monitoredFolder";
    private static final String PREF_NOTIFICATIONS_ENABLED = "notificationsEnabled";
    private static final String PREF_HASHING_ALGORITHM = "hashingAlgorithm";
    
    private Preferences prefs;
    private String monitoredFolder;
    private boolean notificationsEnabled;
    private String hashingAlgorithm;
    
    /**
     * Constructor
     */
    public PreferencesService() {
        prefs = Preferences.userNodeForPackage(PreferencesService.class);
        loadPreferences();
    }
    
    /**
     * Load preferences from storage
     */
    private void loadPreferences() {
        monitoredFolder = prefs.get(PREF_MONITORED_FOLDER, System.getProperty("user.home"));
        notificationsEnabled = prefs.getBoolean(PREF_NOTIFICATIONS_ENABLED, true);
        hashingAlgorithm = prefs.get(PREF_HASHING_ALGORITHM, "SHA-256");
    }
    
    /**
     * Save preferences to storage
     */
    public void savePreferences() {
        prefs.put(PREF_MONITORED_FOLDER, monitoredFolder);
        prefs.putBoolean(PREF_NOTIFICATIONS_ENABLED, notificationsEnabled);
        prefs.put(PREF_HASHING_ALGORITHM, hashingAlgorithm);
    }
    
    /**
     * Get the monitored folder path
     * 
     * @return The monitored folder path
     */
    public String getMonitoredFolder() {
        return monitoredFolder;
    }
    
    /**
     * Set the monitored folder path
     * 
     * @param monitoredFolder The monitored folder path
     */
    public void setMonitoredFolder(String monitoredFolder) {
        this.monitoredFolder = monitoredFolder;
    }
    
    /**
     * Get whether notifications are enabled
     * 
     * @return true if notifications are enabled, false otherwise
     */
    public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }
    
    /**
     * Set whether notifications are enabled
     * 
     * @param notificationsEnabled true to enable notifications, false to disable
     */
    public void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }
    
    /**
     * Get the hashing algorithm
     * 
     * @return The hashing algorithm
     */
    public String getHashingAlgorithm() {
        return hashingAlgorithm;
    }
    
    /**
     * Set the hashing algorithm
     * 
     * @param hashingAlgorithm The hashing algorithm
     */
    public void setHashingAlgorithm(String hashingAlgorithm) {
        this.hashingAlgorithm = hashingAlgorithm;
    }
    
    /**
     * Reset preferences to default values
     */
    public void resetToDefaults() {
        monitoredFolder = System.getProperty("user.home");
        notificationsEnabled = true;
        hashingAlgorithm = "SHA-256";
        savePreferences();
    }
}