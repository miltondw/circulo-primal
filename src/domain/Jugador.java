package domain;

import exceptions.NombreInvalidoException;

public class Jugador extends EntidadJuego {
    private int victorias;
    private int derrotas;
    private int empates;

    public Jugador(String nombre) throws NombreInvalidoException {
        super(nombre);
        this.victorias = 0;
        this.derrotas = 0;
        this.empates = 0;
    }

    public void agregarVictoria() {
        this.victorias++;
    }

    public void agregarDerrota() {
        this.derrotas++;
    }

    public void agregarEmpate() {
        this.empates++;
    }

    public int getVictorias() {
        return victorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public int getEmpates() {
        return empates;
    }

    public int getTotalJuegos() {
        return victorias + derrotas + empates;
    }

    public void mostrarEstadisticas() {
        System.out.println("\n📊 Estadísticas de " + nombre + ":");
        System.out.println("🏆 Victorias: " + victorias);
        System.out.println("💀 Derrotas: " + derrotas);
        System.out.println("🤝 Empates: " + empates);
        System.out.println("📈 Total juegos: " + getTotalJuegos());
    }

    @Override
    public String toString() {
        return "Jugador [nombre=" + nombre + ", victorias=" + victorias + ", derrotas=" + derrotas + ", empates=" + empates + "]";
    }
}
