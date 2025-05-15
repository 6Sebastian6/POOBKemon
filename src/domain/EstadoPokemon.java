package domain;

public abstract class EstadoPokemon {
    protected String nombre;
    protected int duracion; // -1 para estados permanentes hasta curación

    public EstadoPokemon(String nombre, int duracion) {
        this.nombre = nombre;
        this.duracion = duracion;
    }

    // Retorna true si el Pokémon puede actuar, false si no
    public abstract boolean puedeActuar();
    
    // Aplica los efectos del estado al inicio del turno
    public abstract void aplicarEfecto(Pokemon pokemon);
    
    // Reduce la duración del estado y retorna true si el estado debe terminar
    public boolean actualizarDuracion() {
        if (duracion > 0) {
            duracion--;
            return duracion == 0;
        }
        return false;
    }

    public String getNombre() {
        return nombre;
    }
} 