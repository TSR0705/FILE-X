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

    private com.filex.service.MonitorService monitorService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Professional Dashboard Controller Initialized");
        
        // Initialize Services
        com.filex.service.DatabaseService.getInstance().initialize();
        this.monitorService = com.filex.service.MonitorService.getInstance();
        
        // Register for real-time updates
        monitorService.setOnFileEventListener(event -> {
            javafx.application.Platform.runLater(() -> {
                tableEvents.getItems().add(0, event);
                loadDashboardData(); // Refresh summary cards
                statusLabel.setText("New event: " + event.getFileName());
            });
        });
        
        // Load initial data from database
        loadDashboardData();
        
        // Initialize with default values
        lblMonitoringStatus.setText("MONITORING: STANDBY");
        statusLabel.setText("System ready");
    }

    /**
     * Fetch data from DatabaseService and update UI components.
     */
    private void loadDashboardData() {
        try {
            com.filex.service.DatabaseService dbService = com.filex.service.DatabaseService.getInstance();
            java.util.List<com.filex.model.FileEvent> events = dbService.getAllEvents();
            
            // Update summary labels
            lblTotalFiles.setText(String.valueOf(events.size()));
            
            long suspiciousCount = events.stream().filter(com.filex.model.FileEvent::isSuspicious).count();
            lblSuspiciousFiles.setText(String.valueOf(suspiciousCount));
            
            if (!events.isEmpty()) {
                lblLastScan.setText(events.get(0).getTimestamp().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            }
            
            // Populate table if empty (initial load)
            if (tableEvents.getItems().isEmpty()) {
                tableEvents.getItems().setAll(events);
            }
            
            // Setup table column cell factories
            fileNameCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("fileName"));
            eventTypeCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("eventType"));
            timeCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("timestamp"));
            hashCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("sha256"));
            statusCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("suspicious"));
            System.out.println("Dashboard data synchronized.");
        } catch (Exception e) {
            System.err.println("Error loading dashboard data: " + e.getMessage());
        }
    }

    @FXML
    private void handleSimulateEvent() {
        System.out.println("DEBUG: Simulate Event clicked");
        com.filex.model.FileEvent mock = new com.filex.model.FileEvent(
            "simulated_file_" + System.currentTimeMillis() + ".txt",
            "CREATE",
            java.time.LocalDateTime.now(),
            "sim_hash_" + java.util.UUID.randomUUID().toString().substring(0, 8),
            false
        );
        monitorService.addEvent(mock);
        statusLabel.setText("Simulated event detected");
    }

    @FXML
    private void handleStartMonitoring() {
        System.out.println("DEBUG: Start Monitoring clicked");
        String path = "C:/Users/ACER/OneDrive/Desktop/FILE-X/src"; // Default for testing
        monitorService.startMonitoring(path);
        lblMonitoringStatus.setText("MONITORING: ACTIVE");
        lblMonitoringStatus.setStyle("-fx-text-fill: #16A34A;");
        statusLabel.setText("Monitoring: " + path);
    }

    @FXML
    private void handleStopMonitoring() {
        System.out.println("DEBUG: Stop Monitoring clicked");
        monitorService.stopMonitoring();
        lblMonitoringStatus.setText("MONITORING: OFF");
        lblMonitoringStatus.setStyle("-fx-text-fill: #DC2626;");
        statusLabel.setText("Monitoring stopped.");
    }

    @FXML
    private void handleRefresh() {
        System.out.println("DEBUG: Refresh clicked");
        tableEvents.getItems().clear();
        loadDashboardData();
        statusLabel.setText("Data refreshed from database.");
    }

    @FXML
    private void handleSearch() {
        System.out.println("DEBUG: Search clicked");
        String query = txtSearch.getText().toLowerCase();
        if (query.isEmpty()) {
            loadDashboardData();
        } else {
            java.util.List<com.filex.model.FileEvent> filtered = tableEvents.getItems().stream()
                .filter(ev -> ev.getFileName().toLowerCase().contains(query))
                .collect(java.util.stream.Collectors.toList());
            tableEvents.getItems().setAll(filtered);
        }
        statusLabel.setText("Filtered " + tableEvents.getItems().size() + " events.");
    }

    @FXML
    private void handleBrowseFolder() {
        javafx.stage.DirectoryChooser directoryChooser = new javafx.stage.DirectoryChooser();
        directoryChooser.setTitle("Select Folder to Monitor");
        javafx.stage.Stage stage = (Stage) btnBrowseFolder.getScene().getWindow();
        java.io.File selectedDirectory = directoryChooser.showDialog(stage);
        
        if (selectedDirectory != null) {
            System.out.println("Selected folder: " + selectedDirectory.getAbsolutePath());
            statusLabel.setText("Target: " + selectedDirectory.getAbsolutePath());
            // Update monitoring path label if it existed (it's hardcoded in FXML right now)
        }
    }

    @FXML
    private void handleStatistics() {
        // Find main controller and navigate
        javafx.scene.Node current = btnStatistics;
        while (current != null) {
            if (current.getParent() != null && current.getScene() != null) {
                javafx.scene.Parent root = current.getScene().getRoot();
                if (root.getProperties().containsKey("controller")) {
                    MainController mainController = (MainController) root.getProperties().get("controller");
                    mainController.navigateTo("statistics");
                    return;
                }
            }
            current = current.getParent();
        }
    }

    @FXML
    private void showQuickActions() {
        System.out.println("Quick actions triggered");
    }
}
