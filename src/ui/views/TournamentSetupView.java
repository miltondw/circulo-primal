package ui.views;

import ui.GameController;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Vista de configuración del torneo (modo rondas).
 */
public class TournamentSetupView {
    private final GameController controller;
    private final VBox root;
    private final boolean isTwoPlayers;

    private TextField player1NameField;
    private TextField player2NameField;
    private Spinner<Integer> roundsSpinner;

    public TournamentSetupView(GameController controller, int width, int height, boolean isTwoPlayers) {
        this.controller = controller;
        this.isTwoPlayers = isTwoPlayers;
        this.root = new VBox(25);
        initializeUI(width, height);
    }

    private void initializeUI(int width, int height) {
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #1a1a2e, #16213e);");

        // Título
        Label title = new Label(isTwoPlayers ? "👥 MODO 2 JUGADORES" : "🏆 MODO TORNEO");
        title.setFont(Font.font("System", FontWeight.BOLD, 42));
        title.setTextFill(Color.web("#e94560"));

        // Campo nombre jugador 1
        VBox player1Box = createPlayerNameBox("Jugador 1", true);

        // Campo nombre jugador 2 (solo si es modo 2 jugadores)
        VBox player2Box = null;
        if (isTwoPlayers) {
            player2Box = createPlayerNameBox("Jugador 2", false);
        }

        // Selector de rondas
        VBox roundsBox = createRoundsSelector();

        // Botones
        HBox buttonsBox = new HBox(20);
        buttonsBox.setAlignment(Pos.CENTER);

        Button startButton = createButton("⚔️ COMENZAR", "#4ecdc4");
        startButton.setOnAction(e -> handleStart());

        Button backButton = createButton("⬅️ VOLVER", "#555");
        backButton.setOnAction(e -> controller.showMainMenu());

        buttonsBox.getChildren().addAll(backButton, startButton);

        // Agregar elementos al root
        root.getChildren().add(title);
        root.getChildren().add(player1Box);
        if (isTwoPlayers && player2Box != null) {
            root.getChildren().add(player2Box);
        }
        root.getChildren().add(roundsBox);
        root.getChildren().add(buttonsBox);
    }

    private VBox createPlayerNameBox(String labelText, boolean isPlayer1) {
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);

        Label label = new Label(labelText + ":");
        label.setFont(Font.font("System", FontWeight.BOLD, 20));
        label.setTextFill(Color.WHITE);

        TextField textField = new TextField();
        textField.setPromptText("Ingresa tu nombre");
        textField.setPrefWidth(300);
        textField.setMaxWidth(300);
        textField.setFont(Font.font("System", 16));
        textField.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.1);" +
                        "-fx-text-fill: white;" +
                        "-fx-prompt-text-fill: rgba(255, 255, 255, 0.5);" +
                        "-fx-background-radius: 5;" +
                        "-fx-border-color: #4ecdc4;" +
                        "-fx-border-radius: 5;" +
                        "-fx-border-width: 2;" +
                        "-fx-padding: 10;");

        if (isPlayer1) {
            player1NameField = textField;
        } else {
            player2NameField = textField;
        }

        box.getChildren().addAll(label, textField);
        return box;
    }

    private VBox createRoundsSelector() {
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);

        Label label = new Label("Número de Rondas:");
        label.setFont(Font.font("System", FontWeight.BOLD, 20));
        label.setTextFill(Color.WHITE);

        roundsSpinner = new Spinner<>(1, 99, 3);
        roundsSpinner.setPrefWidth(120);
        roundsSpinner.setEditable(true);
        roundsSpinner.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;");

        box.getChildren().addAll(label, roundsSpinner);
        return box;
    }

    private Button createButton(String text, String color) {
        Button button = new Button(text);
        button.setFont(Font.font("System", FontWeight.BOLD, 18));
        button.setPrefWidth(180);
        button.setPrefHeight(50);
        button.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;");

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

    private void handleStart() {
        String player1Name = player1NameField.getText().trim();

        if (player1Name.isEmpty()) {
            showError("Por favor ingresa el nombre del Jugador 1");
            return;
        }

        if (player1Name.length() < 2) {
            showError("El nombre debe tener al menos 2 caracteres");
            return;
        }

        if (isTwoPlayers) {
            String player2Name = player2NameField.getText().trim();
            if (player2Name.isEmpty()) {
                showError("Por favor ingresa el nombre del Jugador 2");
                return;
            }
            if (player2Name.length() < 2) {
                showError("El nombre debe tener al menos 2 caracteres");
                return;
            }
            controller.showTournamentGame(player1Name, player2Name, roundsSpinner.getValue());
        } else {
            controller.showTournamentGame(player1Name, null, roundsSpinner.getValue());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Pane getRoot() {
        return root;
    }
}
