import services.JuegoService;

public class App {
    public static void main(String[] args) throws Exception {
        JuegoService juego = new JuegoService();
        juego.iniciarJuego();
    }
}
