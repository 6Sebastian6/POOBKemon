package domain.estados;

import domain.EstadoPokemon;
import domain.Pokemon;

public class EstadoVeneno extends EstadoPokemon {
    private static final double DAÑO_POR_TURNO_BASE = 0.125; // 1/8 de los PS máximos
    private int turnosActivo;
    
    public EstadoVeneno() {
        super("envenenado", -1); // Estado permanente hasta curación
        this.turnosActivo = 0;
    }
    
    @Override
    public boolean puedeActuar() {
        return true; // El veneno no impide actuar
    }
    
    @Override
    public void aplicarEfecto(Pokemon pokemon) {
        turnosActivo++;
        // El daño aumenta con cada turno
        double multiplicadorDaño = Math.min(turnosActivo * DAÑO_POR_TURNO_BASE, 0.5);
        int daño = (int)(pokemon.getPsMaximo() * multiplicadorDaño);
        pokemon.recibirDanio(daño);
        System.out.println(pokemon.getNombre() + " sufre por el veneno!");
    }
} 