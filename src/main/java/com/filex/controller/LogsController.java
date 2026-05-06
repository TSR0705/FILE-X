package com.filex.controller;

import com.filex.model.FileEvent;
import com.filex.service.DatabaseService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class LogsController implements Initializable {
    @FXML private TableView<FileEvent> tableLogs;
    @FXML private TableColumn<FileEvent, String> colFileName;
    @FXML private TableColumn<FileEvent, String> colEventType;
    @FXML private TableColumn<FileEvent, String> colTimestamp;
    @FXML private TableColumn<FileEvent, String> colHash;
    @FXML private TableColumn<FileEvent, String> colStatus;
    
    @FXML private Button btnRefresh;
    @FXML private TextField txtSearch;
    @FXML private Label lblLogCount;
    @FXML private Label lblLastUpdated;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Logs Controller Initialized");
        
        setupTableColumns();
        loadLogs();
        
        btnRefresh.setOnAction(e -> loadLogs());

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterLogs(newValue);
        });
    }

    private void filterLogs(String query) {
        if (query == null || query.isEmpty()) {
            loadLogs();
            return;
        }
        
        String lowerQuery = query.toLowerCase();
        List<FileEvent> filtered = tableLogs.getItems().stream()
            .filter(e -> e.getFileName().toLowerCase().contains(lowerQuery))
            .collect(java.util.stream.Collectors.toList());
        tableLogs.getItems().setAll(filtered);
    }

    private void setupTableColumns() {
        colFileName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        colEventType.setCellValueFactory(new PropertyValueFactory<>("eventType"));
        colTimestamp.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        colHash.setCellValueFactory(new PropertyValueFactory<>("sha256"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("suspicious"));
    }

    private void loadLogs() {
        try {
            DatabaseService dbService = DatabaseService.getInstance();
            List<FileEvent> events = dbService.getAllEvents();
            
            tableLogs.getItems().setAll(events);
            lblLogCount.setText(events.size() + " logs displayed");
            lblLastUpdated.setText("Last updated: " + java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            
            System.out.println("Logs loaded: " + events.size() + " entries.");
        } catch (Exception e) {
            System.err.println("Error loading logs: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
