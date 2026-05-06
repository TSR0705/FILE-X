package com.filex.controller;

import com.filex.model.FileEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the professional dashboard.
 */
public class ProfessionalDashboardController implements Initializable {

    @FXML
    private Label lblMonitoringStatus;
    @FXML
    private Button btnStartMonitoring;
    @FXML
    private Button btnStopMonitoring;
    @FXML
    private Button btnSimulateEvent;
    @FXML
    private Button btnBrowseFolder;
    @FXML
    private Button btnRefresh;
    @FXML
    private Button btnExport;
    @FXML
    private Button btnStatistics;
    @FXML
    private Button btnNotifications;
    @FXML
    private Button btnToggleTheme;
    @FXML
    private Button btnHelp;

    @FXML
    private Label lblTotalFiles;
    @FXML
    private Label lblSuspiciousFiles;
    @FXML
    private Label lblLastScan;
    @FXML
    private Label lblMonitoredFolders;
    @FXML
    private Label lblQuickSummary;

    @FXML
    private ProgressBar progressBar;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnSearch;
    @FXML
    private ComboBox<String> cmbEventType;
    @FXML
    private CheckBox chkSuspiciousOnly;

    @FXML
    private LineChart<String, Number> lineChartEvents;
    @FXML
    private PieChart pieChartTypes;
    @FXML
    private BarChart<String, Number> barChartSeverity;

    @FXML
    private TableView<FileEvent> tableEvents;
    @FXML
    private TableColumn<FileEvent, String> fileNameCol;
    @FXML
    private TableColumn<FileEvent, String> eventTypeCol;
    @FXML
    private TableColumn<FileEvent, String> timeCol;
    @FXML
    private TableColumn<FileEvent, String> hashCol;
    @FXML
    private TableColumn<FileEvent, String> statusCol;

    @FXML
    private VBox alertContainer;
    @FXML
    private Button btnClearAlerts;
    @FXML
    private Label statusLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Professional Dashboard Controller Initialized");
        // Initialize with default values
        lblMonitoringStatus.setText("MONITORING: STANDBY");
        statusLabel.setText("System ready");

        // Setup mock data or behavior if needed
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        btnSimulateEvent.setOnAction(e -> {
            System.out.println("Simulating event...");
            statusLabel.setText("Simulated event detected");
        });

        btnStartMonitoring.setOnAction(e -> {
            lblMonitoringStatus.setText("MONITORING: ACTIVE");
            lblMonitoringStatus.setStyle("-fx-text-fill: #16A34A;");
        });

        btnStopMonitoring.setOnAction(e -> {
            lblMonitoringStatus.setText("MONITORING: OFF");
            lblMonitoringStatus.setStyle("-fx-text-fill: #DC2626;");
        });
    }

    @FXML
    private void showQuickActions() {
        System.out.println("Quick actions triggered");
    }
}
