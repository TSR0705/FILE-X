package com.filex.controller;

import com.filex.model.FileEvent;
import com.filex.model.Alert;
import com.filex.service.DatabaseService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class StatisticsController implements Initializable {
    @FXML private Label lblTotalEvents;
    @FXML private Label lblSuspiciousEvents;
    @FXML private Label lblFilesMonitored;
    @FXML private Label lblAlertsGenerated;
    
    @FXML private LineChart<String, Number> lineChartEvents;
    @FXML private PieChart pieChartTypes;
    @FXML private BarChart<String, Number> barChartSeverity;
    @FXML private Button btnRefresh;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Statistics Controller Initialized");
        loadStatistics();
        btnRefresh.setOnAction(e -> loadStatistics());
    }

    private void loadStatistics() {
        try {
            DatabaseService dbService = DatabaseService.getInstance();
            List<FileEvent> events = dbService.getAllEvents();
            List<Alert> alerts = dbService.getAllAlerts();
            
            // Update Labels
            lblTotalEvents.setText(String.valueOf(events.size()));
            long suspicious = events.stream().filter(FileEvent::isSuspicious).count();
            lblSuspiciousEvents.setText(String.valueOf(suspicious));
            
            long uniqueFiles = events.stream().map(FileEvent::getFileName).distinct().count();
            lblFilesMonitored.setText(String.valueOf(uniqueFiles));
            lblAlertsGenerated.setText(String.valueOf(alerts.size()));
            
            // Update Pie Chart (Event Types)
            Map<String, Long> typeCounts = events.stream()
                .collect(Collectors.groupingBy(FileEvent::getEventType, Collectors.counting()));
            
            pieChartTypes.getData().setAll(
                typeCounts.entrySet().stream()
                    .map(entry -> new PieChart.Data(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList())
            );
            
            // Update Bar Chart (Alert Severity)
            Map<String, Long> severityCounts = alerts.stream()
                .collect(Collectors.groupingBy(Alert::getSeverity, Collectors.counting()));
            
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            severityCounts.forEach((severity, count) -> series.getData().add(new XYChart.Data<>(severity, count)));
            barChartSeverity.getData().setAll(series);
            
            System.out.println("Statistics loaded.");
        } catch (Exception e) {
            System.err.println("Error loading statistics: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
