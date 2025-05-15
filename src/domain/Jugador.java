package domain;

import java.awt.Color;
import java.util.List;

public class Jugador extends Entrenador {
    public Jugador(String nombre, Color color, List<Pokemon> equipo) {
        super(nombre, color, equipo);
    }

    @Override
    public Accion decidirAccion(Batalla batalla) {
        // La lógica de decisión se maneja a través de la interfaz gráfica
        return null;
    }
}