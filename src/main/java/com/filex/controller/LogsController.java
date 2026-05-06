package com.filex.controller;

import com.filex.model.FileEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import java.net.URL;
import java.util.ResourceBundle;

public class LogsController implements Initializable {
    @FXML private TableView<FileEvent> tableLogs;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Logs Controller Initialized");
    }
}
