package ui.controllers;

import domain.Elemento;
import domain.Jugador;
import domain.ModoJuego;
import ui.utils.ElementUtils;
import java.util.Random;

/**
 * Controlador que maneja la lógica del torneo.
 */
public class TournamentController {
    private final ModoJuego modoJuego;
    private final boolean isTwoPlayers;
    private final Random random = new Random();

    private String player1Choice; // Almacenar elección del jugador 1 en modo 2 jugadores
    private String currentPlayerTurn;

    public TournamentController(String player1Name, String player2Name, int rounds)
            throws Exception {
        isTwoPlayers = (player2Name != null);

        if (isTwoPlayers) {
            modoJuego = new ModoJuego(player1Name, player2Name, rounds);
            currentPlayerTurn = player1Name;
        } else {
            modoJuego = new ModoJuego(player1Name, rounds, true);
        }
    }

    public boolean isTwoPlayersMode() {
        return isTwoPlayers;
    }

    public ModoJuego getModoJuego() {
        return modoJuego;
    }

    public String getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }

    public boolean isWaitingForPlayer1Choice() {
        return isTwoPlayers && player1Choice == null;
    }

    /**
     * Procesa la elección de un elemento.
     * Retorna true si se debe ejecutar la ronda, false si solo se guardó la
     * elección.
     */
    public boolean processElementSelection(String elementName) {
        if (isWaitingForPlayer1Choice()) {
            player1Choice = elementName;
            currentPlayerTurn = modoJuego.getJugador2().getNombre();
            return false; // No ejecutar aún, esperar al jugador 2
        }
        return true; // Ejecutar ronda
    }

    /**
     * Ejecuta una ronda del torneo.
     * Retorna un objeto con el resultado de la ronda.
     */
    public RoundResult executeRound(String player2ElementName) {
        try {
            String player1ElementName = isTwoPlayers ? player1Choice : player2ElementName;

            Elemento player1Element = new Elemento(player1ElementName);
            Elemento player2Element;

            if (isTwoPlayers) {
                player2Element = new Elemento(player2ElementName);
            } else {
                String[] elementos = { "fuego", "agua", "aire", "tierra" };
                String cpuChoice = elementos[random.nextInt(elementos.length)];
                player2Element = new Elemento(cpuChoice);
            }

            // Determinar ganador usando la utilidad
            String resultado = ElementUtils.determinarGanador(
                    player1Element.getNombre(),
                    player2Element.getNombre());

            // Actualizar estadísticas
            updateStats(resultado);

            return new RoundResult(
                    player1Element.getNombre(),
                    player2Element.getNombre(),
                    resultado,
                    modoJuego.getRondaActual());

        } catch (Exception e) {
            throw new RuntimeException("Error al ejecutar la ronda: " + e.getMessage(), e);
        }
    }

    private void updateStats(String resultado) {
        if (resultado.equals("jugador1")) {
            modoJuego.getJugador1().agregarVictoria();
            if (isTwoPlayers) {
                modoJuego.getJugador2().agregarDerrota();
            }
        } else if (resultado.equals("jugador2")) {
            if (isTwoPlayers) {
                modoJuego.getJugador2().agregarVictoria();
                modoJuego.getJugador1().agregarDerrota();
            } else {
                modoJuego.getJugador1().agregarDerrota();
            }
        } else {
            modoJuego.getJugador1().agregarEmpate();
            if (isTwoPlayers) {
                modoJuego.getJugador2().agregarEmpate();
            }
        }
    }

    public void nextRound() {
        modoJuego.avanzarRonda();
        player1Choice = null;
        if (isTwoPlayers) {
            currentPlayerTurn = modoJuego.getJugador1().getNombre();
        }
    }

    public boolean hasMoreRounds() {
        return modoJuego.hayRondasPendientes();
    }

    public TournamentResult getFinalResult() {
        Jugador j1 = modoJuego.getJugador1();
        Jugador j2 = modoJuego.getJugador2();

        int player1Score = j1.getVictorias();
        int player2Score = isTwoPlayers ? j2.getVictorias() : j1.getDerrotas();

        String winner;
        if (player1Score > player2Score) {
            winner = j1.getNombre();
        } else if (player1Score < player2Score) {
            winner = isTwoPlayers ? j2.getNombre() : "CPU";
        } else {
            winner = "empate";
        }

        return new TournamentResult(
                j1.getNombre(),
                isTwoPlayers ? j2.getNombre() : "CPU",
                player1Score,
                player2Score,
                j1.getEmpates(),
                winner);
    }

    /**
     * Clase que representa el resultado de una ronda.
     */
    public static class RoundResult {
        public final String player1Element;
        public final String player2Element;
        public final String winner; // "jugador1", "jugador2" o "empate"
        public final int roundNumber;

        public RoundResult(String player1Element, String player2Element, String winner, int roundNumber) {
            this.player1Element = player1Element;
            this.player2Element = player2Element;
            this.winner = winner;
            this.roundNumber = roundNumber;
        }
    }

    /**
     * Clase que representa el resultado final del torneo.
     */
    public static class TournamentResult {
        public final String player1Name;
        public final String player2Name;
        public final int player1Score;
        public final int player2Score;
        public final int draws;
        public final String winner; // nombre del ganador o "empate"

        public TournamentResult(String player1Name, String player2Name,
                int player1Score, int player2Score, int draws, String winner) {
            this.player1Name = player1Name;
            this.player2Name = player2Name;
            this.player1Score = player1Score;
            this.player2Score = player2Score;
            this.draws = draws;
            this.winner = winner;
        }
    }
}
