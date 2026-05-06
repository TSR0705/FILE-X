package com.filex.util;

/**
 * Configuration class for database settings.
 */
public class DatabaseConfig {
    // Database configuration for SQLite
    public static final String DB_URL = "jdbc:sqlite:filex.db";
    
    // Connection pool settings
    public static final int MAX_POOL_SIZE = 10;
    public static final int MIN_POOL_SIZE = 2;
    public static final long CONNECTION_TIMEOUT = 30000; // 30 seconds
}