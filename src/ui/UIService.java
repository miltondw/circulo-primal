package ui;

public class UIService {
    
    // Colores ANSI
    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String MAGENTA = "\u001B[35m";
    private static final String BLUE = "\u001B[34m";

    public void mostrarBienvenida() {
        limpiarPantalla();
        System.out.println(CYAN + "╔════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN + "║" + RESET + BOLD + YELLOW + "   🔥 JUEGO DE LOS ELEMENTOS 💧 🌪️  🌍" + RESET + CYAN + "        ║" + RESET);
        System.out.println(CYAN + "╚════════════════════════════════════════════════════════╝" + RESET);
        System.out.println();
    }

    public void mostrarMenuPrincipal() {
        System.out.println(BLUE + "┌──────────────────────────────────────────┐" + RESET);
        System.out.println(BLUE + "│" + RESET + "   SELECCIONA EL MODO DE JUEGO:" + BLUE + "          │" + RESET);
        System.out.println(BLUE + "├──────────────────────────────────────────┤" + RESET);
        System.out.println(BLUE + "│" + RESET + GREEN + " 1." + RESET + " Jugar contra la computadora       " + BLUE + "│" + RESET);
        System.out.println(BLUE + "│" + RESET + GREEN + " 2." + RESET + " Jugar contra otro jugador        " + BLUE + "│" + RESET);
        System.out.println(BLUE + "└──────────────────────────────────────────┘" + RESET);
        System.out.print(YELLOW + "▶ Elige una opción (1 o 2): " + RESET);
    }

    public void mostrarMenuElementos() {
        System.out.println(GREEN + "┌────────────────────────────────────┐" + RESET);
        System.out.println(GREEN + "│" + RESET + "    ELIGE TU ELEMENTO:" + GREEN + "              │" + RESET);
        System.out.println(GREEN + "├────────────────────────────────────┤" + RESET);
        System.out.println(GREEN + "│" + RESET + MAGENTA + " 1." + RESET + " 🔥 Fuego" + MAGENTA + "              " + GREEN + "       │" + RESET);
        System.out.println(GREEN + "│" + RESET + MAGENTA + " 2." + RESET + " 💧 Agua" + MAGENTA + "               " + GREEN + "       │" + RESET);
        System.out.println(GREEN + "│" + RESET + MAGENTA + " 3." + RESET + " 🌪️  Aire" + MAGENTA + "               " + GREEN + "       │" + RESET);
        System.out.println(GREEN + "│" + RESET + MAGENTA + " 4." + RESET + " 🌍 Tierra" + MAGENTA + "              " + GREEN + "       │" + RESET);
        System.out.println(GREEN + "└────────────────────────────────────┘" + RESET);
    }

    public void mostrarRonda(int actual, int total) {
        System.out.println();
        System.out.println(BOLD + CYAN + "═══════════════════════════════════════════" + RESET);
        System.out.println(BOLD + CYAN + "   🎮 RONDA " + actual + " de " + total + " 🎮" + RESET);
        System.out.println(BOLD + CYAN + "═══════════════════════════════════════════" + RESET);
        System.out.println();
    }

    public void mostrarSeleccionRegistrada() {
        System.out.println(YELLOW + "┌─────────────────────────────────────────┐" + RESET);
        System.out.println(YELLOW + "│" + RESET + "  🔒 La selección ha sido registrada  " + YELLOW + "│" + RESET);
        System.out.println(YELLOW + "└─────────────────────────────────────────┘" + RESET);
        System.out.println();
    }

    public void mostrarResultadosRonda() {
        System.out.println();
        System.out.println(BOLD + MAGENTA + "✨ RESULTADOS DE LA RONDA ✨" + RESET);
        System.out.println(MAGENTA + "─────────────────────────────────" + RESET);
    }

    public void mostrarEmpate() {
        System.out.println(YELLOW + BOLD + "🤝 ¡EMPATE! 🤝" + RESET);
    }

    public void mostrarVictoria(String ganador, String elemento) {
        System.out.println(GREEN + BOLD + "🏆 ¡" + ganador + " gana! " + elemento + " es poderoso 🏆" + RESET);
    }

    public void mostrarDerrota(String perdedor, String elementoPerdedor, String elementoGanador) {
        System.out.println(RED + BOLD + "💀 " + elementoGanador + " vence a " + elementoPerdedor + " 💀" + RESET);
    }

    public void mostrarResultadoFinal() {
        System.out.println();
        System.out.println(BOLD + CYAN + "╔════════════════════════════════════════╗" + RESET);
        System.out.println(BOLD + CYAN + "║" + RESET + YELLOW + "  🎯 RESULTADO FINAL DEL JUEGO 🎯" + RESET + BOLD + CYAN + "    ║" + RESET);
        System.out.println(BOLD + CYAN + "╚════════════════════════════════════════╝" + RESET);
        System.out.println();
    }

    public void mostrarGanadorFinal(String nombre) {
        System.out.println(BOLD + GREEN + "┌────────────────────────────────────────┐" + RESET);
        System.out.println(BOLD + GREEN + "│" + RESET + "  🏆 " + nombre + " ES EL GANADOR 🏆" + RESET + BOLD + GREEN + "      │" + RESET);
        System.out.println(BOLD + GREEN + "└────────────────────────────────────────┘" + RESET);
    }

    public void mostrarEmpateGeneral() {
        System.out.println(BOLD + YELLOW + "┌────────────────────────────────────────┐" + RESET);
        System.out.println(BOLD + YELLOW + "│" + RESET + "  🤝 ¡EL JUEGO FUE UN EMPATE! 🤝" + RESET + BOLD + YELLOW + "  │" + RESET);
        System.out.println(BOLD + YELLOW + "└────────────────────────────────────────┘" + RESET);
    }

    public void mostrarEstadisticas(String nombre, int victorias, int derrotas, int empates, int total) {
        System.out.println();
        System.out.println(CYAN + "┌────────────────────────────────────────┐" + RESET);
        System.out.println(CYAN + "│ 📊 ESTADÍSTICAS DE " + nombre + CYAN + "             │" + RESET);
        System.out.println(CYAN + "├────────────────────────────────────────┤" + RESET);
        System.out.println(CYAN + "│ " + RESET + GREEN + "🏆 Victorias: " + victorias + CYAN + RESET + String.format("%30s", "") + CYAN + "│" + RESET);
        System.out.println(CYAN + "│ " + RESET + RED + "💀 Derrotas: " + derrotas + CYAN + RESET + String.format("%31s", "") + CYAN + "│" + RESET);
        System.out.println(CYAN + "│ " + RESET + YELLOW + "🤝 Empates: " + empates + CYAN + RESET + String.format("%31s", "") + CYAN + "│" + RESET);
        System.out.println(CYAN + "│ " + RESET + BLUE + "📈 Total: " + total + CYAN + RESET + String.format("%33s", "") + CYAN + "│" + RESET);
        System.out.println(CYAN + "└────────────────────────────────────────┘" + RESET);
    }

    public void mostrarMenuVolverAJugar() {
        System.out.println();
        System.out.println(MAGENTA + "┌──────────────────────────────────────────┐" + RESET);
        System.out.println(MAGENTA + "│" + RESET + "   ¿DESEAS JUGAR DE NUEVO?" + MAGENTA + "             │" + RESET);
        System.out.println(MAGENTA + "├──────────────────────────────────────────┤" + RESET);
        System.out.println(MAGENTA + "│" + RESET + GREEN + " 1." + RESET + " Sí, jugar otra partida           " + MAGENTA + "│" + RESET);
        System.out.println(MAGENTA + "│" + RESET + RED + " 2." + RESET + " No, salir del juego              " + MAGENTA + "│" + RESET);
        System.out.println(MAGENTA + "└──────────────────────────────────────────┘" + RESET);
        System.out.print(YELLOW + "▶ Elige una opción (1 o 2): " + RESET);
    }

    public void mostrarError(String mensaje) {
        System.out.println(RED + BOLD + "❌ Error: " + mensaje + RESET);
    }

    public void mostrarExito(String mensaje) {
        System.out.println(GREEN + BOLD + "✅ " + mensaje + RESET);
    }

    public void mostrarDespedida() {
        System.out.println();
        System.out.println(CYAN + "╔════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN + "║" + RESET + BOLD + YELLOW + "      ¡Gracias por jugar! 👋 ¡Hasta pronto! 👋" + RESET + CYAN + "      ║" + RESET);
        System.out.println(CYAN + "╚════════════════════════════════════════════════════════╝" + RESET);
        System.out.println();
    }

    public void limpiarPantalla() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public void mostrarCargando() {
        System.out.println(YELLOW + "⏳ Procesando..." + RESET);
    }

    public void mostrarLineaDivisora() {
        System.out.println(CYAN + "═══════════════════════════════════════════════════════" + RESET);
    }
}
