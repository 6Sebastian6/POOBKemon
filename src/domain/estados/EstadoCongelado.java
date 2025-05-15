package domain.estados;

import domain.EstadoPokemon;
import domain.Pokemon;

public class EstadoCongelado extends EstadoPokemon {
    private static final double PROBABILIDAD_DESCONGELAR = 0.20; // 20% de probabilidad de descongelarse cada turno
    
    public EstadoCongelado() {
        super("congelado", -1); // Dura hasta que se descongele
    }
    
    @Override
    public boolean puedeActuar() {
        return false; // No puede actuar mientras está congelado
    }
    
    @Override
    public void aplicarEfecto(Pokemon pokemon) {
        // No hay efecto adicional mientras está congelado
        System.out.println(pokemon.getNombre() + " está congelado!");
    }
    
    @Override
    public boolean actualizarDuracion() {
        return Math.random() < PROBABILIDAD_DESCONGELAR; // Retorna true si se descongela
    }
} 