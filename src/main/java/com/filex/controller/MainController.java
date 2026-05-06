package com.filex.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

/**
 * Main controller class that manages navigation and layout.
 */
public class MainController {

    @FXML
    private BorderPane root;

    @FXML
    private StackPane contentPane;

    /**
     * Navigate to a different view.
     * 
     * @param viewName The name of the view to navigate to.
     */
    public void navigateTo(String viewName) {
        try {
            String fxmlFile = "";
            switch (viewName) {
                case "dashboard":
                    fxmlFile = "/fxml/ProfessionalDashboard.fxml";
                    break;
                case "logs":
                    fxmlFile = "/fxml/Logs.fxml";
                    break;
                case "alerts":
                    fxmlFile = "/fxml/Alerts.fxml";
                    break;
                case "settings":
                    fxmlFile = "/fxml/Settings.fxml";
                    break;
                case "help":
                    fxmlFile = "/fxml/Help.fxml";
                    break;
                case "reports":
                    fxmlFile = "/fxml/Reports.fxml";
                    break;
                case "statistics":
                    fxmlFile = "/fxml/Statistics.fxml";
                    break;
                default:
                    fxmlFile = "/fxml/ProfessionalDashboard.fxml";
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent view = loader.load();
            
            // Set the new view in the content pane
            contentPane.getChildren().setAll(view);
            
            System.out.println("Navigated to: " + viewName);
        } catch (IOException e) {
            System.err.println("Error loading view: " + viewName);
            e.printStackTrace();
        }
    }
}
