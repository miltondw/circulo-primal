package ui.views;

import ui.GameController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Vista del menú principal del juego.
 */
public class MainMenuView {
    private final GameController controller;
    private final VBox root;

    public MainMenuView(GameController controller, int width, int height) {
        this.controller = controller;
        this.root = new VBox(30);
        initializeUI(width, height);
    }

    private void initializeUI(int width, int height) {
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #1a1a2e, #16213e);");

        // Título del juego
        Label title = new Label("🔥💧🌪️🌍");
        title.setFont(Font.font("System", FontWeight.BOLD, 60));
        title.setTextFill(Color.WHITE);

        Label subtitle = new Label("CÍRCULO PRIMAL");
        subtitle.setFont(Font.font("System", FontWeight.BOLD, 48));
        subtitle.setTextFill(Color.web("#e94560"));

        Label description = new Label("Batallas Elementales");
        description.setFont(Font.font("System", FontWeight.NORMAL, 24));
        description.setTextFill(Color.web("#aaa"));

        // Botón modo batalla infinita (actual)
        Button battleModeButton = createMenuButton("⚔️ BATALLA INFINITA", "#e94560");
        battleModeButton.setOnAction(e -> controller.showElementSelection());

        // Botón modo torneo (vs Computadora)
        Button tournamentButton = createMenuButton("🏆 TORNEO (vs CPU)", "#ff6b35");
        tournamentButton.setOnAction(e -> controller.showTournamentSetup(false));

        // Botón modo 2 jugadores
        Button twoPlayersButton = createMenuButton("👥 2 JUGADORES", "#4ecdc4");
        twoPlayersButton.setOnAction(e -> controller.showTournamentSetup(true));

        // Botón de salir
        Button exitButton = createMenuButton("🚪 SALIR", "#555");
        exitButton.setOnAction(e -> controller.exitGame());

        root.getChildren().addAll(title, subtitle, description, battleModeButton, tournamentButton, twoPlayersButton,
                exitButton);
    }

    private Button createMenuButton(String text, String color) {
        Button button = new Button(text);
        button.setFont(Font.font("System", FontWeight.BOLD, 20));
        button.setPrefWidth(300);
        button.setPrefHeight(60);
        button.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;");

        // Efecto hover
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: derive(" + color + ", 20%);" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;" +
                        "-fx-scale-x: 1.05;" +
                        "-fx-scale-y: 1.05;"));

        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;"));

        return button;
    }

    public Pane getRoot() {
        return root;
    }
}
