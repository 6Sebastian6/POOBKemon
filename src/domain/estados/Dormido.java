package domain.estados;

import domain.EstadoPokemon;
import domain.Pokemon;
import java.util.Random;

public class Dormido extends EstadoPokemon {
    private static Random random = new Random();

    public Dormido() {
        super("Dormido", 1 + random.nextInt(3)); // Dura entre 1 y 3 turnos
    }

    @Override
    public boolean puedeActuar() {
        return false; // Pokémon dormido no puede actuar
    }

    @Override
    public void aplicarEfecto(Pokemon pokemon) {
        // No hay efecto adicional mientras está dormido
    }
} 