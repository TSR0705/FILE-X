-- Create database
CREATE DATABASE IF NOT EXISTS filex_db;

-- Use the database
USE filex_db;

-- Create file_events table
CREATE TABLE IF NOT EXISTS file_events (
    id INT AUTO_INCREMENT PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    event_type VARCHAR(50) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    sha256 VARCHAR(64),
    suspicious BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create alerts table
CREATE TABLE IF NOT EXISTS alerts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    file_event_id INT,
    severity VARCHAR(20) NOT NULL,
    acknowledged BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    actions_taken TEXT,
    FOREIGN KEY (file_event_id) REFERENCES file_events(id)
);

-- Insert some sample data for testing
INSERT INTO file_events (file_name, event_type, timestamp, sha256, suspicious) VALUES 
('test_document.pdf', 'ENTRY_CREATE', NOW(), 'abc123def456', FALSE),
('confidential_data.xlsx', 'ENTRY_CREATE', NOW(), 'xyz789uvw012', TRUE),
('readme.txt', 'ENTRY_MODIFY', NOW(), 'def456ghi789', FALSE);

INSERT INTO alerts (file_event_id, severity, acknowledged, actions_taken) VALUES 
(2, 'HIGH', FALSE, 'Suspicious file created');

-- Verify the data
SELECT * FROM file_events;
SELECT * FROM alerts;