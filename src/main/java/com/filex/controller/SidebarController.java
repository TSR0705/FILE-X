package com.filex.controller;

import com.filex.theme.ThemeManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the Sidebar view.
 * Manages navigation links, sidebar expansion/collapse, and theme switching.
 * 
 * Future integration points:
 * - NavigationService: Will handle view transitions
 * - UserService: Will provide user-specific navigation options
 */
public class SidebarController implements Initializable {

    @FXML
    private VBox sidebarRoot;
    
    @FXML
    private HBox logoBox;
    
    @FXML
    private Label logoText;
    
    @FXML
    private VBox navList;
    
    @FXML
    private HBox dashboardNavItem;
    
    @FXML
    private HBox logsNavItem;
    
    @FXML
    private HBox alertsNavItem;
    
    @FXML
    private HBox reportsNavItem;
    
    @FXML
    private HBox statisticsNavItem;
    
    @FXML
    private HBox helpNavItem;
    
    @FXML
    private HBox settingsNavItem;
    
    @FXML
    private VBox utilities;
    
    @FXML
    private Button themeToggleBtn;
    
    @FXML
    private SVGPath themeIcon;
    
    @FXML
    private Button collapseBtn;
    
    @FXML
    private SVGPath collapseIcon;
    
    private boolean isCollapsed = false;
    private HBox selectedNavItem;
    
    // Add a field to track the last navigation time
    private long lastNavigationTime = 0;
    private static final long NAVIGATION_DELAY = 300; // 300ms delay between navigation
    
    /**
     * Initialize the controller
     * 
     * @param location The location used to resolve relative paths for the root object
     * @param resources The resources used to localize the root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set up button actions
        setupButtonActions();
        
        // Set up navigation item selection
        setupNavigationItems();
        
        // Set default selection
        selectNav("dashboard");
        
        // Apply initial animation
        sidebarRoot.setOpacity(0);
        Timeline fadeIn = new Timeline(new KeyFrame(Duration.millis(300), e -> sidebarRoot.setOpacity(1)));
        fadeIn.play();
        
        // Add keyboard navigation support for the entire sidebar
        setupSidebarKeyboardNavigation();
    }
    
    /**
     * Set up keyboard navigation for the entire sidebar
     */
    private void setupSidebarKeyboardNavigation() {
        // Add focus traversal support
        sidebarRoot.setFocusTraversable(true);
        
        // Handle key presses on the sidebar root
        sidebarRoot.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    // Move focus to previous navigation item
                    moveFocusUp();
                    break;
                case DOWN:
                    // Move focus to next navigation item
                    moveFocusDown();
                    break;
                case ENTER:
                case SPACE:
                    // Activate currently focused item
                    activateFocusedItem();
                    break;
                case TAB:
                    // Let tab behave normally for focus traversal
                    break;
                default:
                    // Handle specific key shortcuts
                    handleKeyShortcut(event.getCode());
                    break;
            }
        });
    }
    
    /**
     * Move focus to the previous navigation item
     */
    private void moveFocusUp() {
        if (selectedNavItem == null) {
            // If nothing is selected, focus the first item
            dashboardNavItem.requestFocus();
            return;
        }
        
        // Determine the previous item
        if (selectedNavItem == dashboardNavItem) {
            settingsNavItem.requestFocus();
        } else if (selectedNavItem == logsNavItem) {
            dashboardNavItem.requestFocus();
        } else if (selectedNavItem == alertsNavItem) {
            logsNavItem.requestFocus();
        } else if (selectedNavItem == reportsNavItem) {
            alertsNavItem.requestFocus();
        } else if (selectedNavItem == statisticsNavItem) {
            reportsNavItem.requestFocus();
        } else if (selectedNavItem == helpNavItem) {
            statisticsNavItem.requestFocus();
        } else if (selectedNavItem == settingsNavItem) {
            helpNavItem.requestFocus();
        }
    }
    
    /**
     * Move focus to the next navigation item
     */
    private void moveFocusDown() {
        if (selectedNavItem == null) {
            // If nothing is selected, focus the first item
            dashboardNavItem.requestFocus();
            return;
        }
        
        // Determine the next item
        if (selectedNavItem == dashboardNavItem) {
            logsNavItem.requestFocus();
        } else if (selectedNavItem == logsNavItem) {
            alertsNavItem.requestFocus();
        } else if (selectedNavItem == alertsNavItem) {
            reportsNavItem.requestFocus();
        } else if (selectedNavItem == reportsNavItem) {
            statisticsNavItem.requestFocus();
        } else if (selectedNavItem == statisticsNavItem) {
            helpNavItem.requestFocus();
        } else if (selectedNavItem == helpNavItem) {
            settingsNavItem.requestFocus();
        } else if (selectedNavItem == settingsNavItem) {
            dashboardNavItem.requestFocus();
        }
    }
    
    /**
     * Activate the currently focused navigation item
     */
    private void activateFocusedItem() {
        // Get the currently focused node
        javafx.scene.Node focusedNode = sidebarRoot.getScene().getFocusOwner();
        
        // Check if it's one of our navigation items
        if (focusedNode instanceof HBox) {
            HBox focusedItem = (HBox) focusedNode;
            if (focusedItem == dashboardNavItem) {
                selectNav("dashboard");
                animateNavItem(dashboardNavItem);
            } else if (focusedItem == logsNavItem) {
                selectNav("logs");
                animateNavItem(logsNavItem);
            } else if (focusedItem == alertsNavItem) {
                selectNav("alerts");
                animateNavItem(alertsNavItem);
            } else if (focusedItem == reportsNavItem) {
                selectNav("reports");
                animateNavItem(reportsNavItem);
            } else if (focusedItem == statisticsNavItem) {
                selectNav("statistics");
                animateNavItem(statisticsNavItem);
            } else if (focusedItem == helpNavItem) {
                selectNav("help");
                animateNavItem(helpNavItem);
            } else if (focusedItem == settingsNavItem) {
                selectNav("settings");
                animateNavItem(settingsNavItem);
            }
        }
    }
    
    /**
     * Handle key shortcuts for navigation
     */
    private void handleKeyShortcut(javafx.scene.input.KeyCode keyCode) {
        switch (keyCode) {
            case D:
                selectNav("dashboard");
                animateNavItem(dashboardNavItem);
                dashboardNavItem.requestFocus();
                break;
            case L:
                selectNav("logs");
                animateNavItem(logsNavItem);
                logsNavItem.requestFocus();
                break;
            case A:
                selectNav("alerts");
                animateNavItem(alertsNavItem);
                alertsNavItem.requestFocus();
                break;
            case R:
                selectNav("reports");
                animateNavItem(reportsNavItem);
                reportsNavItem.requestFocus();
                break;
            case S:
                selectNav("statistics");
                animateNavItem(statisticsNavItem);
                statisticsNavItem.requestFocus();
                break;
            case T:
                selectNav("settings");
                animateNavItem(settingsNavItem);
                settingsNavItem.requestFocus();
                break;
            case H:
                selectNav("help");
                animateNavItem(helpNavItem);
                helpNavItem.requestFocus();
                break;
        }
    }
    
    /**
     * Set up button actions for navigation
     */
    private void setupButtonActions() {
        dashboardNavItem.setOnMouseClicked(event -> {
            selectNav("dashboard");
            animateNavItem(dashboardNavItem);
        });
        
        logsNavItem.setOnMouseClicked(event -> {
            selectNav("logs");
            animateNavItem(logsNavItem);
        });
        
        alertsNavItem.setOnMouseClicked(event -> {
            selectNav("alerts");
            animateNavItem(alertsNavItem);
        });
        
        reportsNavItem.setOnMouseClicked(event -> {
            selectNav("reports");
            animateNavItem(reportsNavItem);
        });
        
        statisticsNavItem.setOnMouseClicked(event -> {
            selectNav("statistics");
            animateNavItem(statisticsNavItem);
        });
        
        helpNavItem.setOnMouseClicked(event -> {
            selectNav("help");
            animateNavItem(helpNavItem);
        });
        
        settingsNavItem.setOnMouseClicked(event -> {
            selectNav("settings");
            animateNavItem(settingsNavItem);
        });
        
        themeToggleBtn.setOnAction(event -> {
            toggleTheme();
            animateButton(themeToggleBtn);
        });
        
        collapseBtn.setOnAction(event -> {
            toggleCollapse();
            animateButton(collapseBtn);
        });
    }
    
    /**
     * Set up navigation items with keyboard support
     */
    private void setupNavigationItems() {
        // Add keyboard navigation support for each item
        setupNavItemKeyboardSupport(dashboardNavItem, "dashboard");
        setupNavItemKeyboardSupport(logsNavItem, "logs");
        setupNavItemKeyboardSupport(alertsNavItem, "alerts");
        setupNavItemKeyboardSupport(reportsNavItem, "reports");
        setupNavItemKeyboardSupport(statisticsNavItem, "statistics");
        setupNavItemKeyboardSupport(helpNavItem, "help");
        setupNavItemKeyboardSupport(settingsNavItem, "settings");
        
        // Add hover effects
        addHoverEffect(dashboardNavItem);
        addHoverEffect(logsNavItem);
        addHoverEffect(alertsNavItem);
        addHoverEffect(reportsNavItem);
        addHoverEffect(statisticsNavItem);
        addHoverEffect(helpNavItem);
        addHoverEffect(settingsNavItem);
    }
    
    /**
     * Set up keyboard support for a navigation item
     */
    private void setupNavItemKeyboardSupport(HBox item, String viewName) {
        item.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                case SPACE:
                    selectNav(viewName);
                    animateNavItem(item);
                    break;
                case UP:
                    moveFocusUp();
                    break;
                case DOWN:
                    moveFocusDown();
                    break;
                default:
                    // Handle specific key shortcuts
                    handleKeyShortcut(event.getCode());
                    break;
            }
        });
        
        // Make items focus traversable
        item.setFocusTraversable(true);
    }
    
    /**
     * Add hover effect to navigation item
     * 
     * @param item The navigation item to add hover effect to
     */
    private void addHoverEffect(HBox item) {
        // Store original values
        double originalTranslateY = item.getTranslateY();
        
        item.setOnMouseEntered(e -> {
            item.setTranslateY(originalTranslateY - 2);
            item.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 8, 0, 0, 2);");
        });
        
        item.setOnMouseExited(e -> {
            item.setTranslateY(originalTranslateY);
            item.setStyle("");
        });
    }
    
    /**
     * Navigate to a specific view
     * 
     * @param viewName The name of the view to navigate to
     */
    private void selectNav(String viewName) {
        // Prevent rapid navigation clicks
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastNavigationTime < NAVIGATION_DELAY) {
            return;
        }
        lastNavigationTime = currentTime;
        
        // Clear previous selection
        if (selectedNavItem != null) {
            selectedNavItem.getStyleClass().remove("selected");
        }
        
        // Set new selection
        switch (viewName) {
            case "dashboard":
                selectedNavItem = dashboardNavItem;
                break;
            case "logs":
                selectedNavItem = logsNavItem;
                break;
            case "alerts":
                selectedNavItem = alertsNavItem;
                break;
            case "reports":
                selectedNavItem = reportsNavItem;
                break;
            case "statistics":
                selectedNavItem = statisticsNavItem;
                break;
            case "help":
                selectedNavItem = helpNavItem;
                break;
            case "settings":
                selectedNavItem = settingsNavItem;
                break;
            default:
                selectedNavItem = dashboardNavItem;
                break;
        }
        
        // Apply selection style
        selectedNavItem.getStyleClass().add("selected");
        
        // Navigate to the view
        navigateToView(viewName);
    }
    
    /**
     * Navigate to a specific view
     * 
     * @param viewName The name of the view to navigate to
     */
    private void navigateToView(String viewName) {
        // Get the main controller through the parent hierarchy
        try {
            // Try to find the main controller from the scene root
            if (sidebarRoot.getScene() != null) {
                // Get the root of the scene
                javafx.scene.Parent root = sidebarRoot.getScene().getRoot();
                
                // If the root has the controller property, use it
                if (root.getProperties().containsKey("controller")) {
                    MainController mainController = (MainController) root.getProperties().get("controller");
                    if (mainController != null) {
                        mainController.navigateTo(viewName);
                        return;
                    }
                }
                
                // Alternative approach: traverse the parent hierarchy to find MainController
                javafx.scene.Node current = sidebarRoot;
                while (current != null) {
                    if (current instanceof javafx.scene.layout.BorderPane) {
                        // This is likely the main border pane
                        BorderPane borderPane = (BorderPane) current;
                        if (borderPane.getCenter() instanceof javafx.scene.layout.StackPane) {
                            // The center should be our content pane
                            // Try to get the controller from the FXML loader or properties
                            break;
                        }
                    }
                    current = current.getParent();
                }
            }
            
            // Fallback: try to get controller from the main application
            // This requires the Main class to set the controller property
            System.out.println("Navigating to: " + viewName);
        } catch (Exception e) {
            System.err.println("Error navigating to view: " + viewName);
            e.printStackTrace();
        }
    }
    
    /**
     * Toggle between light and dark themes
     */
    public void toggleTheme() {
        // Get the scene from any node
        if (sidebarRoot.getScene() != null) {
            ThemeManager.toggleTheme(sidebarRoot.getScene());
            
            // Update the theme icon
            if (ThemeManager.getCurrentTheme() == ThemeManager.ThemeType.LIGHT) {
                // Moon icon for dark theme
                themeIcon.setContent("M12 3c-4.97 0-9 4.03-9 9s4.03 9 9 9 9-4.03 9-9c0-.46-.04-.92-.1-1.36-.98 1.37-2.58 2.26-4.4 2.26-2.98 0-5.4-2.42-5.4-5.4 0-1.81.89-3.42 2.26-4.4-.44-.06-.9-.1-1.36-.1z");
            } else {
                // Sun icon for light theme
                themeIcon.setContent("M12 7c-2.76 0-5 2.24-5 5s2.24 5 5 5 5-2.24 5-5-2.24-5-5-5zM2 13h2c.55 0 1-.45 1-1s-.45-1-1-1H2c-.55 0-1 .45-1 1s.45 1 1 1zm18 0h2c.55 0 1-.45 1-1s-.45-1-1-1h-2c-.55 0-1 .45-1 1s.45 1 1 1zm11 2v2c0 .55.45 1 1 1s1-.45 1-1v-2c0-.55-.45-1-1-1s-1 .45-1 1zm0-18v2c0 .55.45 1 1 1s1-.45 1-1v-2c0-.55-.45-1-1-1s-1 .45-1 1z");
            }
        }
    }
    
    /**
     * Toggle sidebar collapse/expand state
     */
    public void toggleCollapse() {
        isCollapsed = !isCollapsed;
        
        // Animate the sidebar width
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(300), event -> {
            if (isCollapsed) {
                sidebarRoot.getStyleClass().add("collapsed");
                // Fade out labels
                fadeOutLabels();
                // Update collapse icon to expand icon
                collapseIcon.setContent("M10 6L8.59 7.41 13.17 12l-4.58 4.59L10 18l6-6z");
            } else {
                sidebarRoot.getStyleClass().remove("collapsed");
                // Fade in labels
                fadeInLabels();
                // Update collapse icon to collapse icon
                collapseIcon.setContent("M15.41 7.41L14 6l-6 6 6 6 1.41-1.41L10.83 12z");
            }
        }));
        timeline.play();
    }
    
    /**
     * Fade out labels when collapsing sidebar
     */
    private void fadeOutLabels() {
        // Get all labels in the sidebar
        logoText.setOpacity(0);
        
        // Fade out nav item labels
        dashboardNavItem.getChildren().filtered(node -> node instanceof Label)
                .forEach(node -> ((Label) node).setOpacity(0));
        
        logsNavItem.getChildren().filtered(node -> node instanceof Label)
                .forEach(node -> ((Label) node).setOpacity(0));
        
        alertsNavItem.getChildren().filtered(node -> node instanceof Label)
                .forEach(node -> ((Label) node).setOpacity(0));
        
        settingsNavItem.getChildren().filtered(node -> node instanceof Label)
                .forEach(node -> ((Label) node).setOpacity(0));
    }
    
    /**
     * Fade in labels when expanding sidebar
     */
    private void fadeInLabels() {
        // Get all labels in the sidebar
        logoText.setOpacity(1);
        
        // Fade in nav item labels
        dashboardNavItem.getChildren().filtered(node -> node instanceof Label)
                .forEach(node -> ((Label) node).setOpacity(1));
        
        logsNavItem.getChildren().filtered(node -> node instanceof Label)
                .forEach(node -> ((Label) node).setOpacity(1));
        
        alertsNavItem.getChildren().filtered(node -> node instanceof Label)
                .forEach(node -> ((Label) node).setOpacity(1));
        
        settingsNavItem.getChildren().filtered(node -> node instanceof Label)
                .forEach(node -> ((Label) node).setOpacity(1));
    }
    
    /**
     * Animate navigation item on click
     * 
     * @param item The navigation item to animate
     */
    public void animateNavItem(HBox item) {
        // Store original scale values
        double originalScaleX = item.getScaleX();
        double originalScaleY = item.getScaleY();
        
        // Check if animation is already running
        if (item.getUserData() != null && item.getUserData().equals("animating")) {
            return;
        }
        
        // Mark as animating
        item.setUserData("animating");
        
        // Scale animation
        item.setScaleX(0.95);
        item.setScaleY(0.95);
        
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), e -> {
            // Reset to original scale values
            item.setScaleX(originalScaleX);
            item.setScaleY(originalScaleY);
            // Mark as not animating
            item.setUserData(null);
        }));
        timeline.play();
    }
    
    /**
     * Animate button on click
     * 
     * @param button The button to animate
     */
    public void animateButton(Button button) {
        // Store original scale values
        double originalScaleX = button.getScaleX();
        double originalScaleY = button.getScaleY();
        
        // Check if animation is already running
        if (button.getUserData() != null && button.getUserData().equals("animating")) {
            return;
        }
        
        // Mark as animating
        button.setUserData("animating");
        
        // Scale animation
        button.setScaleX(0.9);
        button.setScaleY(0.9);
        
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), e -> {
            // Reset to original scale values
            button.setScaleX(originalScaleX);
            button.setScaleY(originalScaleY);
            // Mark as not animating
            button.setUserData(null);
        }));
        timeline.play();
    }
    
    /**
     * Check if sidebar is currently collapsed
     * 
     * @return true if collapsed, false if expanded
     */
    public boolean isCollapsed() {
        return isCollapsed;
    }
}