package domain;

public interface ModoJuego {
    void iniciarBatalla();
    void procesarTurno();
    boolean haTerminado();
    Entrenador obtenerGanador();
    void finalizarBatalla();
} 