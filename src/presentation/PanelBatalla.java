package presentation;

import javax.swing.*;
import java.awt.*;
import domain.*;
import java.util.ArrayList;

public class PanelBatalla extends JPanel {
    private JPanel panelPokemon1;
    private JPanel panelPokemon2;
    private JTextArea areaMensajes;

    public PanelBatalla() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel superior para los Pokémon
        JPanel panelSuperior = new JPanel(new GridLayout(1, 2, 20, 0));
        
        // Panel para el Pokémon 1
        panelPokemon1 = crearPanelPokemon();
        panelSuperior.add(panelPokemon1);
        
        // Panel para el Pokémon 2
        panelPokemon2 = crearPanelPokemon();
        panelSuperior.add(panelPokemon2);
        
        add(panelSuperior, BorderLayout.CENTER);

        // Área de mensajes
        areaMensajes = new JTextArea(5, 40);
        areaMensajes.setEditable(false);
        areaMensajes.setLineWrap(true);
        areaMensajes.setWrapStyleWord(true);
        JScrollPane scrollMensajes = new JScrollPane(areaMensajes);
        add(scrollMensajes, BorderLayout.SOUTH);
    }

    private JPanel crearPanelPokemon() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        // Componentes del panel
        panel.add(new JLabel("Nombre: "));
        panel.add(new JLabel("PS: "));
        panel.add(new JLabel("Estado: "));
        
        // Panel para los movimientos
        JPanel panelMovimientos = new JPanel(new GridLayout(2, 2, 5, 5));
        for (int i = 0; i < 4; i++) {
            panelMovimientos.add(crearPanelMovimiento());
        }
        panel.add(panelMovimientos);
        
        return panel;
    }

    private JPanel crearPanelMovimiento() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        panel.add(new JLabel("Nombre: ---"));
        panel.add(new JLabel("PP: --/--"));
        
        return panel;
    }

    public void actualizarBatalla(Batalla batalla) {
        if (batalla == null) return;
        
        // Actualizar información del primer Pokémon
        Pokemon pokemon1 = batalla.getEntrenadores().get(0).obtenerPokemonActivo();
        actualizarPanelPokemon(panelPokemon1, pokemon1);
        
        // Actualizar información del segundo Pokémon
        Pokemon pokemon2 = batalla.getEntrenadores().get(1).obtenerPokemonActivo();
        actualizarPanelPokemon(panelPokemon2, pokemon2);
        
        repaint();
    }

    private void actualizarPanelPokemon(JPanel panel, Pokemon pokemon) {
        Component[] components = panel.getComponents();
        
        // Actualizar información básica
        ((JLabel)components[0]).setText("Nombre: " + pokemon.getNombre());
        ((JLabel)components[1]).setText(String.format("PS: %d/%d", 
            pokemon.getPsActual(), pokemon.getPsMaximo()));
        ((JLabel)components[2]).setText("Estado: " + 
            (pokemon.getEstado() != null ? pokemon.getEstado().getNombre() : "Normal"));
        
        // Actualizar movimientos
        JPanel panelMovimientos = (JPanel)components[3];
        ArrayList<Movimiento> movimientos = pokemon.getMovimientos();
        
        for (int i = 0; i < Math.min(4, movimientos.size()); i++) {
            JPanel panelMov = (JPanel)panelMovimientos.getComponent(i);
            Movimiento mov = movimientos.get(i);
            
            ((JLabel)panelMov.getComponent(0)).setText("Nombre: " + mov.getNombre());
            ((JLabel)panelMov.getComponent(1)).setText(String.format("PP: %d/%d", 
                mov.getPPActual(), mov.getPPMaximo()));
        }
    }

    public void agregarMensaje(String mensaje) {
        areaMensajes.append(mensaje + "\n");
        areaMensajes.setCaretPosition(areaMensajes.getDocument().getLength());
    }
} 