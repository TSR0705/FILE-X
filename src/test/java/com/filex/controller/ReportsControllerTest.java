package com.filex.controller;

import com.filex.model.FileEvent;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportsControllerTest {
    
    @Test
    void testReportItemCreation() {
        // Test that ReportItem can be created correctly
        ReportsController.ReportItem item = new ReportsController.ReportItem(
            "2023-01-01 12:00:00",
            "File Created",
            "test.txt",
            "Normal",
            "Test event"
        );
        
        assertEquals("2023-01-01 12:00:00", item.getDate());
        assertEquals("File Created", item.getEvent());
        assertEquals("test.txt", item.getFile());
        assertEquals("Normal", item.getSeverity());
        assertEquals("Test event", item.getDetails());
    }
}