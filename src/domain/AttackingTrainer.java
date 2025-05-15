package domain;

import java.awt.Color;
import java.util.List;
import java.util.Comparator;

public class AttackingTrainer extends Entrenador {
    
    public AttackingTrainer(String nombre, Color color, List<Pokemon> equipo) {
        super(nombre, color, equipo);
    }

    @Override
    public Accion decidirAccion(Batalla batalla) {
        Pokemon miPokemon = obtenerPokemonActivo();
        Pokemon pokemonEnemigo = batalla.getOponente(this).obtenerPokemonActivo();
        
        // Si el Pokémon activo está muy débil (menos del 20% de PS), intentar cambiar
        if (miPokemon.getPsActual() < miPokemon.getPsMaximo() * 0.2) {
            for (int i = 0; i < getEquipo().size(); i++) {
                Pokemon pokemon = getEquipo().get(i);
                if (!pokemon.estaDerrotado() && pokemon != miPokemon && 
                    pokemon.getPsActual() > pokemon.getPsMaximo() * 0.5) {
                    return new Accion(AccionTipo.CAMBIO, i);
                }
            }
        }

        // Buscar el movimiento más fuerte considerando efectividad de tipo
        Movimiento mejorMovimiento = miPokemon.getMovimientos().stream()
            .filter(m -> m.getPPActual() > 0)
            .max(Comparator.comparingDouble(m -> calcularDanioEstimado(m, miPokemon, pokemonEnemigo)))
            .orElse(null);

        if (mejorMovimiento != null) {
            return new Accion(AccionTipo.ATAQUE, mejorMovimiento);
        }

        // Si no hay movimientos disponibles, buscar un Pokémon con PP
        for (int i = 0; i < getEquipo().size(); i++) {
            Pokemon pokemon = getEquipo().get(i);
            if (!pokemon.estaDerrotado() && pokemon != miPokemon && 
                pokemon.getMovimientos().stream().anyMatch(m -> m.getPPActual() > 0)) {
                return new Accion(AccionTipo.CAMBIO, i);
            }
        }

        // Si no hay más opciones, usar Forcejeo
        return new Accion(AccionTipo.ATAQUE, new Forcejeo());
    }

    private double calcularDanioEstimado(Movimiento movimiento, Pokemon atacante, Pokemon defensor) {
        double potenciaBase = movimiento.getPotencia();
        
        // Considerar STAB (Same Type Attack Bonus)
        if (movimiento.getTipo() == atacante.getTipo()) {
            potenciaBase *= 1.5;
        }
        
        // Considerar efectividad de tipo
        double efectividad = obtenerEfectividadTipo(movimiento.getTipo(), defensor.getTipo());
        
        // Considerar stats relevantes
        double statAtaque = movimiento.esEspecial() ? atacante.getAtaqueEspecialActual() : atacante.getAtaqueActual();
        double statDefensa = movimiento.esEspecial() ? defensor.getDefensaEspecialActual() : defensor.getDefensaActual();
        
        return (potenciaBase * statAtaque / statDefensa) * efectividad;
    }

    private double obtenerEfectividadTipo(Tipo tipoMovimiento, Tipo tipoDefensor) {
        if (Pokemon.SUPER_EFECTIVO.containsKey(tipoMovimiento) && 
            Pokemon.SUPER_EFECTIVO.get(tipoMovimiento).contains(tipoDefensor)) {
            return 2.0;
        } else if (Pokemon.NO_ES_MUY_EFECTIVO.containsKey(tipoMovimiento) && 
                   Pokemon.NO_ES_MUY_EFECTIVO.get(tipoMovimiento).contains(tipoDefensor)) {
            return 0.5;
        } else if (Pokemon.NADA_EFECTIVO.containsKey(tipoMovimiento) && 
                   Pokemon.NADA_EFECTIVO.get(tipoMovimiento).contains(tipoDefensor)) {
            return 0.0;
        }
        return 1.0;
    }
}
