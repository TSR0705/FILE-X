package com.filex;

import com.filex.model.FileEvent;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestTable extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // Create a simple table view
        TableView<FileEvent> tableView = new TableView<>();
        
        // Create columns
        TableColumn<FileEvent, String> fileNameCol = new TableColumn<>("File Name");
        TableColumn<FileEvent, String> eventTypeCol = new TableColumn<>("Event Type");
        TableColumn<FileEvent, LocalDateTime> timeCol = new TableColumn<>("Time");
        TableColumn<FileEvent, String> hashCol = new TableColumn<>("Hash");
        TableColumn<FileEvent, Boolean> statusCol = new TableColumn<>("Status");
        
        // Set cell value factories
        fileNameCol.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        eventTypeCol.setCellValueFactory(new PropertyValueFactory<>("eventType"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        hashCol.setCellValueFactory(new PropertyValueFactory<>("sha256"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("suspicious"));
        
        // Add columns to table
        tableView.getColumns().addAll(fileNameCol, eventTypeCol, timeCol, hashCol, statusCol);
        
        // Create sample data
        ObservableList<FileEvent> data = FXCollections.observableArrayList();
        FileEvent event = new FileEvent();
        event.setFileName("test.txt");
        event.setEventType("Created");
        event.setTimestamp(LocalDateTime.now());
        event.setSha256("abc123");
        event.setSuspicious(false);
        data.add(event);
        
        // Set data to table
        tableView.setItems(data);
        
        // Create layout
        VBox root = new VBox(tableView);
        
        // Create scene and stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Test Table");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}