package com.filex.util;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Utility class for UI animations in the FileX application.
 * Provides helper methods for common animation effects.
 */
public class AnimationUtil {

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
     * Apply a slide-in animation from the left to a node
     * 
     * @param node The node to animate
     * @param durationSeconds The duration of the animation in seconds
     * @return The TranslateTransition that was started
     */
    public static TranslateTransition slideInFromLeft(Node node, double durationSeconds) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(durationSeconds), node);
        translateTransition.setFromX(-300); // Start off-screen to the left
        translateTransition.setToX(0);      // End at original position
        translateTransition.play();
        return translateTransition;
    }

    /**
     * Apply a slide-in animation from the top to a node
     * 
     * @param node The node to animate
     * @param durationSeconds The duration of the animation in seconds
     * @return The TranslateTransition that was started
     */
    public static TranslateTransition slideInFromTop(Node node, double durationSeconds) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(durationSeconds), node);
        translateTransition.setFromY(-100); // Start off-screen to the top
        translateTransition.setToY(0);      // End at original position
        translateTransition.play();
        return translateTransition;
    }

    /**
     * Apply a slide-in animation from the bottom to a node
     * 
     * @param node The node to animate
     * @param durationSeconds The duration of the animation in seconds
     * @return The TranslateTransition that was started
     */
    public static TranslateTransition slideInFromBottom(Node node, double durationSeconds) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(durationSeconds), node);
        translateTransition.setFromY(100); // Start off-screen to the bottom
        translateTransition.setToY(0);     // End at original position
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
     * Apply a bounce animation to a node
     * 
     * @param node The node to animate
     * @param durationSeconds The duration of the animation in seconds
     * @return The Timeline that was started
     */
    public static Timeline bounceNode(Node node, double durationSeconds) {
        Timeline timeline = new Timeline();
        
        // Keyframes for bounce animation
        timeline.getKeyFrames().addAll(
            new KeyFrame(Duration.seconds(0), e -> node.setTranslateY(0)),
            new KeyFrame(Duration.seconds(durationSeconds * 0.25), e -> node.setTranslateY(-20)),
            new KeyFrame(Duration.seconds(durationSeconds * 0.5), e -> node.setTranslateY(0)),
            new KeyFrame(Duration.seconds(durationSeconds * 0.75), e -> node.setTranslateY(-10)),
            new KeyFrame(Duration.seconds(durationSeconds), e -> node.setTranslateY(0))
        );
        
        timeline.play();
        return timeline;
    }

    /**
     * Apply a shake animation to a node
     * 
     * @param node The node to animate
     * @param durationSeconds The duration of the animation in seconds
     * @return The Timeline that was started
     */
    public static Timeline shakeNode(Node node, double durationSeconds) {
        Timeline timeline = new Timeline();
        
        // Keyframes for shake animation
        timeline.getKeyFrames().addAll(
            new KeyFrame(Duration.seconds(0), e -> node.setTranslateX(0)),
            new KeyFrame(Duration.seconds(durationSeconds * 0.1), e -> node.setTranslateX(-10)),
            new KeyFrame(Duration.seconds(durationSeconds * 0.2), e -> node.setTranslateX(10)),
            new KeyFrame(Duration.seconds(durationSeconds * 0.3), e -> node.setTranslateX(-10)),
            new KeyFrame(Duration.seconds(durationSeconds * 0.4), e -> node.setTranslateX(10)),
            new KeyFrame(Duration.seconds(durationSeconds * 0.5), e -> node.setTranslateX(-10)),
            new KeyFrame(Duration.seconds(durationSeconds * 0.6), e -> node.setTranslateX(10)),
            new KeyFrame(Duration.seconds(durationSeconds * 0.7), e -> node.setTranslateX(-10)),
            new KeyFrame(Duration.seconds(durationSeconds * 0.8), e -> node.setTranslateX(10)),
            new KeyFrame(Duration.seconds(durationSeconds * 0.9), e -> node.setTranslateX(-10)),
            new KeyFrame(Duration.seconds(durationSeconds), e -> node.setTranslateX(0))
        );
        
        timeline.play();
        return timeline;
    }

    /**
     * Apply a sequential animation combining multiple effects
     * 
     * @param nodes The nodes to animate in sequence
     * @param durationSeconds The duration of each animation in seconds
     * @return The SequentialTransition that was started
     */
    public static SequentialTransition sequentialFadeIn(Node[] nodes, double durationSeconds) {
        SequentialTransition sequentialTransition = new SequentialTransition();
        
        for (Node node : nodes) {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(durationSeconds), node);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            sequentialTransition.getChildren().add(fadeTransition);
        }
        
        sequentialTransition.play();
        return sequentialTransition;
    }

    /**
     * Apply a parallel animation combining multiple effects
     * 
     * @param nodes The nodes to animate in parallel
     * @param durationSeconds The duration of each animation in seconds
     * @return The ParallelTransition that was started
     */
    public static ParallelTransition parallelFadeIn(Node[] nodes, double durationSeconds) {
        ParallelTransition parallelTransition = new ParallelTransition();
        
        for (Node node : nodes) {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(durationSeconds), node);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            parallelTransition.getChildren().add(fadeTransition);
        }
        
        parallelTransition.play();
        return parallelTransition;
    }
}