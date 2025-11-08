package services;

import java.util.Random;
import java.util.Scanner;

import domain.Elemento;

public class JuegoService {
    private final Scanner sc;
    private final Random random;
    private final String[] elementos = { "fuego", "agua", "aire", "tierra" };

    public JuegoService() {
        this.sc = new Scanner(System.in);
        this.random = new Random();
    }

    public void iniciarJuego() {
        System.out.println("🔥💧🌪️🌍 Bienvenido al juego de los elementos 🌍🌪️💧🔥");
        System.out.print("Elige un elemento (fuego, agua, aire o tierra): ");
        String eleccionJugador = sc.nextLine().toLowerCase();

        Elemento jugador = new Elemento(eleccionJugador);
        Elemento computadora = new Elemento(elementos[random.nextInt(elementos.length)]);

        System.out.println("Tú elegiste: " + jugador.getNombre());
        System.out.println("La computadora eligió: " + computadora.getNombre());

        mostrarResultado(jugador, computadora);
    }

    private void mostrarResultado(Elemento jugador, Elemento computadora) {
        if (jugador.getNombre().equals(computadora.getNombre())) {
            System.out.println("🤝 ¡Empate!");
        } else if (jugador.ganaA(computadora)) {
            System.out.println("🏆 ¡Ganaste! " + jugador.getNombre() + " vence a " + computadora.getNombre());
        } else {
            System.out.println("💀 Perdiste... " + computadora.getNombre() + " vence a " + jugador.getNombre());
        }
    }
}
