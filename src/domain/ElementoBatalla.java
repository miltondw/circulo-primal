package domain;

import java.util.Random;

/**
 * Representa un elemento en modo batalla con estadísticas de combate.
 * Esta clase es utilizada por la interfaz gráfica para las batallas por turnos.
 */
public class ElementoBatalla {
    private final String nombre;
    private final int maxHp;
    private int hp;
    private int ataqueBase;
    private final Random rng = new Random();

    /**
     * Constructor completo con estadísticas personalizadas
     */
    public ElementoBatalla(String nombre, int maxHp, int ataqueBase) {
        this.nombre = nombre.toLowerCase();
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
     * Aumenta el ataque base del elemento (usado para bonos por racha)
     */
    public void aumentarAtaque(int bonus) {
        this.ataqueBase += Math.max(0, bonus);
    }

    /**
     * Restaura la salud del elemento a su máximo
     */
    public void resetSalud() {
        this.hp = this.maxHp;
    }

    /**
     * Verifica si el elemento sigue con vida
     */
    public boolean estaVivo() {
        return hp > 0;
    }

    /**
     * Aplica daño al elemento
     */
    public void recibirDanio(int danio) {
        this.hp = Math.max(0, this.hp - Math.max(0, danio));
    }

    /**
     * Calcula el daño que este elemento hace contra un oponente
     * 
     * @param oponente El elemento oponente
     * @param tipo     El tipo de ataque a realizar
     * @return El daño calculado (0 si falla)
     */
    public int calcularDanioContra(ElementoBatalla oponente, TipoAtaque tipo) {
        // Variación pequeña para que no sea uniforme
        int variacion = rng.nextInt(6); // 0..5
        double base = this.ataqueBase + variacion;

        // bonificador por tipo de ataque
        double modificadorAtaque = 1.0;
        double probAcierto = 1.0;
        switch (tipo) {
            case ELEMENTAL:
                modificadorAtaque = 1.2; // más fuerte
                probAcierto = 0.8; // pero puede fallar
                break;
            case RAPIDO:
            default:
                modificadorAtaque = 1.0;
                probAcierto = 1.0;
        }

        // chequeo de acierto
        if (rng.nextDouble() > probAcierto) {
            return 0; // falló
        }

        // efectividad elemental
        double efectividad = 1.0;
        if (this.ganaA(oponente)) {
            efectividad = 1.5;
        } else if (oponente.ganaA(this)) {
            efectividad = 0.75;
        }

        int danio = (int) Math.round(base * modificadorAtaque * efectividad);
        return Math.max(1, danio); // al menos 1
    }

    @Override
    public String toString() {
        return "ElementoBatalla{" +
                "nombre='" + nombre + '\'' +
                ", hp=" + hp + "/" + maxHp +
                ", atk=" + ataqueBase +
                '}';
    }

    /**
     * Determina si este elemento tiene ventaja sobre otro
     */
    public boolean ganaA(ElementoBatalla otro) {
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
     * Enum que define los tipos de ataque disponibles
     */
    public enum TipoAtaque {
        RAPIDO, // 100% precisión, daño normal
        ELEMENTAL // 80% precisión, +20% daño
    }
}
