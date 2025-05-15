package domain.estados;

import domain.EstadoPokemon;
import domain.Pokemon;

public class EstadoQuemadura extends EstadoPokemon {
    private static final double DAÑO_POR_TURNO = 0.0625; // 1/16 de los PS máximos
    
    public EstadoQuemadura() {
        super("quemado", -1); // Estado permanente hasta curación
    }
    
    @Override
    public boolean puedeActuar() {
        return true; // La quemadura no impide actuar
    }
    
    @Override
    public void aplicarEfecto(Pokemon pokemon) {
        int daño = (int)(pokemon.getPsMaximo() * DAÑO_POR_TURNO);
        pokemon.recibirDanio(daño);
        pokemon.modificarAtaque(0.5); // Reduce el ataque a la mitad
        System.out.println(pokemon.getNombre() + " sufre por su quemadura!");
    }
} 