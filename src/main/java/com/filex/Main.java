package com.filex;

import com.filex.controller.MainController;
import com.filex.service.MonitorService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application class for FileX - Insider File Leak Detector.
 * This is the JavaFX version of the application.
 */
public class Main extends Application {
    
    private MonitorService monitorService;
    
    /**
     * Start method - entry point for the JavaFX application.
     * 
     * @param primaryStage The primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the main FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        Parent root = loader.load();
        
        // Get the controller and set it as a property
        MainController controller = loader.getController();
        root.getProperties().put("controller", controller);
        
        // Create the scene with a preferred size
        Scene scene = new Scene(root, 1400, 900);
        
        // Add CSS stylesheets
        scene.getStylesheets().add(getClass().getResource("/css/theme-light.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/css/animation.css").toExternalForm());
        
        // Apply theme transition
        scene.getRoot().setStyle("-fx-transition: all 300ms ease-in-out;");
        
        // Configure the stage
        primaryStage.setTitle("FileX - Insider File Leak Detector");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(800);
        primaryStage.show();
    }
    
    /**
     * Stop method - called when the application is about to exit.
     */
    @Override
    public void stop() {
        System.out.println("Application is stopping...");
        // Clean up resources
        System.out.println("Application stopped.");
    }
    
    /**
     * Main method - entry point for the application.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // Set system properties for better UI
        System.setProperty("prism.lcdtext", "false");
        System.setProperty("prism.text", "t2k");
        
        // Launch the JavaFX application
        launch(args);
    }
}