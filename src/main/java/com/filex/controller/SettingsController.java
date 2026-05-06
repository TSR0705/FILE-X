package com.filex.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML private TextField txtMonitoredFolder;
    @FXML private Button btnBrowseFolder;
    @FXML private CheckBox chkNotifications;
    @FXML private ComboBox<String> cmbHashing;
    @FXML private Button btnSave;
    @FXML private Button btnReset;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Settings Controller Initialized");
    }
}
