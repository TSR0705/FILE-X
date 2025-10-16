package com.filex.controller;

import com.filex.theme.ThemeManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the TopBar view.
 * Manages the top application bar with title, folder path, status indicator, and theme toggle.
 * 
 * Future integration points:
 * - NavigationService: Will handle view transitions
 * - MonitorService: Will provide monitoring status
 */
public class TopBarController implements Initializable {

    @FXML
    private HBox topBarContainer;
    
    @FXML
    private Label lblAppTitle;
    
    @FXML
    private Label lblFolderPath;
    
    @FXML
    private HBox statusPill;
    
    @FXML
    private Label lblStatus;
    
    @FXML
    private Button btnThemeToggle;
    
    @FXML
    private SVGPath themeIcon;
    
    @FXML
    private Button btnSimulate;
    
    private boolean isMonitoring = true;
    private Timeline pulseTimeline;
    
    /**
     * Initialize the controller
     * 
     * @param location The location used to resolve relative paths for the root object
     * @param resources The resources used to localize the root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set up button actions
        setupButtonActions();
        
        // Start monitoring status pulse animation
        startStatusPulse();
    }
    
    /**
     * Set up button actions
     */
    private void setupButtonActions() {
        btnThemeToggle.setOnAction(event -> toggleTheme());
        btnSimulate.setOnAction(event -> simulateEvent());
    }
    
    /**
     * Toggle between light and dark themes
     */
    private void toggleTheme() {
        // Get the scene from any node
        if (topBarContainer.getScene() != null) {
            ThemeManager.toggleTheme(topBarContainer.getScene());
            
            // Update the theme icon
            if (ThemeManager.getCurrentTheme() == ThemeManager.ThemeType.LIGHT) {
                // Moon icon for dark theme
                themeIcon.setContent("M12 3c-4.97 0-9 4.03-9 9s4.03 9 9 9 9-4.03 9-9c0-.46-.04-.92-.1-1.36-.98 1.37-2.58 2.26-4.4 2.26-2.98 0-5.4-2.42-5.4-5.4 0-1.81.89-3.42 2.26-4.4-.44-.06-.9-.1-1.36-.1z");
            } else {
                // Sun icon for light theme
                themeIcon.setContent("M12 7c-2.76 0-5 2.24-5 5s2.24 5 5 5 5-2.24 5-5-2.24-5-5-5zM2 13h2c.55 0 1-.45 1-1s-.45-1-1-1H2c-.55 0-1 .45-1 1s.45 1 1 1zm18 0h2c.55 0 1-.45 1-1s-.45-1-1-1h-2c-.55 0-1 .45-1 1s.45 1 1 1zM11 2v2c0 .55.45 1 1 1s1-.45 1-1V2c0-.55-.45-1-1-1s-1 .45-1 1zm0 18v2c0 .55.45 1 1 1s1-.45 1-1v-2c0-.55-.45-1-1-1s-1 .45-1 1z");
            }
        }
    }
    
    /**
     * Start a manual scan or simulate an event
     */
    private void simulateEvent() {
        // TODO: Implement scan logic
        // This will be connected to a MonitorService in the future
        System.out.println("Manual event simulation started");
    }
    
    /**
     * Start the status pulse animation
     */
    private void startStatusPulse() {
        pulseTimeline = new Timeline(
            new KeyFrame(Duration.seconds(2), event -> {
                if (isMonitoring) {
                    statusPill.getStyleClass().add("status-pulse");
                }
            })
        );
        pulseTimeline.setCycleCount(Timeline.INDEFINITE);
        pulseTimeline.play();
    }
    
    /**
     * Toggle monitoring status
     */
    public void toggleMonitoring() {
        isMonitoring = !isMonitoring;
        if (isMonitoring) {
            lblStatus.setText("ON");
            statusPill.getStyleClass().remove("off");
            if (pulseTimeline != null) {
                pulseTimeline.play();
            }
        } else {
            lblStatus.setText("OFF");
            statusPill.getStyleClass().add("off");
            statusPill.getStyleClass().remove("status-pulse");
            if (pulseTimeline != null) {
                pulseTimeline.stop();
            }
        }
    }
    
    /**
     * Update the monitored folder path display
     * 
     * @param path The new folder path to display
     */
    public void setFolderPath(String path) {
        lblFolderPath.setText("Monitoring: " + path);
    }
}