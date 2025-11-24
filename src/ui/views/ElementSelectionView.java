package ui.views;

import ui.GameController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Vista de selección de elemento del jugador.
 */
public class ElementSelectionView {
    private final GameController controller;
    private final VBox root;

    public ElementSelectionView(GameController controller, int width, int height) {
        this.controller = controller;
        this.root = new VBox(40);
        initializeUI(width, height);
    }

    private void initializeUI(int width, int height) {
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #1a1a2e, #16213e);");

        // Título
        Label title = new Label("Elige tu Elemento");
        title.setFont(Font.font("System", FontWeight.BOLD, 42));
        title.setTextFill(Color.WHITE);

        // Contenedor de botones de elementos
        HBox elementButtons = new HBox(20);
        elementButtons.setAlignment(Pos.CENTER);

        // Crear botón para cada elemento
        Button fireButton = createElementButton("🔥", "FUEGO", "#ff4500", "fuego");
        Button waterButton = createElementButton("💧", "AGUA", "#1e90ff", "agua");
        Button airButton = createElementButton("🌪️", "AIRE", "#87ceeb", "aire");
        Button earthButton = createElementButton("🌍", "TIERRA", "#8b4513", "tierra");

        elementButtons.getChildren().addAll(fireButton, waterButton, airButton, earthButton);

        // Descripción de ventajas
        Label info = new Label("Fuego > Tierra > Aire > Agua > Fuego");
        info.setFont(Font.font("System", FontWeight.NORMAL, 18));
        info.setTextFill(Color.web("#aaa"));

        // Botón volver
        Button backButton = createBackButton();

        root.getChildren().addAll(title, elementButtons, info, backButton);
    }

    private Button createElementButton(String emoji, String name, String color, String elementName) {
        VBox buttonContent = new VBox(10);
        buttonContent.setAlignment(Pos.CENTER);

        Label emojiLabel = new Label(emoji);
        emojiLabel.setFont(Font.font(60));

        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        nameLabel.setTextFill(Color.WHITE);

        buttonContent.getChildren().addAll(emojiLabel, nameLabel);

        Button button = new Button();
        button.setGraphic(buttonContent);
        button.setPrefSize(180, 200);
        button.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-background-radius: 15;" +
                        "-fx-cursor: hand;" +
                        "-fx-border-width: 3;" +
                        "-fx-border-color: transparent;" +
                        "-fx-border-radius: 15;");

        // Efectos hover
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: derive(" + color + ", 20%);" +
                        "-fx-background-radius: 15;" +
                        "-fx-cursor: hand;" +
                        "-fx-border-width: 3;" +
                        "-fx-border-color: white;" +
                        "-fx-border-radius: 15;" +
                        "-fx-scale-x: 1.1;" +
                        "-fx-scale-y: 1.1;"));

        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-background-radius: 15;" +
                        "-fx-cursor: hand;" +
                        "-fx-border-width: 3;" +
                        "-fx-border-color: transparent;" +
                        "-fx-border-radius: 15;"));

        button.setOnAction(e -> controller.showBattle(elementName));

        return button;
    }

    private Button createBackButton() {
        Button button = new Button("← Volver");
        button.setFont(Font.font("System", FontWeight.NORMAL, 16));
        button.setPrefWidth(150);
        button.setPrefHeight(40);
        button.setStyle(
                "-fx-background-color: #555;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 8;" +
                        "-fx-cursor: hand;");

        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: #777;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 8;" +
                        "-fx-cursor: hand;"));

        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: #555;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 8;" +
                        "-fx-cursor: hand;"));

        button.setOnAction(e -> controller.showMainMenu());

        return button;
    }

    public Pane getRoot() {
        return root;
    }
}
