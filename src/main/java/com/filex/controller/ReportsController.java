package com.filex.controller;

import com.filex.model.FileEvent;
import com.filex.service.DatabaseService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {
    @FXML private Label lblTotalEvents;
    @FXML private Label lblSuspiciousEvents;
    @FXML private Label lblFilesAccessed;
    
    @FXML private DatePicker dateFrom;
    @FXML private DatePicker dateTo;
    @FXML private ComboBox<String> cmbReportType;
    @FXML private Button btnGenerate;
    @FXML private Button btnRefresh;
    
    @FXML private BarChart<String, Number> barChartEvents;
    @FXML private PieChart pieChartSeverity;
    
    @FXML private TableView<FileEvent> tableReport;
    @FXML private TableColumn<FileEvent, String> colReportDate;
    @FXML private TableColumn<FileEvent, String> colReportEvent;
    @FXML private TableColumn<FileEvent, String> colReportFile;
    @FXML private TableColumn<FileEvent, String> colReportSeverity;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Reports Controller Initialized");
        
        dateFrom.setValue(LocalDate.now().minusDays(7));
        dateTo.setValue(LocalDate.now());
        
        cmbReportType.getItems().addAll("Detailed Activity", "Suspicious Only", "Summary");
        cmbReportType.getSelectionModel().select(0);
        
        setupTable();
        loadReportData();
        
        btnRefresh.setOnAction(e -> loadReportData());
        btnGenerate.setOnAction(e -> loadReportData());
    }

    private void setupTable() {
        colReportDate.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        colReportFile.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        colReportEvent.setCellValueFactory(new PropertyValueFactory<>("eventType"));
        colReportSeverity.setCellValueFactory(new PropertyValueFactory<>("suspicious"));
    }

    private void loadReportData() {
        try {
            DatabaseService dbService = DatabaseService.getInstance();
            List<FileEvent> events = dbService.getAllEvents();
            
            lblTotalEvents.setText(String.valueOf(events.size()));
            long suspicious = events.stream().filter(FileEvent::isSuspicious).count();
            lblSuspiciousEvents.setText(String.valueOf(suspicious));
            
            long uniqueFiles = events.stream().map(FileEvent::getFileName).distinct().count();
            lblFilesAccessed.setText(String.valueOf(uniqueFiles));
            
            tableReport.getItems().setAll(events);
            
            System.out.println("Reports data loaded.");
        } catch (Exception e) {
            System.err.println("Error loading reports: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
