package domain.modos;

import domain.Batalla;
import domain.Entrenador;
import java.util.List;

public class ModoNormal extends ModoJuego {
    
    public ModoNormal(List<Entrenador> entrenadores) {
        // En modo normal solo hay una ronda
        super(entrenadores, 1);
    }

    @Override
    public Batalla iniciarBatalla() {
        // En modo normal, los entrenadores ya deben tener sus equipos seleccionados
        Entrenador entrenador1 = entrenadores.get(0);
        Entrenador entrenador2 = entrenadores.get(1);
        return new Batalla(entrenador1, entrenador2);
    }

    @Override
    public void procesarResultadoBatalla(Batalla batalla) {
        // La batalla termina inmediatamente después de un ganador
        juegoTerminado = true;
    }

    @Override
    public boolean verificarFinJuego() {
        // El juego termina cuando hay un ganador
        return juegoTerminado;
    }

    @Override
    public Entrenador obtenerGanador() {
        return entrenadores.stream()
            .filter(e -> e.tienePokemonesVivos())
            .findFirst()
            .orElse(null);
    }

    @Override
    public int obtenerPuntaje(Entrenador entrenador) {
        // En modo normal no hay sistema de puntaje, solo ganador y perdedor
        return entrenador.tienePokemonesVivos() ? 1 : 0;
    }
} 