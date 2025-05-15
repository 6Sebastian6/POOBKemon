package domain;

public class Forcejeo extends MovimientoFisico {
    private static final double DANIO_RETROCESO = 0.25; // 25% del daño causado

    public Forcejeo() {
        super("Forcejeo", Tipo.NORMAL, 50, 100, Integer.MAX_VALUE); // PP infinitos
    }

    @Override
    public void ejecutar(Pokemon atacante, Pokemon defensor) {
        if (!calculaAcierto()) {
            System.out.println("¡El ataque falló!");
            return;
        }

        ResultadoAtaque resultado = CalculadorDanio.calcularDanio(atacante, defensor, this);
        int danio = resultado.getDanio();
        
        // Aplicar daño al defensor
        defensor.recibirDanio(danio);
        
        // Aplicar daño de retroceso al atacante
        int danioRetroceso = (int)(danio * DANIO_RETROCESO);
        atacante.recibirDanio(danioRetroceso);
        
        System.out.println(atacante.getNombre() + " usó Forcejeo por falta de PP!");
        if (!resultado.getMensaje().isEmpty()) {
            System.out.println(resultado.getMensaje());
        }
        System.out.println(atacante.getNombre() + " recibió " + danioRetroceso + " puntos de daño por retroceso!");
    }

    @Override
    public boolean tienePP() {
        return true; // Forcejeo siempre tiene PP disponibles
    }

    @Override
    public void consumirPP() {
        // No consume PP
    }
}