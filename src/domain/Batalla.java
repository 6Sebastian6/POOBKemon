package domain;

import java.util.List;
import java.util.Arrays;

public class Batalla {
    private List<Entrenador> entrenadores;
    private boolean batallaContinua;
    private int turno;

    public Batalla(Entrenador entrenador1, Entrenador entrenador2) {
        this.entrenadores = Arrays.asList(entrenador1, entrenador2);
        this.batallaContinua = true;
        this.turno = 0;
    }

    public List<Entrenador> getEntrenadores() {
        return entrenadores;
    }

    public Entrenador getOponente(Entrenador entrenador) {
        return entrenadores.get(0) == entrenador ? entrenadores.get(1) : entrenadores.get(0);
    }

    public boolean isBatallaContinua() {
        return batallaContinua;
    }

    public void ejecutarTurno(Accion accion1, Accion accion2) {
        if (!batallaContinua) return;

        // Determinar orden de acciones basado en velocidad y prioridad
        Entrenador primero = entrenadores.get(0);
        Entrenador segundo = entrenadores.get(1);
        Accion accionPrimero = accion1;
        Accion accionSegundo = accion2;

        if (segundo.obtenerPokemonActivo().getVelocidadActual() > 
            primero.obtenerPokemonActivo().getVelocidadActual()) {
            // Intercambiar orden si el segundo es más rápido
            Entrenador temp = primero;
            primero = segundo;
            segundo = temp;
            Accion tempAccion = accionPrimero;
            accionPrimero = accionSegundo;
            accionSegundo = tempAccion;
        }

        // Ejecutar acciones en orden
        ejecutarAccion(primero, accionPrimero);
        if (batallaContinua && !verificarFinBatalla()) {
            ejecutarAccion(segundo, accionSegundo);
        }

        // Verificar si la batalla ha terminado
        verificarFinBatalla();
        turno++;
    }

    private void ejecutarAccion(Entrenador entrenador, Accion accion) {
        if (!entrenador.obtenerPokemonActivo().puedeActuar() && accion.getTipo() != AccionTipo.CAMBIO) {
            System.out.println(entrenador.obtenerPokemonActivo().getNombre() + " no puede actuar debido a su estado!");
            return;
        }

        Entrenador oponente = getOponente(entrenador);
        Pokemon atacante = entrenador.obtenerPokemonActivo();
        Pokemon defensor = oponente.obtenerPokemonActivo();

        switch (accion.getTipo()) {
            case ATAQUE:
                if (atacante.puedeActuar()) {
                    // Si no tiene PP, usar Forcejeo
                    Movimiento movimiento = accion.getMovimiento();
                    if (!movimiento.tienePP()) {
                        movimiento = new Forcejeo();
                    }
                    movimiento.ejecutar(atacante, defensor);
                    verificarDerrota(oponente);
                }
                break;
            case CAMBIO:
                entrenador.cambiarPokemonActivo(accion.getIndicePokemon());
                System.out.println(entrenador.getNombre() + " cambió a " + 
                    entrenador.obtenerPokemonActivo().getNombre());
                break;
            case ITEM:
                accion.getPocion().aplicar(atacante);
                break;
            case HUIR:
                System.out.println(entrenador.getNombre() + " ha huido de la batalla");
                batallaContinua = false;
                break;
        }
    }

    private boolean verificarFinBatalla() {
        for (Entrenador entrenador : entrenadores) {
            if (!entrenador.tienePokemonesVivos()) {
                batallaContinua = false;
                return true;
            }
        }
        return false;
    }

    private void verificarDerrota(Entrenador entrenador) {
        if (!entrenador.obtenerPokemonActivo().puedeActuar()) {
            System.out.println(entrenador.obtenerPokemonActivo().getNombre() + " fue derrotado!");
            if (!entrenador.tienePokemonesVivos()) {
                System.out.println(entrenador.getNombre() + " se quedó sin Pokémon!");
                batallaContinua = false;
            }
        }
    }

    public int getTurno() {
        return turno;
    }
}