package domain;

import java.awt.Color;
import java.util.List;
import java.util.Comparator;

public class ExpertTrainer extends Entrenador {
    
    public ExpertTrainer(String nombre, Color color, List<Pokemon> equipo) {
        super(nombre, color, equipo);
    }

    @Override
    public Accion decidirAccion(Batalla batalla) {
        Pokemon miPokemon = obtenerPokemonActivo();
        Pokemon pokemonEnemigo = batalla.getOponente(this).obtenerPokemonActivo();
        
        // Evaluar situación actual
        double situacionActual = evaluarSituacion(miPokemon, pokemonEnemigo);
        
        // Si la situación es desfavorable, considerar cambio
        if (situacionActual < 0.6) {
            int mejorIndice = encontrarMejorCambio(pokemonEnemigo);
            if (mejorIndice != -1) {
                return new Accion(AccionTipo.CAMBIO, mejorIndice);
            }
        }

        // Si el enemigo no tiene estado y tenemos ventaja, considerar movimiento de estado
        if (pokemonEnemigo.getEstado() == null && situacionActual > 1.2) {
            Movimiento movimientoEstado = miPokemon.getMovimientos().stream()
                .filter(m -> m instanceof MovimientoEstado && m.getPPActual() > 0)
                .findFirst()
                .orElse(null);
                
            if (movimientoEstado != null) {
                return new Accion(AccionTipo.ATAQUE, movimientoEstado);
            }
        }

        // Elegir el mejor movimiento basado en la situación
        Movimiento mejorMovimiento = elegirMejorMovimiento(miPokemon, pokemonEnemigo, situacionActual);
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

    private double evaluarSituacion(Pokemon miPokemon, Pokemon pokemonEnemigo) {
        // Evaluar la situación actual considerando múltiples factores
        double ratioPS = miPokemon.getPsActual() / (double)miPokemon.getPsMaximo();
        double ratioAtaque = (miPokemon.getAtaqueActual() + miPokemon.getAtaqueEspecialActual()) /
                           (double)(pokemonEnemigo.getDefensaActual() + pokemonEnemigo.getDefensaEspecialActual());
        double ratioDefensa = (miPokemon.getDefensaActual() + miPokemon.getDefensaEspecialActual()) /
                            (double)(pokemonEnemigo.getAtaqueActual() + pokemonEnemigo.getAtaqueEspecialActual());
        
        // Considerar ventaja de tipo
        double ventajaTipo = calcularVentajaTipo(miPokemon.getTipo(), pokemonEnemigo.getTipo());
        
        // Penalizar si tenemos estado negativo
        double factorEstado = miPokemon.getEstado() == null ? 1.0 : 0.8;
        
        return (ratioPS * 0.4 + ratioAtaque * 0.2 + ratioDefensa * 0.2 + ventajaTipo * 0.2) * factorEstado;
    }

    private int encontrarMejorCambio(Pokemon pokemonEnemigo) {
        int mejorIndice = -1;
        double mejorPuntuacion = 0;
        
        for (int i = 0; i < getEquipo().size(); i++) {
            Pokemon pokemon = getEquipo().get(i);
            if (!pokemon.estaDerrotado() && pokemon != obtenerPokemonActivo()) {
                double puntuacion = evaluarSituacion(pokemon, pokemonEnemigo);
                if (puntuacion > mejorPuntuacion) {
                    mejorPuntuacion = puntuacion;
                    mejorIndice = i;
                }
            }
        }
        
        return mejorPuntuacion > 0.8 ? mejorIndice : -1;
    }

    private Movimiento elegirMejorMovimiento(Pokemon miPokemon, Pokemon pokemonEnemigo, double situacion) {
        return miPokemon.getMovimientos().stream()
            .filter(m -> m.getPPActual() > 0)
            .max(Comparator.comparingDouble(m -> calcularEficienciaMovimiento(m, miPokemon, pokemonEnemigo, situacion)))
            .orElse(null);
    }

    private double calcularEficienciaMovimiento(Movimiento movimiento, Pokemon atacante, 
                                              Pokemon defensor, double situacion) {
        double danioBase = calcularDanioEstimado(movimiento, atacante, defensor);
        
        // Si la situación es favorable, priorizar movimientos ofensivos
        if (situacion > 1.2) {
            return danioBase * 1.2;
        }
        // Si la situación es desfavorable, priorizar movimientos seguros
        else if (situacion < 0.8) {
            return danioBase * (movimiento.getPrecision() / 100.0);
        }
        // En situación neutral, balance entre daño y precisión
        return danioBase * ((movimiento.getPrecision() + 100) / 200.0);
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

    private double calcularVentajaTipo(Tipo miTipo, Tipo tipoEnemigo) {
        double ventaja = obtenerEfectividadTipo(miTipo, tipoEnemigo);
        double desventaja = obtenerEfectividadTipo(tipoEnemigo, miTipo);
        return ventaja / desventaja;
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