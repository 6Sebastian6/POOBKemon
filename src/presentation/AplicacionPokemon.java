package presentation;

import javax.swing.*;
import java.awt.*;
import domain.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class AplicacionPokemon {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Crear Pokémon usando el PokemonFactory
                Pokemon pikachu = PokemonFactory.crearPokemon("pikachu", 50);
                Pokemon charmander = PokemonFactory.crearPokemon("charmander", 50);

                // Crear entrenadores
                ArrayList<Pokemon> equipoJugador = new ArrayList<>(Arrays.asList(pikachu));
                ArrayList<Pokemon> equipoRival = new ArrayList<>(Arrays.asList(charmander));
                
                Entrenador jugador = new AttackingTrainer("Jugador", Color.BLUE, equipoJugador);
                Entrenador rival = new DefensiveTrainer("Rival", Color.RED, equipoRival);

                // Crear batalla
                Batalla batalla = new Batalla(jugador, rival);

                // Crear y mostrar la ventana
                VentanaPrincipal ventana = new VentanaPrincipal();
                ventana.iniciarBatalla(batalla);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, 
                    "Error al iniciar la aplicación: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
} 