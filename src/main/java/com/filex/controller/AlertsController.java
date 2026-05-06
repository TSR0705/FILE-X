package com.filex.controller;

import com.filex.model.Alert;
import com.filex.service.DatabaseService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AlertsController implements Initializable {
    @FXML private VBox alertsContainer;
    @FXML private ListView<Alert> alertsList;
    @FXML private Label lblAlertCount;
    @FXML private Button btnRefreshAlerts;
    @FXML private Button btnClearAlerts;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Alerts Controller Initialized");
        
        // Register with MonitorService for real-time alerts
        com.filex.service.MonitorService.getInstance().setAlertsController(this);
        
        loadAlerts();
        
        btnRefreshAlerts.setOnAction(e -> loadAlerts());
    }

    private void loadAlerts() {
        try {
            DatabaseService dbService = DatabaseService.getInstance();
            List<Alert> alerts = dbService.getAllAlerts();
            
            alertsList.getItems().setAll(alerts);
            lblAlertCount.setText(String.valueOf(alerts.size()));
            
            System.out.println("Alerts loaded: " + alerts.size() + " entries.");
        } catch (Exception e) {
            System.err.println("Error loading alerts: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Add an alert object to the UI
     * 
     * @param alert The alert to add
     */
    public void addAlertObject(com.filex.model.Alert alert) {
        alertsList.getItems().add(0, alert);
        lblAlertCount.setText(String.valueOf(alertsList.getItems().size()));
    }

    @FXML
    private void showNotificationCenter() {
        System.out.println("Notification center opened");
    }

    @FXML
    private void acknowledgeAlert() {
        Alert selected = alertsList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setAcknowledged(true);
            System.out.println("Alert acknowledged: " + selected.getId());
            loadAlerts(); // Refresh to show changes if needed
        }
    }
}
