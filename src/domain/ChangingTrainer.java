package domain;

import java.awt.Color;
import java.util.List;
import java.util.Comparator;

public class ChangingTrainer extends Entrenador {
    private int turnosSinCambiar = 0;
    private static final int TURNOS_MINIMOS_ENTRE_CAMBIOS = 2;
    
    public ChangingTrainer(String nombre, Color color, List<Pokemon> equipo) {
        super(nombre, color, equipo);
    }

    @Override
    public Accion decidirAccion(Batalla batalla) {
        Pokemon miPokemon = obtenerPokemonActivo();
        Pokemon pokemonEnemigo = batalla.getOponente(this).obtenerPokemonActivo();
        
        // Incrementar contador de turnos sin cambiar
        turnosSinCambiar++;
        
        // Evaluar si es necesario cambiar
        if (turnosSinCambiar >= TURNOS_MINIMOS_ENTRE_CAMBIOS) {
            // Buscar el mejor Pokémon para la situación actual
            int mejorIndice = encontrarMejorCambio(pokemonEnemigo);
            if (mejorIndice != -1) {
                turnosSinCambiar = 0;
                return new Accion(AccionTipo.CAMBIO, mejorIndice);
            }
        }

        // Si no podemos o no queremos cambiar, usar el mejor movimiento disponible
        Movimiento mejorMovimiento = elegirMejorMovimiento(miPokemon, pokemonEnemigo);
        if (mejorMovimiento != null) {
            return new Accion(AccionTipo.ATAQUE, mejorMovimiento);
        }

        // Si no hay movimientos disponibles, forzar cambio independiente del contador
        for (int i = 0; i < getEquipo().size(); i++) {
            Pokemon pokemon = getEquipo().get(i);
            if (!pokemon.estaDerrotado() && pokemon != miPokemon && 
                pokemon.getMovimientos().stream().anyMatch(m -> m.getPPActual() > 0)) {
                turnosSinCambiar = 0;
                return new Accion(AccionTipo.CAMBIO, i);
            }
        }

        // Si no hay más opciones, usar Forcejeo
        return new Accion(AccionTipo.ATAQUE, new Forcejeo());
    }

    private int encontrarMejorCambio(Pokemon pokemonEnemigo) {
        int mejorIndice = -1;
        double mejorPuntuacion = 0;
        
        for (int i = 0; i < getEquipo().size(); i++) {
            Pokemon pokemon = getEquipo().get(i);
            if (!pokemon.estaDerrotado() && pokemon != obtenerPokemonActivo()) {
                double puntuacion = evaluarVentaja(pokemon, pokemonEnemigo);
                if (puntuacion > mejorPuntuacion) {
                    mejorPuntuacion = puntuacion;
                    mejorIndice = i;
                }
            }
        }
        
        // Solo cambiar si la ventaja es significativa
        return mejorPuntuacion > 1.3 ? mejorIndice : -1;
    }

    private double evaluarVentaja(Pokemon miPokemon, Pokemon pokemonEnemigo) {
        // Evaluar ventaja de tipo
        double ventajaTipo = calcularVentajaTipo(miPokemon.getTipo(), pokemonEnemigo.getTipo());
        
        // Evaluar ventaja de stats
        double ventajaStats = calcularVentajaStats(miPokemon, pokemonEnemigo);
        
        // Evaluar estado de salud
        double ratioPS = miPokemon.getPsActual() / (double)miPokemon.getPsMaximo();
        
        // Penalizar si tiene estado
        double factorEstado = miPokemon.getEstado() == null ? 1.0 : 0.7;
        
        return (ventajaTipo * 0.4 + ventajaStats * 0.3 + ratioPS * 0.3) * factorEstado;
    }

    private double calcularVentajaTipo(Tipo miTipo, Tipo tipoEnemigo) {
        double ventaja = obtenerEfectividadTipo(miTipo, tipoEnemigo);
        double desventaja = obtenerEfectividadTipo(tipoEnemigo, miTipo);
        return ventaja / desventaja;
    }

    private double calcularVentajaStats(Pokemon miPokemon, Pokemon pokemonEnemigo) {
        double ratioAtaque = (miPokemon.getAtaqueActual() + miPokemon.getAtaqueEspecialActual()) /
                           (double)(pokemonEnemigo.getDefensaActual() + pokemonEnemigo.getDefensaEspecialActual());
        double ratioDefensa = (miPokemon.getDefensaActual() + miPokemon.getDefensaEspecialActual()) /
                            (double)(pokemonEnemigo.getAtaqueActual() + pokemonEnemigo.getAtaqueEspecialActual());
        return (ratioAtaque + ratioDefensa) / 2;
    }

    private Movimiento elegirMejorMovimiento(Pokemon miPokemon, Pokemon pokemonEnemigo) {
        return miPokemon.getMovimientos().stream()
            .filter(m -> m.getPPActual() > 0)
            .max(Comparator.comparingDouble(m -> calcularEficienciaMovimiento(m, miPokemon, pokemonEnemigo)))
            .orElse(null);
    }

    private double calcularEficienciaMovimiento(Movimiento movimiento, Pokemon atacante, Pokemon defensor) {
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