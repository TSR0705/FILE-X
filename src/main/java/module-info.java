module com.filex {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.prefs;
    requires java.sql;
    
    opens com.filex to javafx.fxml;
    opens com.filex.controller to javafx.fxml;
    opens com.filex.model to javafx.base, javafx.fxml;
    opens com.filex.service to javafx.base;
    
    exports com.filex;
    exports com.filex.theme;
    exports com.filex.model;
    exports com.filex.controller;
    exports com.filex.util;
    exports com.filex.service;
}