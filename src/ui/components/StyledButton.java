package ui.components;

import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Botón estilizado reutilizable.
 */
public class StyledButton extends Button {

    public StyledButton(String text, String color) {
        this(text, color, 250, 50);
    }

    public StyledButton(String text, String color, int width, int height) {
        super(text);
        this.setFont(Font.font("System", FontWeight.BOLD, 18));
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        applyStyle(color);
        setupHoverEffects(color);
    }

    private void applyStyle(String color) {
        this.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;");
    }

    private void setupHoverEffects(String color) {
        this.setOnMouseEntered(e -> this.setStyle(
                "-fx-background-color: derive(" + color + ", 20%);" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;"));

        this.setOnMouseExited(e -> applyStyle(color));
    }
}
