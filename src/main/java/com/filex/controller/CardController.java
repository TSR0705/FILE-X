package com.filex.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the Card view.
 * Manages individual dashboard cards showing summary statistics.
 * 
 * Future integration points:
 * - DatabaseService: Will provide statistical data
 * - MonitorService: Will provide real-time monitoring data
 */
public class CardController implements Initializable {

    @FXML
    private VBox cardContainer;
    
    @FXML
    private Label lblCardTitle;
    
    @FXML
    private Label lblCardValue;
    
    @FXML
    private Label lblCardDescription;
    
    private String cardType;
    
    /**
     * Initialize the controller
     * 
     * @param location The location used to resolve relative paths for the root object
     * @param resources The resources used to localize the root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Card initialization is done through configure method
    }
    
    /**
     * Configure the card with specific type and data
     * 
     * @param type The type of card (TotalFiles, SuspiciousFiles, Alerts, etc.)
     * @param title The title to display on the card
     * @param value The value to display on the card
     * @param description The description to display on the card
     */
    public void configure(String type, String title, String value, String description) {
        this.cardType = type;
        lblCardTitle.setText(title);
        lblCardValue.setText(value);
        lblCardDescription.setText(description);
    }
    
    /**
     * Update the card value
     * 
     * @param value The new value to display
     */
    public void updateValue(String value) {
        lblCardValue.setText(value);
    }
    
    /**
     * Update the card with integer value
     * 
     * @param value The new integer value to display
     */
    public void updateValue(int value) {
        lblCardValue.setText(String.valueOf(value));
    }
    
    /**
     * Get the card type
     * 
     * @return The type of this card
     */
    public String getCardType() {
        return cardType;
    }
}