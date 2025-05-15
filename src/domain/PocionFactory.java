package domain;

import java.util.ArrayList;
import java.util.List;

public class ItemFactory {
    public static List<Pocion> crearPociones() {
        List<Pocion> pociones = new ArrayList<>();
        pociones.add(new Pocion("Potion", 20, false));
        pociones.add(new Pocion("Super Potion", 50, false));
        pociones.add(new Pocion("Hyper Potion", 200, false));
        pociones.add(new Pocion("Revive", 50, true)); // Revive con 50 PS
        return pociones;
    }
}