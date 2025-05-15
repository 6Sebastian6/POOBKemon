package domain.modos;

import domain.Batalla;
import domain.Entrenador;
import domain.Pokemon;
import domain.PokemonFactory;
import java.util.List;
import java.util.ArrayList;

public class ModoSupervivencia extends ModoJuego {
    private static final int NIVEL_POKEMON = 100;
    private static final int POKEMON_POR_EQUIPO = 6;
    
    public ModoSupervivencia(List<Entrenador> entrenadores) {
        // En modo supervivencia es una única batalla con Pokémon aleatorios
        super(entrenadores, 1);
        // Generar equipos aleatorios para cada entrenador
        entrenadores.forEach(this::generarEquipoAleatorio);
    }

    private void generarEquipoAleatorio(Entrenador entrenador) {
        List<Pokemon> equipoAleatorio = new ArrayList<>();
        for (int i = 0; i < POKEMON_POR_EQUIPO; i++) {
            equipoAleatorio.add(PokemonFactory.crearPokemonAleatorio(NIVEL_POKEMON));
        }
        entrenador.setEquipo(equipoAleatorio);
    }

    @Override
    public Batalla iniciarBatalla() {
        return new Batalla(entrenadores.get(0), entrenadores.get(1));
    }

    @Override
    public void procesarResultadoBatalla(Batalla batalla) {
        // La batalla termina cuando hay un ganador
        juegoTerminado = true;
    }

    @Override
    public boolean verificarFinJuego() {
        return juegoTerminado;
    }

    @Override
    public Entrenador obtenerGanador() {
        return entrenadores.stream()
            .filter(e -> e.tienePokemonesVivos())
            .findFirst()
            .orElse(null);
    }

    @Override
    public int obtenerPuntaje(Entrenador entrenador) {
        // En modo supervivencia no hay sistema de puntaje, solo ganador y perdedor
        return entrenador.tienePokemonesVivos() ? 1 : 0;
    }
} 