package services;

import java.util.Scanner;

import domain.ModoJuego;
import exceptions.NombreInvalidoException;
import ui.UIService;

public class JuegoService {
    private final Scanner sc;
    private final InputService inputService;
    private final ResultadoService resultadoService;
    private final RondaService rondaService;
    private final UIService uiService;

    public JuegoService() {
        this.sc = new Scanner(System.in);
        this.inputService = new InputService(sc);
        this.resultadoService = new ResultadoService();
        this.rondaService = new RondaService(inputService, resultadoService);
        this.uiService = new UIService();
    }

    public void iniciarJuego() {
        try {
            uiService.mostrarBienvenida();
            
            int tipoJuego = inputService.seleccionarTipoJuego();
            
            String nombreJugador1 = inputService.obtenerNombreJugador("¿Cuál es tu nombre? ");
            int rondas = inputService.obtenerNumeroRondas("¿Cuántas rondas deseas jugar? ");

            ModoJuego modoJuego;
            
            if (tipoJuego == 1) {
                modoJuego = new ModoJuego(nombreJugador1, rondas, true);
                rondaService.jugarContraComputadora(modoJuego);
            } else {
                String nombreJugador2 = inputService.obtenerNombreJugador("¿Cuál es el nombre del segundo jugador? ");
                modoJuego = new ModoJuego(nombreJugador1, nombreJugador2, rondas);
                rondaService.jugarContraJugador(modoJuego);
            }
            
            resultadoService.mostrarGanador(modoJuego);
            
        } catch (NombreInvalidoException e) {
            uiService.mostrarError("Nombre inválido: " + e.getMessage());
        } catch (NumberFormatException e) {
            uiService.mostrarError("Por favor ingresa números válidos");
        } catch (Exception e) {
            uiService.mostrarError("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public boolean preguntarParaVolverAJugar() {
        return inputService.preguntarParaVolverAJugar();
    }

    public void cerrarScanner() {
        try {
            if (sc != null) {
                sc.close();
            }
        } catch (Exception e) {
            uiService.mostrarError("Error al cerrar el scanner: " + e.getMessage());
        }
    }
}
