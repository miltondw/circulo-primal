import services.JuegoService;

/**
 * Punto de entrada principal del juego Círculo Primal.
 * Inicializa el servicio de juego y arranca el bucle de batallas elementales.
 */
public class App {
    /**
     * Método main - crea una instancia del servicio de juego y lo inicia.
     * 
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) throws Exception {
        JuegoService juego = new JuegoService();
        juego.iniciarJuego();
    }
}
