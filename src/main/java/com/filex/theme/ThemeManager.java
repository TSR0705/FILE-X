package com.filex.theme;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.prefs.Preferences;

/**
 * Manages the application theme by providing color variables and theme switching functionality.
 * Supports both light and dark themes with smooth transitions and persistent settings.
 */
public class ThemeManager {
    
    public enum ThemeType {
        LIGHT, DARK
    }
    
    private static ThemeType currentTheme = ThemeType.LIGHT;
    private static final Preferences prefs = Preferences.userNodeForPackage(ThemeManager.class);
    private static final String THEME_PREF_KEY = "selected_theme";
    
    // New color scheme constants
    public static final String BG_COLOR_LIGHT = "#F5F7FA";
    public static final String BG_COLOR_DARK = "#0F0F17";
    public static final String SIDEBAR_BG_LIGHT = "#EAECEE";
    public static final String SIDEBAR_BG_DARK = "#181825";
    public static final String CARD_BG_LIGHT = "#FFFFFF";
    public static final String CARD_BG_DARK = "#1F1F2E";
    public static final String TEXT_PRIMARY_LIGHT = "#1A1A1A";
    public static final String TEXT_PRIMARY_DARK = "#E5E5E5";
    public static final String TEXT_SECONDARY_LIGHT = "#60656D";
    public static final String TEXT_SECONDARY_DARK = "#B0B0B0";
    public static final String PRIMARY_LIGHT = "#0078D4";
    public static final String PRIMARY_DARK = "#3399FF";
    public static final String ACCENT_LIGHT = "#00B8A9";
    public static final String ACCENT_DARK = "#00D4C7";
    public static final String BORDER_LIGHT = "#DADCE0";
    public static final String BORDER_DARK = "#2A2A35";
    public static final String SHADOW_LIGHT = "rgba(0, 0, 0, 0.05)";
    public static final String SHADOW_DARK = "rgba(0, 0, 0, 0.3)";
    public static final String HOVER_LIGHT = "rgba(0,120,212,0.08)";
    public static final String HOVER_DARK = "rgba(51,153,255,0.1)";
    public static final String ALERT_LIGHT = "#D32F2F";
    public static final String ALERT_DARK = "#EF4444";
    public static final String SUCCESS_LIGHT = "#388E3C";
    public static final String SUCCESS_DARK = "#34D399";
    
    /**
     * Initialize the theme manager and load the saved theme preference
     */
    public static void initialize() {
        // Load saved theme preference
        String savedTheme = prefs.get(THEME_PREF_KEY, ThemeType.LIGHT.name());
        try {
            currentTheme = ThemeType.valueOf(savedTheme);
        } catch (IllegalArgumentException e) {
            currentTheme = ThemeType.LIGHT;
        }
    }
    
    /**
     * Gets the primary color for the current theme.
     * 
     * @return The primary color as a hex string
     */
    public static String getPrimaryColor() {
        return currentTheme == ThemeType.LIGHT ? PRIMARY_LIGHT : PRIMARY_DARK;
    }
    
    /**
     * Gets the secondary color for the current theme.
     * 
     * @return The secondary color as a hex string
     */
    public static String getSecondaryColor() {
        return currentTheme == ThemeType.LIGHT ? ACCENT_LIGHT : ACCENT_DARK;
    }
    
    /**
     * Gets the background color for the current theme.
     * 
     * @return The background color as a hex string
     */
    public static String getBackgroundColor() {
        return currentTheme == ThemeType.LIGHT ? BG_COLOR_LIGHT : BG_COLOR_DARK;
    }
    
    /**
     * Gets the card background color for the current theme.
     * 
     * @return The card background color as a hex string
     */
    public static String getCardBackgroundColor() {
        return currentTheme == ThemeType.LIGHT ? CARD_BG_LIGHT : CARD_BG_DARK;
    }
    
    /**
     * Gets the sidebar background color for the current theme.
     * 
     * @return The sidebar background color as a hex string
     */
    public static String getSidebarBackgroundColor() {
        return currentTheme == ThemeType.LIGHT ? SIDEBAR_BG_LIGHT : SIDEBAR_BG_DARK;
    }
    
    /**
     * Gets the primary text color for the current theme.
     * 
     * @return The primary text color as a hex string
     */
    public static String getPrimaryTextColor() {
        return currentTheme == ThemeType.LIGHT ? TEXT_PRIMARY_LIGHT : TEXT_PRIMARY_DARK;
    }
    
    /**
     * Gets the secondary text color for the current theme.
     * 
     * @return The secondary text color as a hex string
     */
    public static String getSecondaryTextColor() {
        return currentTheme == ThemeType.LIGHT ? TEXT_SECONDARY_LIGHT : TEXT_SECONDARY_DARK;
    }
    
    /**
     * Gets the border color for the current theme.
     * 
     * @return The border color as a hex string
     */
    public static String getBorderColor() {
        return currentTheme == ThemeType.LIGHT ? BORDER_LIGHT : BORDER_DARK;
    }
    
    /**
     * Gets the shadow color for the current theme.
     * 
     * @return The shadow color as a rgba string
     */
    public static String getShadowColor() {
        return currentTheme == ThemeType.LIGHT ? SHADOW_LIGHT : SHADOW_DARK;
    }
    
    /**
     * Gets the hover color for the current theme.
     * 
     * @return The hover color as a rgba string
     */
    public static String getHoverColor() {
        return currentTheme == ThemeType.LIGHT ? HOVER_LIGHT : HOVER_DARK;
    }
    
    /**
     * Gets the alert color for the current theme.
     * 
     * @return The alert color as a hex string
     */
    public static String getAlertColor() {
        return currentTheme == ThemeType.LIGHT ? ALERT_LIGHT : ALERT_DARK;
    }
    
    /**
     * Gets the success color for the current theme.
     * 
     * @return The success color as a hex string
     */
    public static String getSuccessColor() {
        return currentTheme == ThemeType.LIGHT ? SUCCESS_LIGHT : SUCCESS_DARK;
    }
    
    /**
     * Apply a specific theme to the scene with a smooth transition.
     * 
     * @param scene The scene to apply the theme to
     * @param theme The theme to apply
     */
    public static void applyTheme(Scene scene, ThemeType theme) {
        // Apply fade transition for smooth theme switching
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), scene.getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        
        fadeOut.setOnFinished(e -> {
            // Remove all existing theme stylesheets
            scene.getStylesheets().removeIf(s -> s.contains("theme-light.css") || s.contains("theme-dark.css"));
            
            // Add the new theme stylesheet
            String themePath;
            if (theme == ThemeType.LIGHT) {
                themePath = ThemeManager.class.getResource("/css/theme-light.css").toExternalForm();
            } else {
                themePath = ThemeManager.class.getResource("/css/theme-dark.css").toExternalForm();
            }
            
            scene.getStylesheets().add(themePath);
            currentTheme = theme;
            
            // Save the theme preference
            prefs.put(THEME_PREF_KEY, theme.name());
            
            // Fade back in
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), scene.getRoot());
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        
        fadeOut.play();
    }
    
    /**
     * Toggle between light and dark themes.
     * 
     * @param scene The scene to toggle the theme for
     */
    public static void toggleTheme(Scene scene) {
        ThemeType newTheme = currentTheme == ThemeType.LIGHT ? ThemeType.DARK : ThemeType.LIGHT;
        applyTheme(scene, newTheme);
    }
    
    /**
     * Get the current theme type.
     * 
     * @return The current theme type
     */
    public static ThemeType getCurrentTheme() {
        return currentTheme;
    }
    
    /**
     * Set the current theme type.
     * 
     * @param theme The theme type to set
     */
    public static void setCurrentTheme(ThemeType theme) {
        currentTheme = theme;
    }
    
    /**
     * Prints all theme colors to the console.
     */
    public static void printThemeColors() {
        System.out.println("Current Theme: " + (currentTheme == ThemeType.LIGHT ? "Light" : "Dark"));
        System.out.println("Theme Colors:");
        System.out.println("- Primary: " + getPrimaryColor());
        System.out.println("- Secondary: " + getSecondaryColor());
        System.out.println("- Background: " + getBackgroundColor());
        System.out.println("- Sidebar Background: " + getSidebarBackgroundColor());
        System.out.println("- Card Background: " + getCardBackgroundColor());
        System.out.println("- Text Primary: " + getPrimaryTextColor());
        System.out.println("- Text Secondary: " + getSecondaryTextColor());
        System.out.println("- Border: " + getBorderColor());
        System.out.println("- Shadow: " + getShadowColor());
        System.out.println("- Hover: " + getHoverColor());
        System.out.println("- Alert: " + getAlertColor());
        System.out.println("- Success: " + getSuccessColor());
    }
}