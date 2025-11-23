package services;

import domain.EntidadJuego;
import domain.ModoJuego;
import ui.UIService;

public class ResultadoService {
    private final UIService uiService;

    public ResultadoService() {
        this.uiService = new UIService();
    }
    
    public void mostrarResultadoVsComputadora(EntidadJuego jugador, EntidadJuego computadora, ModoJuego modoJuego) {
        if (jugador == null || computadora == null) {
            uiService.mostrarError("Elementos nulos");
            return;
        }

        if (jugador.getNombre().equals(computadora.getNombre())) {
            uiService.mostrarEmpate();
            modoJuego.getJugador1().agregarEmpate();
            modoJuego.getJugador2().agregarEmpate();
        } else if (jugador.ganaA(computadora)) {
            uiService.mostrarVictoria("¡Ganaste!", jugador.getNombre());
            modoJuego.getJugador1().agregarVictoria();
            modoJuego.getJugador2().agregarDerrota();
        } else {
            uiService.mostrarDerrota(jugador.getNombre(), computadora.getNombre(), computadora.getNombre());
            modoJuego.getJugador1().agregarDerrota();
            modoJuego.getJugador2().agregarVictoria();
        }
    }

    public void mostrarResultadoJugadorVsJugador(EntidadJuego jugador1, EntidadJuego jugador2, ModoJuego modoJuego) {
        if (jugador1 == null || jugador2 == null) {
            uiService.mostrarError("Elementos nulos");
            return;
        }

        if (jugador1.getNombre().equals(jugador2.getNombre())) {
            uiService.mostrarEmpate();
            modoJuego.getJugador1().agregarEmpate();
            modoJuego.getJugador2().agregarEmpate();
        } else if (jugador1.ganaA(jugador2)) {
            uiService.mostrarVictoria(modoJuego.getJugador1().getNombre(), jugador1.getNombre());
            modoJuego.getJugador1().agregarVictoria();
            modoJuego.getJugador2().agregarDerrota();
        } else {
            uiService.mostrarVictoria(modoJuego.getJugador2().getNombre(), jugador2.getNombre());
            modoJuego.getJugador1().agregarDerrota();
            modoJuego.getJugador2().agregarVictoria();
        }
    }

    public void mostrarGanador(ModoJuego modoJuego) {
        try {
            uiService.mostrarResultadoFinal();
            int victorias1 = modoJuego.getJugador1().getVictorias();
            int victorias2 = modoJuego.getJugador2().getVictorias();
            
            if (victorias1 > victorias2) {
                uiService.mostrarGanadorFinal(modoJuego.getJugador1().getNombre());
            } else if (victorias2 > victorias1) {
                uiService.mostrarGanadorFinal(modoJuego.getJugador2().getNombre());
            } else {
                uiService.mostrarEmpateGeneral();
            }
            
            uiService.mostrarEstadisticas(modoJuego.getJugador1().getNombre(), 
                                         modoJuego.getJugador1().getVictorias(),
                                         modoJuego.getJugador1().getDerrotas(),
                                         modoJuego.getJugador1().getEmpates(),
                                         modoJuego.getJugador1().getTotalJuegos());
            
            uiService.mostrarEstadisticas(modoJuego.getJugador2().getNombre(), 
                                         modoJuego.getJugador2().getVictorias(),
                                         modoJuego.getJugador2().getDerrotas(),
                                         modoJuego.getJugador2().getEmpates(),
                                         modoJuego.getJugador2().getTotalJuegos());
        } catch (NullPointerException e) {
            uiService.mostrarError("No se pudo mostrar el ganador");
        }
    }
}
