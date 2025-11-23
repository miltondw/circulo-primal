package services;

import domain.Elemento;
import domain.ModoJuego;
import exceptions.ElementoInvalidoException;
import exceptions.NombreInvalidoException;
import ui.UIService;
import java.util.Random;

public class RondaService {
    private final Random random;
    private final String[] elementos = { "fuego", "agua", "aire", "tierra" };
    private final InputService inputService;
    private final ResultadoService resultadoService;
    private final UIService uiService;

    public RondaService(InputService inputService, ResultadoService resultadoService) {
        this.random = new Random();
        this.inputService = inputService;
        this.resultadoService = resultadoService;
        this.uiService = new UIService();
    }

    public void jugarContraComputadora(ModoJuego modoJuego) {
        while (modoJuego.hayRondasPendientes()) {
            try {
                uiService.mostrarRonda(modoJuego.getRondaActual(), modoJuego.getRondas());
                
                int opcion = seleccionarElemento(modoJuego.getJugador1().getNombre() + ", selecciona (1-4): ");
                
                String eleccionJugador = obtenerElementoPorNumero(opcion);
                Elemento elementoJugador = new Elemento(eleccionJugador);
                Elemento elementoComputadora = new Elemento(elementos[random.nextInt(elementos.length)]);

                System.out.println();
                System.out.println("Tu elección: 🎯 " + elementoJugador.getNombre().toUpperCase());
                System.out.println("Computadora: 🤖 " + elementoComputadora.getNombre().toUpperCase());

                uiService.mostrarResultadosRonda();
                resultadoService.mostrarResultadoVsComputadora(elementoJugador, elementoComputadora, modoJuego);
                modoJuego.avanzarRonda();
            } catch (ElementoInvalidoException | NombreInvalidoException e) {
                uiService.mostrarError(e.getMessage());
            }
        }
    }

    public void jugarContraJugador(ModoJuego modoJuego) {
        while (modoJuego.hayRondasPendientes()) {
            try {
                uiService.mostrarRonda(modoJuego.getRondaActual(), modoJuego.getRondas());
                
                // Primer jugador elige
                System.out.println("🎮 Turno de " + modoJuego.getJugador1().getNombre());
                int opcion1 = seleccionarElemento("Selecciona (1-4): ");
                String eleccionJugador1 = obtenerElementoPorNumero(opcion1);
                
                // Limpiar pantalla y ocultar selección
                inputService.limpiarPantalla();
                uiService.mostrarSeleccionRegistrada();
                
                // Segundo jugador elige
                System.out.println("🎮 Turno de " + modoJuego.getJugador2().getNombre());
                int opcion2 = seleccionarElemento("Selecciona (1-4): ");
                String eleccionJugador2 = obtenerElementoPorNumero(opcion2);
                
                // Limpiar pantalla y mostrar ambas selecciones
                inputService.limpiarPantalla();
                Elemento elementoJugador1 = new Elemento(eleccionJugador1);
                Elemento elementoJugador2 = new Elemento(eleccionJugador2);

                uiService.mostrarResultadosRonda();
                System.out.println(modoJuego.getJugador1().getNombre() + " eligió: " + elementoJugador1.getNombre().toUpperCase());
                System.out.println(modoJuego.getJugador2().getNombre() + " eligió: " + elementoJugador2.getNombre().toUpperCase());
                System.out.println();

                resultadoService.mostrarResultadoJugadorVsJugador(elementoJugador1, elementoJugador2, modoJuego);
                modoJuego.avanzarRonda();
            } catch (ElementoInvalidoException | NombreInvalidoException e) {
                uiService.mostrarError(e.getMessage());
            }
        }
    }

    private int seleccionarElemento(String mensaje) {
        inputService.getUIService().mostrarMenuElementos();
        return inputService.obtenerOpcionElemento(mensaje);
    }

    private String obtenerElementoPorNumero(int opcion) {
        switch (opcion) {
            case 1:
                return "fuego";
            case 2:
                return "agua";
            case 3:
                return "aire";
            case 4:
                return "tierra";
            default:
                return "fuego";
        }
    }
}
