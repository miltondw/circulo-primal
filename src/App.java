import services.JuegoService;

public class App {
    public static void main(String[] args) {
        JuegoService juego = null;
        try {
            juego = new JuegoService();
            boolean seguirJugando = true;
            
            while (seguirJugando) {
                juego.iniciarJuego();
                seguirJugando = juego.preguntarParaVolverAJugar();
            }
        } catch (Exception e) {
            System.err.println("❌ Error fatal: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (juego != null) {
                juego.cerrarScanner();
            }
            System.out.println("\n👋 ¡Gracias por jugar!");
        }
    }
}

