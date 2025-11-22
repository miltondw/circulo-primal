package domain;

import java.util.Random;

/**
 * Representa un elemento (personaje) en el juego de batallas.
 * Cada elemento tiene tipo (fuego, agua, aire, tierra), estadísticas de combate
 * y relaciones de efectividad contra otros elementos.
 */
public class Elemento {
    private final String nombre; // Tipo de elemento: fuego, agua, aire, tierra
    private final int maxHp; // Puntos de vida máximos
    private int hp; // Puntos de vida actuales
    private int ataqueBase; // Poder de ataque base (puede aumentar con rachas)
    private final Random rng = new Random();

    /**
     * Constructor del elemento.
     * 
     * @param nombre     tipo de elemento (fuego, agua, aire, tierra)
     * @param maxHp      puntos de vida máximos
     * @param ataqueBase poder de ataque inicial
     */
    public Elemento(String nombre, int maxHp, int ataqueBase) {
        this.nombre = nombre;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.ataqueBase = ataqueBase;
    }

    public String getNombre() {
        return nombre;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAtaqueBase() {
        return ataqueBase;
    }

    /**
     * Aumenta el ataque base (usado para bonus de rachas).
     * 
     * @param bonus cantidad a agregar al ataque
     */
    public void aumentarAtaque(int bonus) {
        this.ataqueBase += Math.max(0, bonus);
    }

    /**
     * Restaura la vida al máximo (antes de cada batalla).
     */
    public void resetSalud() {
        this.hp = this.maxHp;
    }

    /**
     * Verifica si el elemento aún está vivo.
     * 
     * @return true si hp > 0
     */
    public boolean estaVivo() {
        return hp > 0;
    }

    /**
     * Reduce la vida del elemento.
     * 
     * @param danio cantidad de daño a recibir
     */
    public void recibirDanio(int danio) {
        this.hp = Math.max(0, this.hp - Math.max(0, danio));
    }

    /**
     * Calcula el daño que este elemento inflige a un oponente.
     * Considera: tipo de ataque, efectividad elemental y variación aleatoria.
     * 
     * @param oponente el elemento enemigo
     * @param tipo     tipo de ataque (RAPIDO o ELEMENTAL)
     * @return daño causado (0 si el ataque falla)
     */
    public int calcularDanioContra(Elemento oponente, TipoAtaque tipo) {
        // Variación pequeña para que no sea uniforme
        int variacion = rng.nextInt(6); // 0..5
        double base = this.ataqueBase + variacion;

        // Bonificador por tipo de ataque
        double modificadorAtaque = 1.0;
        double probAcierto = 1.0;
        switch (tipo) {
            case ELEMENTAL:
                modificadorAtaque = 1.2; // más fuerte (+20% daño)
                probAcierto = 0.8; // pero puede fallar (80% acierto)
                break;
            case RAPIDO:
            default:
                modificadorAtaque = 1.0; // daño normal
                probAcierto = 1.0; // siempre acierta
        }

        // Chequeo de acierto
        if (rng.nextDouble() > probAcierto) {
            return 0; // el ataque falló
        }

        // Efectividad elemental (ventaja/desventaja de tipo)
        double efectividad = 1.0;
        if (this.ganaA(oponente)) {
            efectividad = 1.5; // super efectivo
        } else if (oponente.ganaA(this)) {
            efectividad = 0.75; // poco efectivo
        }

        int danio = (int) Math.round(base * modificadorAtaque * efectividad);
        return Math.max(1, danio); // al menos 1 de daño
    }

    @Override
    public String toString() {
        return "Elemento{" +
                "nombre='" + nombre + '\'' +
                ", hp=" + hp + "/" + maxHp +
                ", atk=" + ataqueBase +
                '}';
    }

    /**
     * Determina si este elemento tiene ventaja de tipo sobre otro.
     * Relación circular: fuego > tierra > aire > agua > fuego
     * 
     * @param otro el elemento enemigo
     * @return true si este elemento gana al otro
     */
    public boolean ganaA(Elemento otro) {
        switch (nombre) {
            case "fuego":
                return otro.nombre.equals("tierra");
            case "agua":
                return otro.nombre.equals("fuego");
            case "aire":
                return otro.nombre.equals("agua");
            case "tierra":
                return otro.nombre.equals("aire");
            default:
                return false;
        }
    }

    /**
     * Tipos de ataque disponibles en combate.
     * RAPIDO: siempre acierta, daño normal.
     * ELEMENTAL: puede fallar, pero hace más daño.
     */
    public enum TipoAtaque {
        RAPIDO, ELEMENTAL
    }
}
