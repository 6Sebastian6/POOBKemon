package domain.estados;

import domain.EstadoPokemon;
import domain.Pokemon;

public class Quemado extends EstadoPokemon {
    private static final double DANIO_PORCENTUAL = 0.0625; // 1/16 de la vida máxima
    private static final double REDUCCION_ATAQUE = 0.5; // Reduce el ataque a la mitad

    public Quemado() {
        super("Quemado", -1); // Estado permanente hasta curación
    }

    @Override
    public boolean puedeActuar() {
        return true; // La quemadura no impide actuar
    }

    @Override
    public void aplicarEfecto(Pokemon pokemon) {
        // Aplicar daño por quemadura
        int danio = (int)(pokemon.getPsMaximo() * DANIO_PORCENTUAL);
        pokemon.recibirDanio(danio);
        
        // Reducir ataque
        pokemon.modificarAtaque(REDUCCION_ATAQUE);
        
        System.out.println(pokemon.getNombre() + " sufre daño por quemadura!");
    }
} 