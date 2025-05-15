package domain.estados;

import domain.EstadoPokemon;
import domain.Pokemon;

public class EstadoParalisis extends EstadoPokemon {
    private static final double PROBABILIDAD_NO_MOVERSE = 0.25;
    
    public EstadoParalisis() {
        super("paralizado", -1); // Estado permanente hasta curación
    }
    
    @Override
    public boolean puedeActuar() {
        return Math.random() > PROBABILIDAD_NO_MOVERSE;
    }
    
    @Override
    public void aplicarEfecto(Pokemon pokemon) {
        pokemon.modificarVelocidad(0.5); // Reduce la velocidad a la mitad
    }
} 