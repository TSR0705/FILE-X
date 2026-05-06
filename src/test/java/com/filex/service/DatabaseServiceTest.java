package com.filex.service;

import com.filex.model.FileEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseServiceTest {
    
    private DatabaseService databaseService;
    
    @BeforeEach
    void setUp() {
        databaseService = DatabaseService.getInstance();
        databaseService.initialize();
    }
    
    @AfterEach
    void tearDown() {
        databaseService.close();
    }
    
    @Test
    void testGetEventsByDateRange() {
        // Test that the method exists and can be called
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);
        
        // This should not throw an exception
        List<FileEvent> events = databaseService.getEventsByDateRange(startDate, endDate);
        
        // We can't assert specific counts without knowing the database state,
        // but we can verify the method works
        assertNotNull(events);
    }
}