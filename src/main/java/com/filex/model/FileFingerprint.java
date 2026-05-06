package com.filex.model;

import java.time.LocalDateTime;

/**
 * Model class representing a file fingerprint.
 * Captures details about file integrity using SHA-256 hashing.
 */
public class FileFingerprint {
    private int id;
    private String filePath;
    private String sha256;
    private long size;
    private LocalDateTime lastSeen;
    private LocalDateTime createdAt;

    /**
     * Default constructor
     */
    public FileFingerprint() {
    }

    /**
     * Constructor with all fields
     * 
     * @param id The unique identifier
     * @param filePath The file path
     * @param sha256 The SHA256 hash of the file
     * @param size The file size in bytes
     * @param lastSeen The last time the file was seen
     * @param createdAt The creation timestamp
     */
    public FileFingerprint(int id, String filePath, String sha256, long size, LocalDateTime lastSeen, LocalDateTime createdAt) {
        this.id = id;
        this.filePath = filePath;
        this.sha256 = sha256;
        this.size = size;
        this.lastSeen = lastSeen;
        this.createdAt = createdAt;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "FileFingerprint{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", sha256='" + sha256 + '\'' +
                ", size=" + size +
                ", lastSeen=" + lastSeen +
                ", createdAt=" + createdAt +
                '}';
    }
}