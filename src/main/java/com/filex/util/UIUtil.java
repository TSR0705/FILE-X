package com.filex.util;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Utility class for UI animations and measurements.
 * Provides helper methods for common UI effects and layout calculations.
 */
public class UIUtil {

    /**
     * Apply a fade-in animation to a node
     * 
     * @param node The node to animate
     * @param durationSeconds The duration of the animation in seconds
     * @return The FadeTransition that was started
     */
    public static FadeTransition fadeInNode(Node node, double durationSeconds) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(durationSeconds), node);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
        return fadeTransition;
    }

    /**
     * Apply a fade-out animation to a node
     * 
     * @param node The node to animate
     * @param durationSeconds The duration of the animation in seconds
     * @return The FadeTransition that was started
     */
    public static FadeTransition fadeOutNode(Node node, double durationSeconds) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(durationSeconds), node);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
        return fadeTransition;
    }

    /**
     * Apply a slide-in animation from the right to a node
     * 
     * @param node The node to animate
     * @param durationSeconds The duration of the animation in seconds
     * @return The TranslateTransition that was started
     */
    public static TranslateTransition slideInFromRight(Node node, double durationSeconds) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(durationSeconds), node);
        translateTransition.setFromX(300); // Start off-screen to the right
        translateTransition.setToX(0);     // End at original position
        translateTransition.play();
        return translateTransition;
    }

    /**
     * Apply a slide-out animation to the left from a node
     * 
     * @param node The node to animate
     * @param durationSeconds The duration of the animation in seconds
     * @return The TranslateTransition that was started
     */
    public static TranslateTransition slideOutToLeft(Node node, double durationSeconds) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(durationSeconds), node);
        translateTransition.setFromX(0);
        translateTransition.setToX(-300);
        translateTransition.play();
        return translateTransition;
    }

    /**
     * Apply a pulse animation to a node
     * 
     * @param node The node to animate
     * @param times The number of times to pulse
     * @param durationSeconds The duration of each pulse in seconds
     * @return The ScaleTransition that was started
     */
    public static ScaleTransition pulseNode(Node node, double times, double durationSeconds) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(durationSeconds / times), node);
        scaleTransition.setByX(0.1);
        scaleTransition.setByY(0.1);
        scaleTransition.setCycleCount((int) (times * 2)); // Scale up and down for each pulse
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
        return scaleTransition;
    }

    /**
     * Apply a subtle hover effect to a node
     * 
     * @param node The node to animate
     * @param durationSeconds The duration of the animation in seconds
     */
    public static void applyHoverEffect(Node node, double durationSeconds) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(durationSeconds), node);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.05);
        scaleTransition.setToY(1.05);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(2);
        scaleTransition.play();
    }
}