package ui.views;

import ui.GameController;
import domain.ElementoBatalla;
import domain.ElementoBatalla.TipoAtaque;
import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import java.util.Random;

/**
 * Vista de batalla con animaciones y efectos visuales.
 */
public class BattleView {
    private final GameController controller;
    private final BorderPane root;
    private final Random random = new Random();

    private ElementoBatalla jugador;
    private ElementoBatalla enemigo; // Componentes UI
    private Label jugadorNameLabel;
    private Label enemigoNameLabel;
    private ProgressBar jugadorHpBar;
    private ProgressBar enemigoHpBar;
    private Label jugadorHpText;
    private Label enemigoHpText;
    private TextArea combatLog;
    private Button rapidoButton;
    private Button elementalButton;
    private Button volverButton;

    // Elementos visuales de los personajes
    private Pane jugadorSprite;
    private Pane enemigoSprite;

    // Variables de estado
    private int rachaVictorias = 0;
    private boolean battleEnded = false;

    public BattleView(GameController controller, int width, int height) {
        this.controller = controller;
        this.root = new BorderPane();
        initializeUI(width, height);
    }

    private void initializeUI(int width, int height) {
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #0f0c29, #302b63, #24243e);");

        // Panel superior: enemigo
        VBox topPanel = createEnemyPanel();
        root.setTop(topPanel);

        // Panel central: área de batalla
        HBox centerPanel = createBattleArea();
        root.setCenter(centerPanel);

        // Panel inferior: jugador y controles
        VBox bottomPanel = createPlayerPanel();
        root.setBottom(bottomPanel);

        // Panel derecho: log de combate
        VBox rightPanel = createCombatLogPanel();
        root.setRight(rightPanel);
    }

    private VBox createEnemyPanel() {
        VBox panel = new VBox(10);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new javafx.geometry.Insets(20));

        enemigoNameLabel = new Label();
        enemigoNameLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
        enemigoNameLabel.setTextFill(Color.web("#ff6b6b"));

        // Barra de vida
        HBox hpBox = new HBox(10);
        hpBox.setAlignment(Pos.CENTER);

        enemigoHpBar = new ProgressBar(1.0);
        enemigoHpBar.setPrefWidth(300);
        enemigoHpBar.setPrefHeight(30);
        enemigoHpBar.setStyle("-fx-accent: #ff6b6b;");

        enemigoHpText = new Label();
        enemigoHpText.setFont(Font.font("System", FontWeight.BOLD, 16));
        enemigoHpText.setTextFill(Color.WHITE);

        hpBox.getChildren().addAll(enemigoHpBar, enemigoHpText);
        panel.getChildren().addAll(enemigoNameLabel, hpBox);

        return panel;
    }

    private HBox createBattleArea() {
        HBox area = new HBox(100);
        area.setAlignment(Pos.CENTER);
        area.setPadding(new javafx.geometry.Insets(40));

        // Sprite del jugador
        jugadorSprite = createCharacterSprite(120, "#4ecdc4");

        // Sprite del enemigo
        enemigoSprite = createCharacterSprite(120, "#ff6b6b");

        area.getChildren().addAll(jugadorSprite, enemigoSprite);
        return area;
    }

    private Pane createCharacterSprite(double size, String color) {
        StackPane sprite = new StackPane();

        // Círculo exterior (aura)
        javafx.scene.shape.Circle aura = new javafx.scene.shape.Circle(size / 2);
        aura.setFill(Color.web(color, 0.3));

        // Círculo interior (cuerpo)
        javafx.scene.shape.Circle body = new javafx.scene.shape.Circle(size / 2.5);
        body.setFill(Color.web(color));
        body.setStroke(Color.WHITE);
        body.setStrokeWidth(3);

        sprite.getChildren().addAll(aura, body);
        return sprite;
    }

    private VBox createPlayerPanel() {
        VBox panel = new VBox(15);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new javafx.geometry.Insets(20));

        // Barra de vida
        HBox hpBox = new HBox(10);
        hpBox.setAlignment(Pos.CENTER);

        jugadorHpBar = new ProgressBar(1.0);
        jugadorHpBar.setPrefWidth(300);
        jugadorHpBar.setPrefHeight(30);
        jugadorHpBar.setStyle("-fx-accent: #4ecdc4;");

        jugadorHpText = new Label();
        jugadorHpText.setFont(Font.font("System", FontWeight.BOLD, 16));
        jugadorHpText.setTextFill(Color.WHITE);

        hpBox.getChildren().addAll(jugadorHpBar, jugadorHpText);

        jugadorNameLabel = new Label();
        jugadorNameLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
        jugadorNameLabel.setTextFill(Color.web("#4ecdc4"));

        // Botones de ataque
        HBox attackButtons = new HBox(20);
        attackButtons.setAlignment(Pos.CENTER);

        rapidoButton = createAttackButton("⚡ ATAQUE RÁPIDO", "#4ecdc4");
        rapidoButton.setOnAction(e -> ejecutarTurno(TipoAtaque.RAPIDO));

        elementalButton = createAttackButton("💥 ATAQUE ELEMENTAL", "#ff6b6b");
        elementalButton.setOnAction(e -> ejecutarTurno(TipoAtaque.ELEMENTAL));

        attackButtons.getChildren().addAll(rapidoButton, elementalButton);

        // Botón volver (inicialmente oculto)
        volverButton = createAttackButton("🏠 Menú Principal", "#555");
        volverButton.setOnAction(e -> controller.showMainMenu());
        volverButton.setVisible(false);

        panel.getChildren().addAll(hpBox, jugadorNameLabel, attackButtons, volverButton);

        return panel;
    }

    private VBox createCombatLogPanel() {
        VBox panel = new VBox(10);
        panel.setPadding(new javafx.geometry.Insets(20));
        panel.setPrefWidth(280);

        Label title = new Label("📜 Log de Combate");
        title.setFont(Font.font("System", FontWeight.BOLD, 18));
        title.setTextFill(Color.WHITE);

        combatLog = new TextArea();
        combatLog.setEditable(false);
        combatLog.setWrapText(true);
        combatLog.setPrefHeight(500);
        combatLog.setStyle(
                "-fx-control-inner-background: #1a1a2e;" +
                        "-fx-text-fill: #aaa;" +
                        "-fx-font-size: 13px;");

        panel.getChildren().addAll(title, combatLog);
        return panel;
    }

    private Button createAttackButton(String text, String color) {
        Button button = new Button(text);
        button.setFont(Font.font("System", FontWeight.BOLD, 16));
        button.setPrefWidth(220);
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
                        "-fx-cursor: hand;"));

        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;"));

        return button;
    }

    /**
     * Inicializa una nueva batalla con el elemento seleccionado
     */
    public void initBattle(String elementoSeleccionado) {
        // Crear personajes
        jugador = new ElementoBatalla(elementoSeleccionado, 50, 12);

        String[] elementos = { "fuego", "agua", "aire", "tierra" };
        String elementoEnemigo = elementos[random.nextInt(elementos.length)];
        enemigo = new ElementoBatalla(elementoEnemigo, 50, 12);

        // Actualizar UI
        jugadorNameLabel.setText("🎮 " + jugador.getNombre().toUpperCase());
        enemigoNameLabel.setText("🤖 " + enemigo.getNombre().toUpperCase());

        actualizarBarrasVida();

        // Cambiar color del sprite según elemento
        updateSpriteColor(jugadorSprite, getElementColor(elementoSeleccionado));
        updateSpriteColor(enemigoSprite, getElementColor(elementoEnemigo));

        combatLog.setText("¡Batalla iniciada!\n\n");
        battleEnded = false;

        // Habilitar botones
        rapidoButton.setDisable(false);
        elementalButton.setDisable(false);
        volverButton.setVisible(false);
    }

    private void updateSpriteColor(Pane sprite, String color) {
        if (sprite instanceof StackPane) {
            StackPane stack = (StackPane) sprite;
            if (stack.getChildren().size() >= 2) {
                javafx.scene.shape.Circle aura = (javafx.scene.shape.Circle) stack.getChildren().get(0);
                javafx.scene.shape.Circle body = (javafx.scene.shape.Circle) stack.getChildren().get(1);
                aura.setFill(Color.web(color, 0.3));
                body.setFill(Color.web(color));
            }
        }
    }

    private String getElementColor(String elemento) {
        switch (elemento) {
            case "fuego":
                return "#ff4500";
            case "agua":
                return "#1e90ff";
            case "aire":
                return "#87ceeb";
            case "tierra":
                return "#8b4513";
            default:
                return "#4ecdc4";
        }
    }

    /**
     * Ejecuta un turno completo (jugador + enemigo)
     */
    private void ejecutarTurno(TipoAtaque tipoAtaqueJugador) {
        if (battleEnded)
            return;

        // Deshabilitar botones durante animación
        rapidoButton.setDisable(true);
        elementalButton.setDisable(true);

        // Turno del jugador
        int danioJugador = jugador.calcularDanioContra(enemigo, tipoAtaqueJugador);
        enemigo.recibirDanio(danioJugador);

        String logJugador = danioJugador == 0
                ? "⚠️ Tu ataque " + tipoAtaqueJugador.name().toLowerCase() + " falló!\n"
                : "⚔️ Tu " + tipoAtaqueJugador.name().toLowerCase() + " hace " + danioJugador + " de daño\n";

        combatLog.appendText(logJugador);

        // Animar ataque del jugador
        animateAttack(jugadorSprite, enemigoSprite, () -> {
            actualizarBarrasVida();

            // Verificar si el enemigo fue derrotado
            if (!enemigo.estaVivo()) {
                finalizarBatalla(true);
                return;
            }

            // Turno del enemigo (después de un pequeño delay)
            PauseTransition pause = new PauseTransition(Duration.seconds(0.8));
            pause.setOnFinished(e -> {
                TipoAtaque tipoAtaqueEnemigo = random.nextBoolean()
                        ? TipoAtaque.RAPIDO
                        : TipoAtaque.ELEMENTAL;

                int danioEnemigo = enemigo.calcularDanioContra(jugador, tipoAtaqueEnemigo);
                jugador.recibirDanio(danioEnemigo);

                String logEnemigo = danioEnemigo == 0
                        ? "⚠️ El ataque enemigo " + tipoAtaqueEnemigo.name().toLowerCase() + " falló!\n"
                        : "💢 Enemigo " + tipoAtaqueEnemigo.name().toLowerCase() + " hace " + danioEnemigo
                                + " de daño\n";

                combatLog.appendText(logEnemigo);
                combatLog.appendText("\n");

                // Animar ataque del enemigo
                animateAttack(enemigoSprite, jugadorSprite, () -> {
                    actualizarBarrasVida();

                    // Verificar si el jugador fue derrotado
                    if (!jugador.estaVivo()) {
                        finalizarBatalla(false);
                    } else {
                        // Re-habilitar botones
                        rapidoButton.setDisable(false);
                        elementalButton.setDisable(false);
                    }
                });
            });
            pause.play();
        });
    }

    private void actualizarBarrasVida() {
        double jugadorRatio = (double) jugador.getHp() / jugador.getMaxHp();
        double enemigoRatio = (double) enemigo.getHp() / enemigo.getMaxHp();

        jugadorHpBar.setProgress(jugadorRatio);
        enemigoHpBar.setProgress(enemigoRatio);

        jugadorHpText.setText(jugador.getHp() + "/" + jugador.getMaxHp());
        enemigoHpText.setText(enemigo.getHp() + "/" + enemigo.getMaxHp());
    }

    private void animateAttack(Pane attacker, Pane target, Runnable onComplete) {
        // Animación de movimiento hacia adelante
        TranslateTransition moveForward = new TranslateTransition(Duration.seconds(0.2), attacker);
        moveForward.setByX(attacker == jugadorSprite ? 50 : -50);

        // Animación de sacudida del objetivo
        TranslateTransition shake1 = new TranslateTransition(Duration.millis(50), target);
        shake1.setByX(10);

        TranslateTransition shake2 = new TranslateTransition(Duration.millis(50), target);
        shake2.setByX(-20);

        TranslateTransition shake3 = new TranslateTransition(Duration.millis(50), target);
        shake3.setByX(10);

        SequentialTransition shakeSequence = new SequentialTransition(shake1, shake2, shake3);

        // Animación de retroceso
        TranslateTransition moveBack = new TranslateTransition(Duration.seconds(0.2), attacker);
        moveBack.setByX(attacker == jugadorSprite ? -50 : 50);

        // Secuencia completa
        SequentialTransition sequence = new SequentialTransition(
                moveForward,
                shakeSequence,
                moveBack);

        sequence.setOnFinished(e -> onComplete.run());
        sequence.play();
    }

    private void finalizarBatalla(boolean jugadorGano) {
        battleEnded = true;
        rapidoButton.setDisable(true);
        elementalButton.setDisable(true);

        if (jugadorGano) {
            rachaVictorias++;
            combatLog.appendText("🏆 ¡VICTORIA! Has ganado la batalla\n");

            if (rachaVictorias == 3) {
                jugador.aumentarAtaque(3);
                combatLog.appendText("\n🔥 ¡BONUS! 3 victorias seguidas\n");
                combatLog.appendText("Ataque +3 permanente\n");
            } else if (rachaVictorias > 3 && rachaVictorias % 5 == 0) {
                jugador.aumentarAtaque(2);
                combatLog.appendText("\n✨ Racha de " + rachaVictorias + " victorias!\n");
                combatLog.appendText("Ataque adicional +2\n");
            }

            combatLog.appendText("\nRacha actual: " + rachaVictorias + " victorias\n");

            // Animar victoria
            animateVictory(jugadorSprite);
        } else {
            if (rachaVictorias >= 3) {
                combatLog.appendText("\n💔 Tu racha de " + rachaVictorias + " se rompió\n");
            }
            rachaVictorias = 0;
            combatLog.appendText("💀 DERROTA... Has sido derrotado\n");

            // Animar derrota
            animateDefeat(jugadorSprite);
        }

        // Mostrar botón de volver después de un delay
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> volverButton.setVisible(true));
        pause.play();
    }

    private void animateVictory(Pane sprite) {
        ScaleTransition scale = new ScaleTransition(Duration.seconds(0.5), sprite);
        scale.setToX(1.3);
        scale.setToY(1.3);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);
        scale.play();
    }

    private void animateDefeat(Pane sprite) {
        FadeTransition fade = new FadeTransition(Duration.seconds(1), sprite);
        fade.setToValue(0.3);
        fade.play();
    }

    public Pane getRoot() {
        return root;
    }
}
