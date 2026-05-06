package com.filex.util;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Utility class for JavaFX-specific operations.
 * Provides helper methods for UI updates and common JavaFX tasks.
 */
public class FXUtil {

    /**
     * Safely run UI updates from non-UI threads.
     * 
     * @param runnable The code to run on the UI thread
     */
    public static void runOnUiThread(Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        } else {
            Platform.runLater(runnable);
        }
    }

    /**
     * Show an alert dialog.
     * 
     * @param title The title of the alert
     * @param message The message to display
     */
    public static void showAlert(String title, String message) {
        runOnUiThread(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    /**
     * Show an error alert dialog.
     * 
     * @param title The title of the alert
     * @param message The error message to display
     */
    public static void showError(String title, String message) {
        runOnUiThread(() -> {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    /**
     * Show a confirmation dialog.
     * 
     * @param title The title of the alert
     * @param message The confirmation message to display
     * @param onConfirm The action to perform if the user confirms
     */
    public static void showConfirmation(String title, String message, Runnable onConfirm) {
        runOnUiThread(() -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            
            alert.showAndWait().ifPresent(response -> {
                if (response == javafx.scene.control.ButtonType.OK) {
                    onConfirm.run();
                }
            });
        });
    }
}