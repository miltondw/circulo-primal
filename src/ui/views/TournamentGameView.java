package ui.views;

import ui.GameController;
import ui.components.*;
import ui.controllers.TournamentController;
import ui.utils.ElementUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Vista refactorizada del modo torneo con responsabilidades separadas.
 */
public class TournamentGameView {
    private final GameController gameController;
    private final BorderPane root;

    private TournamentController tournamentController;

    // Componentes UI
    private Label roundLabel;
    private Label player1NameLabel;
    private Label player2NameLabel;
    private Label player1ScoreLabel;
    private Label player2ScoreLabel;
    private CombatLogPanel logPanel;
    private VBox elementButtonsBox;
    private StyledButton continueButton;

    public TournamentGameView(GameController controller, int width, int height) {
        this.gameController = controller;
        this.root = new BorderPane();
        initializeUI(width, height);
    }

    private void initializeUI(int width, int height) {
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #0f0c29, #302b63, #24243e);");

        // Panel superior: información del torneo
        VBox topPanel = createTopPanel();
        root.setTop(topPanel);

        // Panel central: botones de selección de elementos
        elementButtonsBox = createElementButtons();
        root.setCenter(elementButtonsBox);

        // Panel derecho: log de combate
        logPanel = new CombatLogPanel();
        root.setRight(logPanel);

        // Panel inferior: botón continuar
        VBox bottomPanel = new VBox(20);
        bottomPanel.setAlignment(Pos.CENTER);
        bottomPanel.setPadding(new javafx.geometry.Insets(20));

        continueButton = new StyledButton("➡️ SIGUIENTE RONDA", "#4ecdc4");
        continueButton.setVisible(false);
        continueButton.setOnAction(e -> handleContinue());

        bottomPanel.getChildren().add(continueButton);
        root.setBottom(bottomPanel);
    }

    public void initTournament(String player1Name, String player2Name, int rounds) {
        try {
            tournamentController = new TournamentController(player1Name, player2Name, rounds);

            updateScoreBoard();
            logPanel.addLog("🎮 ¡Torneo iniciado!");
            logPanel.addLog("📋 Total de rondas: " + rounds);
            logPanel.addLog("⚔️ ¡Que comiencen las batallas!");
            logPanel.addLog("");

            if (tournamentController.isTwoPlayersMode()) {
                logPanel.addLog("🎯 Turno de " + tournamentController.getCurrentPlayerTurn());
                logPanel.addLog("Selecciona tu elemento...");
            } else {
                logPanel.addLog("🎯 Selecciona tu elemento");
            }

        } catch (Exception e) {
            logPanel.addLog("❌ Error: " + e.getMessage());
        }
    }

    private VBox createTopPanel() {
        VBox panel = new VBox(15);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new javafx.geometry.Insets(20));

        roundLabel = new Label();
        roundLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
        roundLabel.setTextFill(Color.web("#ffd700"));

        HBox scoresBox = new HBox(50);
        scoresBox.setAlignment(Pos.CENTER);

        // Jugador 1
        VBox player1Box = createPlayerScoreBox("#4ecdc4");
        player1NameLabel = (Label) ((VBox) player1Box.getChildren().get(0)).getChildren().get(0);
        player1ScoreLabel = (Label) ((VBox) player1Box.getChildren().get(0)).getChildren().get(1);

        Label vsLabel = new Label("VS");
        vsLabel.setFont(Font.font("System", FontWeight.BOLD, 32));
        vsLabel.setTextFill(Color.web("#e94560"));

        // Jugador 2 / CPU
        VBox player2Box = createPlayerScoreBox("#ff6b6b");
        player2NameLabel = (Label) ((VBox) player2Box.getChildren().get(0)).getChildren().get(0);
        player2ScoreLabel = (Label) ((VBox) player2Box.getChildren().get(0)).getChildren().get(1);

        scoresBox.getChildren().addAll(player1Box, vsLabel, player2Box);
        panel.getChildren().addAll(roundLabel, scoresBox);

        return panel;
    }

    private VBox createPlayerScoreBox(String color) {
        VBox container = new VBox(5);
        container.setAlignment(Pos.CENTER);

        VBox box = new VBox(5);
        box.setAlignment(Pos.CENTER);

        Label nameLabel = new Label();
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        nameLabel.setTextFill(Color.web(color));

        Label scoreLabel = new Label();
        scoreLabel.setFont(Font.font("System", FontWeight.BOLD, 36));
        scoreLabel.setTextFill(Color.WHITE);

        box.getChildren().addAll(nameLabel, scoreLabel);
        container.getChildren().add(box);

        return container;
    }

    private VBox createElementButtons() {
        VBox box = new VBox(30);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new javafx.geometry.Insets(20));

        Label title = new Label("Elige tu Elemento");
        title.setFont(Font.font("System", FontWeight.BOLD, 32));
        title.setTextFill(Color.WHITE);

        HBox buttonsRow = new HBox(20);
        buttonsRow.setAlignment(Pos.CENTER);

        ElementButton fireButton = new ElementButton("🔥", "FUEGO", "#ff4500",
                () -> handleElementSelection("fuego"));
        ElementButton waterButton = new ElementButton("💧", "AGUA", "#1e90ff",
                () -> handleElementSelection("agua"));
        ElementButton airButton = new ElementButton("🌪️", "AIRE", "#87ceeb",
                () -> handleElementSelection("aire"));
        ElementButton earthButton = new ElementButton("🌍", "TIERRA", "#8b4513",
                () -> handleElementSelection("tierra"));

        buttonsRow.getChildren().addAll(fireButton, waterButton, airButton, earthButton);

        Label hint = new Label("Fuego > Tierra > Aire > Agua > Fuego");
        hint.setFont(Font.font("System", 16));
        hint.setTextFill(Color.web("#aaa"));

        box.getChildren().addAll(title, buttonsRow, hint);
        return box;
    }

    private void handleElementSelection(String elementName) {
        boolean shouldExecuteRound = tournamentController.processElementSelection(elementName);

        if (!shouldExecuteRound) {
            // Jugador 1 hizo su elección, esperar al jugador 2
            logPanel.addLog("✅ " + tournamentController.getModoJuego().getJugador1().getNombre() + " ha elegido");
            logPanel.addLog("");
            logPanel.addLog("🎯 Turno de " + tournamentController.getCurrentPlayerTurn());
            logPanel.addLog("Selecciona tu elemento...");
        } else {
            // Ejecutar la ronda
            elementButtonsBox.setDisable(true);
            executeRound(elementName);
        }
    }

    private void executeRound(String player2ElementName) {
        try {
            TournamentController.RoundResult result = tournamentController.executeRound(player2ElementName);

            displayRoundResult(result);
            updateScoreBoard();

            if (tournamentController.hasMoreRounds()) {
                continueButton.setVisible(true);
            } else {
                showFinalResults();
            }

        } catch (Exception e) {
            logPanel.addLog("❌ Error: " + e.getMessage());
        }
    }

    private void displayRoundResult(TournamentController.RoundResult result) {
        logPanel.addLog("━━━━━━━━━━━━━━━━━━━━━━━━");
        logPanel.addLog("⚔️ RESULTADO DE LA RONDA " + result.roundNumber);
        logPanel.addLog("");

        String p1Name = tournamentController.getModoJuego().getJugador1().getNombre();
        String p2Name = tournamentController.isTwoPlayersMode()
                ? tournamentController.getModoJuego().getJugador2().getNombre()
                : "CPU";

        logPanel.addLog(p1Name + ": " +
                ElementUtils.getElementEmoji(result.player1Element) + " " +
                result.player1Element.toUpperCase());
        logPanel.addLog(p2Name + ": " +
                ElementUtils.getElementEmoji(result.player2Element) + " " +
                result.player2Element.toUpperCase());
        logPanel.addLog("");

        if (result.winner.equals("jugador1")) {
            logPanel.addLog("🎉 ¡" + p1Name + " gana esta ronda!");
        } else if (result.winner.equals("jugador2")) {
            logPanel.addLog("🎉 ¡" + p2Name + " gana esta ronda!");
        } else {
            logPanel.addLog("🤝 ¡Empate!");
        }

        logPanel.addLog("━━━━━━━━━━━━━━━━━━━━━━━━");
        logPanel.addLog("");
    }

    private void handleContinue() {
        tournamentController.nextRound();
        continueButton.setVisible(false);
        elementButtonsBox.setDisable(false);

        updateScoreBoard();
        logPanel.addLog("🎯 RONDA " + tournamentController.getModoJuego().getRondaActual());
        if (tournamentController.isTwoPlayersMode()) {
            logPanel.addLog("Turno de " + tournamentController.getCurrentPlayerTurn());
        }
        logPanel.addLog("Selecciona tu elemento...");
        logPanel.addLog("");
    }

    private void showFinalResults() {
        TournamentController.TournamentResult result = tournamentController.getFinalResult();

        logPanel.addLog("🏁 ¡TORNEO FINALIZADO!");
        logPanel.addLog("");
        logPanel.addLog("📊 RESULTADOS FINALES:");
        logPanel.addLog("━━━━━━━━━━━━━━━━━━━━━━━━");
        logPanel.addLog(result.player1Name + ": " + result.player1Score + " victorias");
        logPanel.addLog(result.player2Name + ": " + result.player2Score + " victorias");
        logPanel.addLog("Empates: " + result.draws);
        logPanel.addLog("");

        if (result.winner.equals("empate")) {
            logPanel.addLog("🤝 ¡Es un EMPATE TOTAL!");
        } else {
            logPanel.addLog("👑 ¡" + result.winner + " es el CAMPEÓN!");
        }

        continueButton.setText("🏠 VOLVER AL MENÚ");
        continueButton.setOnAction(e -> gameController.showMainMenu());
        continueButton.setVisible(true);
    }

    private void updateScoreBoard() {
        roundLabel.setText("🎮 RONDA " + tournamentController.getModoJuego().getRondaActual() +
                " / " + tournamentController.getModoJuego().getRondas());

        player1NameLabel.setText(tournamentController.getModoJuego().getJugador1().getNombre());
        player1ScoreLabel.setText("🏆 " + tournamentController.getModoJuego().getJugador1().getVictorias());

        if (tournamentController.isTwoPlayersMode()) {
            player2NameLabel.setText(tournamentController.getModoJuego().getJugador2().getNombre());
            player2ScoreLabel.setText("🏆 " + tournamentController.getModoJuego().getJugador2().getVictorias());
        } else {
            player2NameLabel.setText("CPU");
            player2ScoreLabel.setText("🏆 " + tournamentController.getModoJuego().getJugador1().getDerrotas());
        }
    }

    public Pane getRoot() {
        return root;
    }
}
