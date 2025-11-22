package services;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import domain.Elemento;
import domain.Elemento.TipoAtaque;

/**
 * Servicio principal del juego de batallas elementales.
 * Controla el flujo del juego, gestiona las batallas por turnos,
 * rastrea rachas de victorias y otorga bonus a los elementos.
 */
public class JuegoService {
    private final Scanner sc;
    private final Random random;
    private final String[] nombresElementos = { "fuego", "agua", "aire", "tierra" };

    // Almacena victorias consecutivas por cada elemento
    private final Map<String, Integer> rachas = new HashMap<>();

    // Cache de personajes para mantener mejoras de ataque durante la sesión
    private final Map<String, Elemento> cachePersonajes = new HashMap<>();

    public JuegoService() {
        this.sc = new Scanner(System.in);
        this.random = new Random();
    }

    /**
     * Inicia el bucle principal del juego.
     * Permite jugar múltiples batallas hasta que el jugador decida salir.
     */
    public void iniciarJuego() {
        System.out.println("🔥💧🌪️🌍 Bienvenido al juego de los elementos (modo combate) 🌍🌪️💧🔥");
        boolean continuar = true;

        while (continuar) {
            // Selección de personajes
            Elemento jugador = elegirElementoJugador();
            Elemento enemigo = crearElementoAleatorio();

            // Resetear HP para nueva batalla (mantiene bonus de ataque)
            enemigo.resetSalud();
            jugador.resetSalud();

            System.out.println("Tu personaje: " + jugador);
            System.out.println("Enemigo: " + enemigo);

            // Ejecutar la batalla
            String ganador = loopCombate(jugador, enemigo);

            // Actualizar racha y dar bonus si corresponde
            actualizarRacha(ganador, jugador.getNombre());

            // Preguntar si continuar
            System.out.print("¿Jugar otra batalla? (s/n): ");
            String resp = sc.nextLine().trim().toLowerCase();
            continuar = resp.startsWith("s");
        }

        System.out.println("Gracias por jugar. ¡Hasta la próxima!");
    }

    /**
     * Solicita al jugador que elija un elemento válido.
     * Repite la pregunta hasta obtener una entrada correcta.
     * 
     * @return el elemento elegido por el jugador
     */
    private Elemento elegirElementoJugador() {
        while (true) {
            System.out.print("Elige un elemento (fuego, agua, aire, tierra): ");
            String entrada = sc.nextLine().trim().toLowerCase();
            if (esValido(entrada)) {
                return obtenerPersonaje(entrada);
            }
            System.out.println("Elemento inválido. Intenta de nuevo.");
        }
    }

    /**
     * Valida si un nombre de elemento es correcto.
     * 
     * @param nombre el nombre a validar
     * @return true si el nombre está en la lista de elementos válidos
     */
    private boolean esValido(String nombre) {
        for (String n : nombresElementos) {
            if (n.equals(nombre))
                return true;
        }
        return false;
    }

    /**
     * Crea un elemento aleatorio para el enemigo.
     * 
     * @return elemento enemigo seleccionado aleatoriamente
     */
    private Elemento crearElementoAleatorio() {
        String nombre = nombresElementos[random.nextInt(nombresElementos.length)];
        return obtenerPersonaje(nombre);
    }

    /**
     * Obtiene o crea un personaje elemento.
     * Usa caché para mantener las mejoras de ataque entre batallas.
     * 
     * @param nombre tipo de elemento
     * @return instancia del elemento (nueva o cacheada)
     */
    private Elemento obtenerPersonaje(String nombre) {
        // Caching para mantener mejoras de ataque en la misma sesión
        return cachePersonajes.computeIfAbsent(nombre, n -> new Elemento(n, 50, 12));
    }

    /**
     * Bucle principal de combate por turnos.
     * Alterna turnos entre jugador y enemigo hasta que uno quede sin HP.
     * 
     * @param jugador el elemento del jugador
     * @param enemigo el elemento enemigo
     * @return nombre del elemento ganador
     */
    private String loopCombate(Elemento jugador, Elemento enemigo) {
        while (jugador.estaVivo() && enemigo.estaVivo()) {
            // Turno del jugador
            turno(jugador, enemigo, true);
            if (!enemigo.estaVivo())
                break;

            // Turno del enemigo
            turno(enemigo, jugador, false);

            // Mostrar estado actual
            mostrarBarraVida(jugador, "Jugador");
            mostrarBarraVida(enemigo, "Enemigo");
        }

        // Determinar ganador y mostrar mensaje
        String ganador = jugador.estaVivo() ? jugador.getNombre() : enemigo.getNombre();
        if (jugador.estaVivo()) {
            System.out.println("🏆 ¡Ganaste la batalla!");
        } else {
            System.out.println("💀 Has sido derrotado...");
        }
        return ganador;
    }

    /**
     * Ejecuta un turno de ataque.
     * 
     * @param atacante  el elemento que ataca
     * @param defensor  el elemento que recibe el ataque
     * @param esJugador true si el atacante es el jugador
     */
    private void turno(Elemento atacante, Elemento defensor, boolean esJugador) {
        TipoAtaque tipo = elegirTipoAtaque(esJugador);
        int danio = atacante.calcularDanioContra(defensor, tipo);
        defensor.recibirDanio(danio);

        String etiquetaAtacante = esJugador ? "Tu" : "El";
        if (danio == 0) {
            System.out.println(etiquetaAtacante + " ataque " + tipo.name().toLowerCase() + " falló!");
        } else {
            System.out.println(etiquetaAtacante + " " + tipo.name().toLowerCase() + " hace " + danio + " de daño.");
        }
    }

    /**
     * Determina el tipo de ataque a usar.
     * Si es jugador, solicita entrada. Si es IA, elige aleatoriamente.
     * 
     * @param esJugador true si es turno del jugador
     * @return tipo de ataque elegido
     */
    private TipoAtaque elegirTipoAtaque(boolean esJugador) {
        if (!esJugador) {
            // IA elige aleatoriamente
            return random.nextDouble() < 0.5 ? TipoAtaque.RAPIDO : TipoAtaque.ELEMENTAL;
        }

        // Jugador elige manualmente
        while (true) {
            System.out.print("Elige ataque (r = rápido, e = elemental): ");
            String entrada = sc.nextLine().trim().toLowerCase();
            switch (entrada) {
                case "r":
                    return TipoAtaque.RAPIDO;
                case "e":
                    return TipoAtaque.ELEMENTAL;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    /**
     * Muestra una barra visual de vida en la consola.
     * 
     * @param elemento el elemento cuya vida se mostrará
     * @param etiqueta texto descriptivo (ej: "Jugador", "Enemigo")
     */
    private void mostrarBarraVida(Elemento elemento, String etiqueta) {
        int anchura = 20;
        double ratio = (double) elemento.getHp() / elemento.getMaxHp();
        int llenos = (int) Math.round(ratio * anchura);

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < anchura; i++) {
            sb.append(i < llenos ? '#' : '-');
        }
        sb.append(']');

        System.out.println(etiqueta + " Vida " + sb + " " + elemento.getHp() + "/" + elemento.getMaxHp());
    }

    /**
     * Actualiza la racha de victorias del jugador y otorga bonus.
     * - 3 victorias consecutivas: +3 ataque permanente
     * - Cada 5 victorias adicionales: +2 ataque
     * - Perder reinicia la racha a 0
     * 
     * @param ganador       nombre del elemento ganador
     * @param nombreJugador nombre del elemento del jugador
     */
    private void actualizarRacha(String ganador, String nombreJugador) {
        boolean jugadorGano = ganador.equals(nombreJugador);
        int rachaActual = rachas.getOrDefault(nombreJugador, 0);

        if (jugadorGano) {
            rachaActual++;
            rachas.put(nombreJugador, rachaActual);

            // Bonus al alcanzar 3 victorias
            if (rachaActual == 3) {
                Elemento personaje = cachePersonajes.get(nombreJugador);
                personaje.aumentarAtaque(3);
                System.out.println("🔥 BONUS! " + nombreJugador
                        + " alcanza 3 victorias seguidas. Ataque +3 permanente en esta sesión.");
            }
            // Bonus cada 5 victorias adicionales
            else if (rachaActual > 3 && rachaActual % 5 == 0) {
                Elemento personaje = cachePersonajes.get(nombreJugador);
                personaje.aumentarAtaque(2);
                System.out.println("✨ Racha " + rachaActual + "! Ataque adicional +2.");
            }
        } else {
            // Perdió: resetear racha
            if (rachaActual >= 3) {
                System.out.println("La racha de " + rachaActual + " se rompió.");
            }
            rachas.put(nombreJugador, 0);
        }
    }
}
