package domain.estados;

import domain.EstadoPokemon;
import domain.Pokemon;
import java.util.Random;

public class Congelado extends EstadoPokemon {
    private static final double PROBABILIDAD_DESCONGELAR = 0.2; // 20% de probabilidad de descongelarse
    private static Random random = new Random();

    public Congelado() {
        super("Congelado", -1); // Estado permanente hasta descongelarse o curación
    }

    @Override
    public boolean puedeActuar() {
        // Verificar si se descongela
        if (random.nextDouble() < PROBABILIDAD_DESCONGELAR) {
            System.out.println("¡El Pokémon se ha descongelado!");
            return true;
        }
        return false;
    }

    @Override
    public void aplicarEfecto(Pokemon pokemon) {
        // El congelamiento no causa daño, solo impide moverse
        if (!puedeActuar()) {
            System.out.println(pokemon.getNombre() + " está congelado y no puede moverse!");
        }
    }

    @Override
    public boolean actualizarDuracion() {
        // Si puede actuar (se descongeló), el estado termina
        return puedeActuar();
    }
} 