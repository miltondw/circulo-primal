package ui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Componente reutilizable para botones de elementos.
 */
public class ElementButton extends Button {

    public ElementButton(String emoji, String name, String color, Runnable onSelect) {
        super();

        VBox content = new VBox(8);
        content.setAlignment(Pos.CENTER);

        Label emojiLabel = new Label(emoji);
        emojiLabel.setFont(Font.font(50));

        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        nameLabel.setTextFill(Color.WHITE);

        content.getChildren().addAll(emojiLabel, nameLabel);

        this.setGraphic(content);
        this.setPrefSize(140, 160);
        this.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-background-radius: 12;" +
                        "-fx-cursor: hand;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-color: transparent;" +
                        "-fx-border-radius: 12;");

        this.setOnMouseEntered(e -> this.setStyle(
                "-fx-background-color: derive(" + color + ", 20%);" +
                        "-fx-background-radius: 12;" +
                        "-fx-cursor: hand;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-color: white;" +
                        "-fx-border-radius: 12;" +
                        "-fx-scale-x: 1.08;" +
                        "-fx-scale-y: 1.08;"));

        this.setOnMouseExited(e -> this.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-background-radius: 12;" +
                        "-fx-cursor: hand;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-color: transparent;" +
                        "-fx-border-radius: 12;"));

        this.setOnAction(e -> onSelect.run());
    }
}
