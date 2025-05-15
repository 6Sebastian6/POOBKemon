package domain;

public class Accion {
    private AccionTipo tipo;
    private Movimiento movimiento;
    private Pocion pocion;
    private int indicePokemon;

    public Accion(AccionTipo tipo, Movimiento movimiento) {
        this.tipo = tipo;
        this.movimiento = movimiento;
    }

    public Accion(AccionTipo tipo, int indicePokemon) {
        this.tipo = tipo;
        this.indicePokemon = indicePokemon;
    }

    public Accion(AccionTipo tipo, Pocion pocion) {
        this.tipo = tipo;
        this.pocion = pocion;
    }

    public AccionTipo getTipo() { return tipo; }
    public Movimiento getMovimiento() { return movimiento; }
    public Pocion getPocion() { return pocion; }
    public int getIndicePokemon() { return indicePokemon; }
}
