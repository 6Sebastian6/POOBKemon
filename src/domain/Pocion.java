package domain;

public class Pocion {
    private String nombre;
    private int cantidadCuracion;

    public Pocion(String nombre, int cantidadCuracion) {
        this.nombre = nombre;
        this.cantidadCuracion = cantidadCuracion;
    }

    public void aplicar(Pokemon pokemon) {
        pokemon.curar(cantidadCuracion);
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidadCuracion() {
        return cantidadCuracion;
    }
}

