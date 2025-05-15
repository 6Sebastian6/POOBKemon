package presentation;

import javax.swing.*;
import java.awt.*;
import domain.*;
import java.util.List;

public class PanelControles extends JPanel {
    private JPanel panelMovimientos;
    private JPanel panelCambio;
    private Batalla batallaActual;
    private Pokemon pokemonActivo;

    public PanelControles() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Controles"));

        // Panel de movimientos
        panelMovimientos = new JPanel(new GridLayout(2, 2, 5, 5));
        panelMovimientos.setBorder(BorderFactory.createTitledBorder("Movimientos"));
        
        // Panel de cambio de Pokémon
        panelCambio = new JPanel(new GridLayout(3, 2, 5, 5));
        panelCambio.setBorder(BorderFactory.createTitledBorder("Cambiar Pokémon"));

        // Añadir paneles
        add(panelMovimientos, BorderLayout.CENTER);
        add(panelCambio, BorderLayout.EAST);
    }

    public void actualizarControles(Batalla batalla) {
        this.batallaActual = batalla;
        if (batalla == null) return;

        // Obtener el Pokémon activo del jugador
        pokemonActivo = batalla.getEntrenadores().get(0).obtenerPokemonActivo();
        
        actualizarPanelMovimientos();
        actualizarPanelCambio();
    }

    private void actualizarPanelMovimientos() {
        panelMovimientos.removeAll();
        
        // Crear botones para cada movimiento
        List<Movimiento> movimientos = pokemonActivo.getMovimientos();
        for (int i = 0; i < Math.min(4, movimientos.size()); i++) {
            Movimiento mov = movimientos.get(i);
            JButton btnMovimiento = crearBotonMovimiento(mov);
            panelMovimientos.add(btnMovimiento);
        }

        panelMovimientos.revalidate();
        panelMovimientos.repaint();
    }

    private JButton crearBotonMovimiento(Movimiento movimiento) {
        JButton btn = new JButton();
        btn.setLayout(new BoxLayout(btn, BoxLayout.Y_AXIS));
        
        // Añadir información del movimiento
        btn.add(new JLabel(movimiento.getNombre()));
        btn.add(new JLabel(String.format("PP: %d/%d", 
            movimiento.getPPActual(), movimiento.getPPMaximo())));
        
        // Configurar el evento
        btn.addActionListener(e -> {
            if (!movimiento.tienePP()) {
                // Si no hay PP, usar Forcejeo
                new Forcejeo().ejecutar(pokemonActivo, 
                    batallaActual.getEntrenadores().get(1).obtenerPokemonActivo());
            } else {
                // Usar el movimiento normalmente
                movimiento.ejecutar(pokemonActivo, 
                    batallaActual.getEntrenadores().get(1).obtenerPokemonActivo());
            }
            actualizarControles(batallaActual);
        });
        
        return btn;
    }

    private void actualizarPanelCambio() {
        panelCambio.removeAll();
        
        // Crear botones para cada Pokémon en el equipo
        List<Pokemon> equipo = batallaActual.getEntrenadores().get(0).getEquipo();
        for (int i = 0; i < equipo.size(); i++) {
            Pokemon pokemon = equipo.get(i);
            if (!pokemon.estaDerrotado() && pokemon != pokemonActivo) {
                JButton btnCambio = new JButton(pokemon.getNombre());
                final int index = i;
                btnCambio.addActionListener(e -> {
                    batallaActual.getEntrenadores().get(0).cambiarPokemonActivo(index);
                    actualizarControles(batallaActual);
                });
                panelCambio.add(btnCambio);
            }
        }

        panelCambio.revalidate();
        panelCambio.repaint();
    }
} 