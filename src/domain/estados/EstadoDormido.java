package domain.estados;

import domain.EstadoPokemon;
import domain.Pokemon;

public class EstadoDormido extends EstadoPokemon {
    private static final double PROBABILIDAD_DESPERTAR = 0.33; // 33% de probabilidad de despertar cada turno
    
    public EstadoDormido() {
        super("dormido", 3); // Dura máximo 3 turnos
    }
    
    @Override
    public boolean puedeActuar() {
        return false; // No puede actuar mientras está dormido
    }
    
    @Override
    public void aplicarEfecto(Pokemon pokemon) {
        // No hay efecto adicional mientras duerme
        System.out.println(pokemon.getNombre() + " está dormido!");
    }
    
    @Override
    public boolean actualizarDuracion() {
        if (Math.random() < PROBABILIDAD_DESPERTAR || duracion == 1) {
            return true; // El Pokémon despierta
        }
        duracion--;
        return false;
    }
} 