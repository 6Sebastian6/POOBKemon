package domain;

import java.awt.Color;
import java.util.List;
import java.util.Comparator;

public class DefensiveTrainer extends Entrenador {
    
    public DefensiveTrainer(String nombre, Color color, List<Pokemon> equipo) {
        super(nombre, color, equipo);
    }

    @Override
    public Accion decidirAccion(Batalla batalla) {
        Pokemon miPokemon = obtenerPokemonActivo();
        Pokemon pokemonEnemigo = batalla.getOponente(this).obtenerPokemonActivo();
        
        // Si el Pokémon está muy débil (menos del 35% de PS), intentar cambiar
        if (miPokemon.getPsActual() < miPokemon.getPsMaximo() * 0.35) {
            // Buscar el Pokémon con mejor defensa y PS
            int mejorIndice = -1;
            double mejorPuntuacion = 0;
            
            for (int i = 0; i < getEquipo().size(); i++) {
                Pokemon pokemon = getEquipo().get(i);
                if (!pokemon.estaDerrotado() && pokemon != miPokemon) {
                    double puntuacion = calcularPuntuacionDefensiva(pokemon);
                    if (puntuacion > mejorPuntuacion) {
                        mejorPuntuacion = puntuacion;
                        mejorIndice = i;
                    }
                }
            }
            
            if (mejorIndice != -1) {
                return new Accion(AccionTipo.CAMBIO, mejorIndice);
            }
        }

        // Priorizar movimientos de estado si el enemigo no tiene uno
        if (pokemonEnemigo.getEstado() == null) {
            Movimiento movimientoEstado = miPokemon.getMovimientos().stream()
                .filter(m -> m instanceof MovimientoEstado && m.getPPActual() > 0)
                .findFirst()
                .orElse(null);
                
            if (movimientoEstado != null) {
                return new Accion(AccionTipo.ATAQUE, movimientoEstado);
            }
        }

        // Si no hay movimientos de estado disponibles o el enemigo ya tiene estado,
        // usar el movimiento con mejor relación daño/defensa
        Movimiento mejorMovimiento = miPokemon.getMovimientos().stream()
            .filter(m -> m.getPPActual() > 0)
            .max(Comparator.comparingDouble(m -> calcularEficienciaMovimiento(m, miPokemon, pokemonEnemigo)))
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

    private double calcularPuntuacionDefensiva(Pokemon pokemon) {
        return (pokemon.getPsActual() / (double)pokemon.getPsMaximo()) * 
               ((pokemon.getDefensaActual() + pokemon.getDefensaEspecialActual()) / 2.0);
    }

    private double calcularEficienciaMovimiento(Movimiento movimiento, Pokemon atacante, Pokemon defensor) {
        // Para el entrenador defensivo, valoramos más los movimientos que no comprometan mucho la defensa
        double danioBase = calcularDanioEstimado(movimiento, atacante, defensor);
        double factorDefensivo = (atacante.getDefensaActual() + atacante.getDefensaEspecialActual()) / 2.0;
        return danioBase * (factorDefensivo / 200.0); // Normalizar el factor defensivo
    }

    private double calcularDanioEstimado(Movimiento movimiento, Pokemon atacante, Pokemon defensor) {
        double potenciaBase = movimiento.getPotencia();
        
        // Considerar STAB
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