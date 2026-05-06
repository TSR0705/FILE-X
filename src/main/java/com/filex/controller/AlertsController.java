package com.filex.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;

public class AlertsController implements Initializable {
    @FXML private VBox alertContainer;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Alerts Controller Initialized");
    }

    /**
     * Add an alert object to the UI
     * 
     * @param alert The alert to add
     */
    public void addAlertObject(com.filex.model.Alert alert) {
        System.out.println("Adding alert to UI: " + alert.getActionsTaken());
        // In a real implementation, we would add this to the alertContainer VBox
    }

    @FXML
    private void showNotificationCenter() {
        System.out.println("Notification center opened");
    }

    @FXML
    private void acknowledgeAlert() {
        System.out.println("Alert acknowledged");
    }
}
