package com.filex.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML private TextField txtMonitoredFolder;
    @FXML private CheckBox chkEnableMonitoring;
    @FXML private CheckBox chkEnableNotifications;
    @FXML private ComboBox<String> cmbAlertLevel;
    @FXML private ComboBox<String> cmbHashingAlgorithm;
    @FXML private Slider sliderSensitivity;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Settings Controller Initialized");
        
        cmbAlertLevel.getItems().addAll("Low", "Medium", "High", "Critical");
        cmbAlertLevel.getSelectionModel().select("Medium");
        
        cmbHashingAlgorithm.getItems().addAll("SHA-256", "SHA-512", "MD5");
        cmbHashingAlgorithm.getSelectionModel().select("SHA-256");
        
        // Mock load from .env or preferences
        txtMonitoredFolder.setText("C:/Users/You/Projects/Confidential");
    }

    @FXML
    private void browseFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder to Monitor");
        Stage stage = (Stage) txtMonitoredFolder.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);
        
        if (selectedDirectory != null) {
            txtMonitoredFolder.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    private void saveSettings() {
        System.out.println("Settings saved successfully!");
        // Logic to update PreferencesService or .env
    }

    @FXML
    private void resetSettings() {
        txtMonitoredFolder.setText("C:/Users/You/Projects/Confidential");
        chkEnableMonitoring.setSelected(true);
        chkEnableNotifications.setSelected(true);
        cmbAlertLevel.getSelectionModel().select("Medium");
        cmbHashingAlgorithm.getSelectionModel().select("SHA-256");
        sliderSensitivity.setValue(50);
        System.out.println("Settings reset to defaults.");
    }
}
