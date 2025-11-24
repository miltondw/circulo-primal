package ui.components;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Panel de log de combate reutilizable.
 */
public class CombatLogPanel extends VBox {
    private final TextArea logArea;

    public CombatLogPanel() {
        super(10);
        this.setPadding(new javafx.geometry.Insets(20));
        this.setPrefWidth(300);
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3);");

        Label logTitle = new Label("📜 Registro de Combate");
        logTitle.setFont(Font.font("System", FontWeight.BOLD, 18));
        logTitle.setTextFill(Color.WHITE);

        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setWrapText(true);
        logArea.setPrefHeight(600);
        logArea.setStyle(
                "-fx-control-inner-background: rgba(0, 0, 0, 0.5);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-family: 'Consolas', 'Monaco', monospace;");

        this.getChildren().addAll(logTitle, logArea);
    }

    public void addLog(String message) {
        logArea.appendText(message + "\n");
    }

    public void clear() {
        logArea.clear();
    }
}
