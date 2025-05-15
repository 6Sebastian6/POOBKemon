package domain.estados;

import domain.EstadoPokemon;
import domain.Pokemon;
import java.util.Random;

public class Paralizado extends EstadoPokemon {
    private static final double PROBABILIDAD_NO_MOVERSE = 0.25;
    private static Random random = new Random();

    public Paralizado() {
        super("Paralizado", -1); // Estado permanente hasta curación
    }

    @Override
    public boolean puedeActuar() {
        return random.nextDouble() >= PROBABILIDAD_NO_MOVERSE;
    }

    @Override
    public void aplicarEfecto(Pokemon pokemon) {
        // La parálisis reduce la velocidad a la mitad
        pokemon.modificarVelocidad(0.5);
    }
} 