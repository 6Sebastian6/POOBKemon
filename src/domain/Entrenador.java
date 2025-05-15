package domain;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

public abstract class Entrenador {
    protected String nombre;
    protected Color color;
    protected List<Pokemon> equipo;
    protected int pokemonActivoIndex;

    public Entrenador(String nombre, Color color, List<Pokemon> equipo) {
        this.nombre = nombre;
        this.color = color;
        this.equipo = equipo;
        this.pokemonActivoIndex = 0;
    }

    public abstract Accion decidirAccion(Batalla batalla);

    public boolean tienePokemonesVivos() {
        return equipo.stream().anyMatch(p -> !p.estaDerrotado());
    }

    public Pokemon obtenerPokemonActivo() {
        return equipo.get(pokemonActivoIndex);
    }

    public void cambiarPokemonActivo(int nuevoIndex) {
        if (nuevoIndex >= 0 && nuevoIndex < equipo.size() && !equipo.get(nuevoIndex).estaDerrotado()) {
            pokemonActivoIndex = nuevoIndex;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public Color getColor() {
        return color;
    }

    public List<Pokemon> getEquipo() {
        return equipo;
    }

    public void setEquipo(List<Pokemon> nuevoEquipo) {
        if (nuevoEquipo != null) {
            this.equipo = new ArrayList<>(nuevoEquipo);
            this.pokemonActivoIndex = 0;
        }
    }
}