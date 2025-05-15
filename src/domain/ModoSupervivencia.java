package domain;

public class ModoSupervivencia implements ModoJuego {
    @Override
    public void iniciarBatalla() {
        System.out.println("Iniciando batalla en modo supervivencia");
        // Se generan pokemones aleatorios con nivel 100 y movimientos predefinidos
    }
}
