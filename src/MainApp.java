import javafx.application.Application;
import javafx.stage.Stage;
import ui.GameController;

/**
 * Aplicación principal del juego Círculo Primal con interfaz gráfica JavaFX.
 * Inicializa la ventana principal y el controlador del juego.
 */
public class MainApp extends Application {
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("🔥 Círculo Primal - Batallas Elementales");
        primaryStage.setResizable(false);

        // Inicializar controlador del juego
        GameController gameController = new GameController(primaryStage, WINDOW_WIDTH, WINDOW_HEIGHT);
        gameController.showMainMenu();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
