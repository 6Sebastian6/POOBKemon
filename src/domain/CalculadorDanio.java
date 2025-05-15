package domain;

import java.util.Random;

public class CalculadorDanio {
    private static final Random random = new Random();
    private static final double PROBABILIDAD_CRITICO = 0.0625; // 1/16 probabilidad de crítico
    private static final double MULTIPLICADOR_CRITICO = 1.5;
    
    public static ResultadoAtaque calcularDanio(Pokemon atacante, Pokemon defensor, Movimiento movimiento) {
        // Cálculo base del daño
        double danioBase = ((2.0 * atacante.getNivel() / 5.0 + 2) * movimiento.getPotencia() * 
            obtenerAtaqueRelevante(atacante, movimiento) / obtenerDefensaRelevante(defensor, movimiento)) / 50.0 + 2;
        
        // Calcular STAB (Same Type Attack Bonus)
        double stab = calcularSTAB(atacante, movimiento);
        
        // Calcular efectividad
        double efectividad = calcularEfectividad(movimiento.getTipo(), defensor.getTipo());
        
        // Calcular crítico
        boolean esCritico = random.nextDouble() < PROBABILIDAD_CRITICO;
        double multiplicadorCritico = esCritico ? MULTIPLICADOR_CRITICO : 1.0;
        
        // Variación aleatoria (85-100%)
        double variacion = 0.85 + (random.nextDouble() * 0.15);
        
        // Cálculo final
        int danioFinal = (int)(danioBase * stab * efectividad * multiplicadorCritico * variacion);
        
        return new ResultadoAtaque(danioFinal, efectividad, esCritico);
    }
    
    private static double calcularSTAB(Pokemon atacante, Movimiento movimiento) {
        return atacante.getTipo() == movimiento.getTipo() ? 1.5 : 1.0;
    }
    
    private static double calcularEfectividad(Tipo tipoMovimiento, Tipo tipoDefensor) {
        if (Pokemon.SUPER_EFECTIVO.containsKey(tipoMovimiento) && 
            Pokemon.SUPER_EFECTIVO.get(tipoMovimiento).contains(tipoDefensor)) {
            return 2.0;
        }
        if (Pokemon.NO_ES_MUY_EFECTIVO.containsKey(tipoMovimiento) && 
            Pokemon.NO_ES_MUY_EFECTIVO.get(tipoMovimiento).contains(tipoDefensor)) {
            return 0.5;
        }
        if (Pokemon.NADA_EFECTIVO.containsKey(tipoMovimiento) && 
            Pokemon.NADA_EFECTIVO.get(tipoMovimiento).contains(tipoDefensor)) {
            return 0.0;
        }
        return 1.0;
    }
    
    private static int obtenerAtaqueRelevante(Pokemon pokemon, Movimiento movimiento) {
        return movimiento.esEspecial() ? pokemon.getAtaqueEspecialActual() : pokemon.getAtaqueActual();
    }
    
    private static int obtenerDefensaRelevante(Pokemon pokemon, Movimiento movimiento) {
        return movimiento.esEspecial() ? pokemon.getDefensaEspecialActual() : pokemon.getDefensaActual();
    }
} 