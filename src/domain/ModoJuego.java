package domain;

import exceptions.NombreInvalidoException;

public class ModoJuego {
    private Jugador jugador1;
    private Jugador jugador2;
    private int rondas;
    private int rondaActual;
    private boolean esJugador2Real;

    public ModoJuego(String nombreJugador1, int rondas, boolean esJugador2Real) 
            throws NombreInvalidoException, IllegalArgumentException {
        if (rondas <= 0) {
            throw new IllegalArgumentException("El número de rondas debe ser mayor a 0");
        }
        this.jugador1 = new Jugador(nombreJugador1);
        this.rondas = rondas;
        this.rondaActual = 1;
        this.esJugador2Real = esJugador2Real;
        
        if (esJugador2Real) {
            this.jugador2 = new Jugador("Computadora");
        }
    }

    public ModoJuego(String nombreJugador1, String nombreJugador2, int rondas) 
            throws NombreInvalidoException, IllegalArgumentException {
        if (rondas <= 0) {
            throw new IllegalArgumentException("El número de rondas debe ser mayor a 0");
        }
        this.jugador1 = new Jugador(nombreJugador1);
        this.jugador2 = new Jugador(nombreJugador2);
        this.rondas = rondas;
        this.rondaActual = 1;
        this.esJugador2Real = true;
    }

    public Jugador getJugador1() {
        return jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }

    public int getRondas() {
        return rondas;
    }

    public int getRondaActual() {
        return rondaActual;
    }

    public boolean esJugador2Real() {
        return esJugador2Real;
    }

    public void avanzarRonda() {
        this.rondaActual++;
    }

    public boolean hayRondasPendientes() {
        return rondaActual <= rondas;
    }

    public void mostrarEstadoJuego() {
        System.out.println("\n📋 Estado del juego:");
        System.out.println("Jugador 1: " + jugador1.getNombre());
        System.out.println("Jugador 2: " + jugador2.getNombre());
        System.out.println("Ronda: " + rondaActual + " de " + rondas);
        System.out.println("\n--- Estadísticas Finales ---");
        jugador1.mostrarEstadisticas();
        jugador2.mostrarEstadisticas();
    }
}
