package ui.utils;

/**
 * Utilidades para elementos del juego.
 */
public class ElementUtils {

    public static String getElementEmoji(String element) {
        switch (element.toLowerCase()) {
            case "fuego":
                return "🔥";
            case "agua":
                return "💧";
            case "aire":
                return "🌪️";
            case "tierra":
                return "🌍";
            default:
                return "❓";
        }
    }

    public static String getElementColor(String element) {
        switch (element.toLowerCase()) {
            case "fuego":
                return "#ff4500";
            case "agua":
                return "#1e90ff";
            case "aire":
                return "#87ceeb";
            case "tierra":
                return "#8b4513";
            default:
                return "#555";
        }
    }

    /**
     * Determina qué elemento gana según las reglas del juego.
     * Retorna "jugador1", "jugador2" o "empate"
     */
    public static String determinarGanador(String elem1, String elem2) {
        if (elem1.equals(elem2)) {
            return "empate";
        }

        switch (elem1.toLowerCase()) {
            case "fuego":
                return elem2.equals("tierra") ? "jugador1" : "jugador2";
            case "agua":
                return elem2.equals("fuego") ? "jugador1" : "jugador2";
            case "aire":
                return elem2.equals("agua") ? "jugador1" : "jugador2";
            case "tierra":
                return elem2.equals("aire") ? "jugador1" : "jugador2";
            default:
                return "empate";
        }
    }
}
