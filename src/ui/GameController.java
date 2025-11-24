package ui;

import ui.views.*;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * Controlador principal del juego que gestiona las transiciones entre
 * pantallas.
 */
public class GameController {
    private final Stage stage;
    private final int width;
    private final int height;

    private MainMenuView mainMenuView;
    private ElementSelectionView elementSelectionView;
    private BattleView battleView;
    private TournamentSetupView tournamentSetupView;
    private ui.views.TournamentGameView tournamentGameView;

    public GameController(Stage stage, int width, int height) {
        this.stage = stage;
        this.width = width;
        this.height = height;
        initializeViews();
    }

    private void initializeViews() {
        // Las vistas se crean bajo demanda para evitar reutilizar nodos de escena
    }

    /**
     * Muestra el menú principal
     */
    public void showMainMenu() {
        mainMenuView = new MainMenuView(this, width, height);
        Scene scene = new Scene(mainMenuView.getRoot(), width, height);
        try {
            scene.getStylesheets().add(getClass().getResource("/resources/styles/game.css").toExternalForm());
        } catch (Exception e) {
            System.err.println("No se pudo cargar el CSS: " + e.getMessage());
        }
        stage.setScene(scene);
    }

    /**
     * Muestra la pantalla de selección de elemento
     */
    public void showElementSelection() {
        elementSelectionView = new ElementSelectionView(this, width, height);
        Scene scene = new Scene(elementSelectionView.getRoot(), width, height);
        try {
            scene.getStylesheets().add(getClass().getResource("/resources/styles/game.css").toExternalForm());
        } catch (Exception e) {
            System.err.println("No se pudo cargar el CSS: " + e.getMessage());
        }
        stage.setScene(scene);
    }

    /**
     * Muestra la pantalla de batalla con el elemento seleccionado
     */
    public void showBattle(String selectedElement) {
        battleView = new BattleView(this, width, height);
        battleView.initBattle(selectedElement);
        Scene scene = new Scene(battleView.getRoot(), width, height);
        try {
            scene.getStylesheets().add(getClass().getResource("/resources/styles/game.css").toExternalForm());
        } catch (Exception e) {
            System.err.println("No se pudo cargar el CSS: " + e.getMessage());
        }
        stage.setScene(scene);
    }

    /**
     * Muestra la pantalla de configuración del torneo
     */
    public void showTournamentSetup(boolean isTwoPlayers) {
        tournamentSetupView = new TournamentSetupView(this, width, height, isTwoPlayers);
        Scene scene = new Scene(tournamentSetupView.getRoot(), width, height);
        try {
            scene.getStylesheets().add(getClass().getResource("/resources/styles/game.css").toExternalForm());
        } catch (Exception e) {
            System.err.println("No se pudo cargar el CSS: " + e.getMessage());
        }
        stage.setScene(scene);
    }

    /**
     * Muestra la pantalla del juego de torneo
     */
    public void showTournamentGame(String player1Name, String player2Name, int rounds) {
        tournamentGameView = new ui.views.TournamentGameView(this, width, height);
        Scene scene = new Scene(tournamentGameView.getRoot(), width, height);
        try {
            scene.getStylesheets().add(getClass().getResource("/resources/styles/game.css").toExternalForm());
        } catch (Exception e) {
            System.err.println("No se pudo cargar el CSS: " + e.getMessage());
        }
        stage.setScene(scene);
        tournamentGameView.initTournament(player1Name, player2Name, rounds);
    }

    /**
     * Cierra la aplicación
     */
    public void exitGame() {
        stage.close();
    }
}
