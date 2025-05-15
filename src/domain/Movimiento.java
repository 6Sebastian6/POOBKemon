package domain;

public abstract class Movimiento {
    protected String nombre;
    protected Tipo tipo;
    protected int potencia;
    protected int precision;
    protected boolean especial;
    protected int ppActual;
    protected int ppMaximo;

    public Movimiento(String nombre, Tipo tipo, int potencia, int precision, boolean especial, int ppMaximo) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.potencia = potencia;
        this.precision = precision;
        this.especial = especial;
        this.ppMaximo = ppMaximo;
        this.ppActual = ppMaximo;
    }

    public void ejecutar(Pokemon atacante, Pokemon defensor) {
        if (!tienePP()) {
            System.out.println("¡No quedan PP para este movimiento!");
            return;
        }

        if (!calculaAcierto()) {
            System.out.println("¡El ataque falló!");
            return;
        }

        consumirPP();
        ResultadoAtaque resultado = CalculadorDanio.calcularDanio(atacante, defensor, this);
        defensor.recibirDanio(resultado.getDanio());

        System.out.println(atacante.getNombre() + " usó " + getNombre() + "!");
        if (!resultado.getMensaje().isEmpty()) {
            System.out.println(resultado.getMensaje());
        }
    }

    public String getNombre() {
        return nombre;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public int getPotencia() {
        return potencia;
    }

    public int getPrecision() {
        return precision;
    }

    public boolean esEspecial() {
        return especial;
    }

    public boolean tienePP() {
        return ppActual > 0;
    }

    public void consumirPP() {
        if (ppActual > 0) {
            ppActual--;
        }
    }

    public void restaurarPP() {
        ppActual = ppMaximo;
    }

    public int getPPActual() {
        return ppActual;
    }

    public int getPPMaximo() {
        return ppMaximo;
    }

    protected boolean calculaAcierto() {
        return Math.random() * 100 <= precision;
    }
}