package domain.estados;

import domain.EstadoPokemon;
import domain.Pokemon;

public class Envenenado extends EstadoPokemon {
    private static final double DANIO_PORCENTUAL = 0.125; // 1/8 de la vida máxima

    public Envenenado() {
        super("Envenenado", -1); // Estado permanente hasta curación
    }

    @Override
    public boolean puedeActuar() {
        return true; // El envenenamiento no impide actuar
    }

    @Override
    public void aplicarEfecto(Pokemon pokemon) {
        int danio = (int)(pokemon.getPsMaximo() * DANIO_PORCENTUAL);
        pokemon.recibirDanio(danio);
        System.out.println(pokemon.getNombre() + " sufre daño por envenenamiento!");
    }
} 