package domain;

public class ResultadoAtaque {
    private final int danio;
    private final double efectividad;
    private final boolean esCritico;
    private final String mensaje;

    public ResultadoAtaque(int danio, double efectividad, boolean esCritico) {
        this.danio = danio;
        this.efectividad = efectividad;
        this.esCritico = esCritico;
        this.mensaje = generarMensaje();
    }

    private String generarMensaje() {
        StringBuilder mensaje = new StringBuilder();
        
        if (efectividad > 1.0) {
            mensaje.append("¡Es super efectivo! ");
        } else if (efectividad < 1.0 && efectividad > 0.0) {
            mensaje.append("No es muy efectivo... ");
        } else if (efectividad == 0.0) {
            mensaje.append("No afecta al Pokémon enemigo... ");
        }
        
        if (esCritico) {
            mensaje.append("¡Golpe crítico! ");
        }
        
        return mensaje.toString().trim();
    }

    public int getDanio() {
        return danio;
    }

    public double getEfectividad() {
        return efectividad;
    }

    public boolean esCritico() {
        return esCritico;
    }

    public String getMensaje() {
        return mensaje;
    }
} 