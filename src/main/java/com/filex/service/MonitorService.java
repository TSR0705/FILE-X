package com.filex.service;

import com.filex.model.FileEvent;
import com.filex.model.Alert;
import com.filex.util.FXUtil;
import com.filex.util.HashUtil;
import com.filex.controller.AlertsController;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;

/**
 * Service class for monitoring file system events.
 * This implementation uses Java's WatchService API for real-time file system monitoring.
 */
public class MonitorService {
    
    private WatchService watchService;
    private ExecutorService executorService;
    private DatabaseService databaseService;
    private boolean isMonitoring = false;
    private String monitoredPath;
    private OnFileEventListener onFileEventListener;
    private AlertsController alertsController;
    
    // Interface for file event callbacks
    public interface OnFileEventListener {
        void onFileEvent(FileEvent event);
    }
    
    /**
     * Set the alerts controller for real-time alert updates
     * 
     * @param controller The alerts controller
     */
    public void setAlertsController(AlertsController controller) {
        this.alertsController = controller;
    }
    
    /**
     * Constructor
     */
    public MonitorService() {
        try {
            this.watchService = FileSystems.getDefault().newWatchService();
            this.executorService = Executors.newSingleThreadExecutor();
            // Use the singleton database service instance
            this.databaseService = DatabaseService.getInstance();
        } catch (IOException e) {
            System.err.println("Error initializing MonitorService: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Set the file event listener for real-time UI updates
     * 
     * @param listener The listener to notify of file events
     */
    public void setOnFileEventListener(OnFileEventListener listener) {
        this.onFileEventListener = listener;
    }
    
    /**
     * Check if monitoring is currently active.
     * 
     * @return true if monitoring is active, false otherwise
     */
    public boolean isMonitoring() {
        return isMonitoring;
    }
    
    /**
     * Start monitoring the file system for events.
     * 
     * @param path The path to monitor
     */
    public void startMonitoring(String path) {
        // Stop any existing monitoring first
        if (isMonitoring) {
            stopMonitoring();
        }
        
        this.monitoredPath = path;
        
        try {
            // Initialize database service
            databaseService.initialize();
            
            // Register the path with the watch service
            Path dir = Paths.get(path);
            dir.register(watchService, 
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);
            
            isMonitoring = true;
            System.out.println("Started monitoring path: " + path);
            
            // Start monitoring in a separate thread
            executorService.submit(this::monitorFileSystem);
        } catch (IOException e) {
            System.err.println("Error starting monitoring: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Stop monitoring the file system for events.
     */
    public void stopMonitoring() {
        if (!isMonitoring) {
            System.out.println("Monitoring is not active.");
            return;
        }
        
        isMonitoring = false;
        try {
            watchService.close();
            watchService = FileSystems.getDefault().newWatchService();
            System.out.println("Stopped monitoring.");
        } catch (IOException e) {
            System.err.println("Error stopping monitoring: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Monitor file system events in a separate thread.
     */
    private void monitorFileSystem() {
        while (isMonitoring) {
            try {
                WatchKey key = watchService.take();
                
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    
                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        continue;
                    }
                    
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path fileName = ev.context();
                    Path fullPath = ((Path) key.watchable()).resolve(fileName);
                    
                    // Create FileEvent object
                    FileEvent fileEvent = new FileEvent();
                    fileEvent.setFileName(fileName.toString());
                    fileEvent.setEventType(kind.name().replace("ENTRY_", "")); // Remove "ENTRY_" prefix
                    fileEvent.setTimestamp(LocalDateTime.now());
                    
                    // Compute SHA-256 hash if file exists
                    String fullPathStr = fullPath.toString();
                    if (Files.exists(fullPath) && !Files.isDirectory(fullPath)) {
                        String sha256 = HashUtil.computeSHA256(fullPathStr);
                        fileEvent.setSha256(sha256);
                    } else {
                        fileEvent.setSha256(""); // Empty for deleted files or directories
                    }
                    
                    boolean isSuspicious = isSuspiciousEvent(kind, fullPath);
                    fileEvent.setSuspicious(isSuspicious);
                    
                    // Save to database
                    databaseService.saveEvent(fileEvent);
                    
                    // If the event is suspicious, create an alert
                    if (isSuspicious) {
                        // Create an alert for the suspicious event
                        Alert alert = new Alert();
                        alert.setFileEventId(0); // This should be the actual file event ID
                        alert.setSeverity("HIGH");
                        alert.setAcknowledged(false);
                        alert.setCreatedAt(LocalDateTime.now());
                        alert.setActionsTaken("File event detected as suspicious: " + fileEvent.getFileName());
                        databaseService.saveAlert(alert);
                        System.out.println("Alert created for suspicious event: " + fileEvent.getFileName());
                        
                        // Notify the alerts controller if available
                        if (alertsController != null) {
                            Platform.runLater(() -> alertsController.addAlertObject(alert));
                        }
                    }
                    
                    System.out.println("New event detected: " + fileEvent);
                    
                    // Notify listener for real-time UI updates
                    if (onFileEventListener != null) {
                        // Use JavaFX Application Thread for UI updates
                        Platform.runLater(() -> onFileEventListener.onFileEvent(fileEvent));
                    }
                }
                
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            } catch (InterruptedException e) {
                System.out.println("Monitoring interrupted.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    /**
     * Determine if a file event is suspicious.
     * 
     * @param kind The type of event
     * @param path The path of the file
     * @return true if the event is suspicious, false otherwise
     */
    private boolean isSuspiciousEvent(WatchEvent.Kind<?> kind, Path path) {
        // Simple suspicious detection logic
        // In a real implementation, this would be more sophisticated
        String fileName = path.getFileName().toString().toLowerCase();
        
        // Check for sensitive file types
        if (fileName.endsWith(".key") || fileName.endsWith(".pem") || 
            fileName.endsWith(".p12") || fileName.endsWith(".cer") ||
            fileName.endsWith(".jks") || fileName.contains("password") ||
            fileName.contains("secret") || fileName.contains("private")) {
            return true;
        }
        
        // Check for bulk operations would go here
        // This would require tracking multiple events in a time window
        
        return false;
    }
    
    /**
     * Get the currently monitored path.
     * 
     * @return The monitored path
     */
    public String getMonitoredPath() {
        return monitoredPath;
    }
    
    /**
     * Get recent file events from the database.
     * 
     * @return List of recent file events
     */
    public List<FileEvent> getRecentEvents() {
        // Initialize database service if not already done
        databaseService.initialize();
        // Return events from database instead of mock data
        return databaseService.getAllEvents();
    }
    
    /**
     * Add a file event to the monitoring system.
     * This method would be called by the file watcher when events occur.
     * 
     * @param event The file event to add
     */
    public void addEvent(FileEvent event) {
        // Initialize database service if not already done
        databaseService.initialize();
        // Save event to database
        databaseService.saveEvent(event);
        System.out.println("New event detected: " + event);
    }
    
    /**
     * Close resources.
     */
    public void close() {
        stopMonitoring();
        executorService.shutdown();
        // Don't close database service here as it's a singleton
        // databaseService.close();
    }
}