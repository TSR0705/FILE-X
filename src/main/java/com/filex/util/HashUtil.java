package com.filex.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for computing file hashes.
 */
public class HashUtil {
    
    /**
     * Compute the SHA-256 hash of a file.
     * 
     * @param filePath The path to the file
     * @return The SHA-256 hash as a hexadecimal string, or empty string if file doesn't exist
     */
    public static String computeSHA256(String filePath) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            FileInputStream fis = new FileInputStream(filePath);
            
            byte[] byteArray = new byte[1024];
            int bytesCount = 0;
            
            while ((bytesCount = fis.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesCount);
            }
            
            fis.close();
            
            byte[] bytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            
            return sb.toString();
        } catch (IOException | NoSuchAlgorithmException e) {
            System.err.println("Error computing SHA-256 hash for file: " + filePath);
            e.printStackTrace();
            return "";
        }
    }
}