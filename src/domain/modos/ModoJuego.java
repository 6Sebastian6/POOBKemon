package domain.modos;

import domain.Batalla;
import domain.Entrenador;
import java.util.List;

public abstract class ModoJuego {
    protected List<Entrenador> entrenadores;
    protected int rondaActual;
    protected int maxRondas;
    protected boolean juegoTerminado;

    public ModoJuego(List<Entrenador> entrenadores, int maxRondas) {
        this.entrenadores = entrenadores;
        this.maxRondas = maxRondas;
        this.rondaActual = 1;
        this.juegoTerminado = false;
    }

    // Método para iniciar una nueva batalla
    public abstract Batalla iniciarBatalla();

    // Método para procesar el resultado de una batalla
    public abstract void procesarResultadoBatalla(Batalla batalla);

    // Método para verificar si el juego ha terminado
    public abstract boolean verificarFinJuego();

    // Método para obtener el ganador del juego
    public abstract Entrenador obtenerGanador();

    // Método para obtener el puntaje actual de un entrenador
    public abstract int obtenerPuntaje(Entrenador entrenador);

    // Getters útiles
    public int getRondaActual() {
        return rondaActual;
    }

    public int getMaxRondas() {
        return maxRondas;
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }

    public List<Entrenador> getEntrenadores() {
        return entrenadores;
    }

    // Método para curar todos los Pokémon de un entrenador
    protected void curarEquipo(Entrenador entrenador) {
        entrenador.getEquipo().forEach(pokemon -> {
            pokemon.curar(pokemon.getPsMaximo());
            pokemon.curarEstado();
            pokemon.getMovimientos().forEach(movimiento -> movimiento.restaurarPP());
        });
    }
} 