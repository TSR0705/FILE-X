package com.filex.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for database settings.
 * Loads values from .env file at startup.
 */
public class DatabaseConfig {
    public static String DB_URL;
    public static String DB_USER;
    public static String DB_PASSWORD;
    
    public static final int MAX_POOL_SIZE = 10;
    public static final int MIN_POOL_SIZE = 2;
    public static final long CONNECTION_TIMEOUT = 30000; // 30 seconds

    static {
        loadEnv();
    }

    /**
     * Simple parser for .env file.
     */
    private static void loadEnv() {
        Map<String, String> env = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(".env"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    env.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Warning: Could not load .env file. Using defaults.");
        }

        DB_URL = env.getOrDefault("DB_URL", "jdbc:mysql://localhost:3306/filex_db?useSSL=false&serverTimezone=UTC");
        DB_USER = env.getOrDefault("DB_USER", "root");
        DB_PASSWORD = env.getOrDefault("DB_PASSWORD", "");
    }
}