package com.filex.controller;

import com.filex.util.AnimationUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the Help view.
 * Manages the display of help content and user guide.
 */
public class HelpController implements Initializable {

    @FXML
    private VBox helpContainer;
    
    @FXML
    private TextField searchField;
    
    // Support buttons
    @FXML
    private Button emailButton;
    
    @FXML
    private Button docsButton;
    
    /**
     * Initialize the controller
     * 
     * @param location The location used to resolve relative paths for the root object
     * @param resources The resources used to localize the root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set up button actions
        setupButtonActions();
        
        // Apply initial animation
        AnimationUtil.fadeInNode(helpContainer, 0.5);
    }
    
    /**
     * Set up button actions
     */
    private void setupButtonActions() {
        // Set up support button actions
        if (emailButton != null) {
            emailButton.setOnAction(event -> {
                System.out.println("Email support button clicked");
                AnimationUtil.pulseNode(emailButton, 1, 0.2);
                // In a real implementation, this would open the default email client
            });
        }
        
        if (docsButton != null) {
            docsButton.setOnAction(event -> {
                System.out.println("Documentation button clicked");
                AnimationUtil.pulseNode(docsButton, 1, 0.2);
                // In a real implementation, this would open the documentation in a browser
            });
        }
        
        // Set up search functionality
        if (searchField != null) {
            searchField.setOnAction(event -> {
                String query = searchField.getText();
                if (query != null && !query.trim().isEmpty()) {
                    // Perform search
                    performSearch(query);
                }
            });
        }
    }
    
    /**
     * Perform search in help content
     * 
     * @param query The search query
     */
    private void performSearch(String query) {
        // In a real implementation, this would search through help content
        System.out.println("Searching for: " + query);
        // Visual feedback for search
        if (searchField != null) {
            AnimationUtil.pulseNode(searchField, 1, 0.3);
        }
    }
}