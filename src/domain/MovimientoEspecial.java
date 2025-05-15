package domain;

public class MovimientoEspecial extends Movimiento {
    
    public MovimientoEspecial(String nombre, Tipo tipo, int potencia, int precision, int ppMaximo) {
        super(nombre, tipo, potencia, precision, true, ppMaximo);
    }

    @Override
    public void ejecutar(Pokemon atacante, Pokemon defensor) {
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
}
