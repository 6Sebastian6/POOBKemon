package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.*;
import java.net.URL;
import domain.*;

public class VentanaPrincipal extends JFrame {
    private CardLayout layout;
    private JPanel mainPanel;
    private Clip clipCancion;
    private JPanel overlayPanel;
    private float alpha = 1.0f;
    private PanelBatalla panelBatalla;
    private PanelControles panelControles;
    private JPanel panelJuego;

    public VentanaPrincipal() {
        setTitle("POOBKemon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Panel principal con CardLayout
        layout = new CardLayout();
        mainPanel = new JPanel(layout);

        // Crear panel de juego (batalla)
        panelJuego = new JPanel(new BorderLayout());
        panelBatalla = new PanelBatalla();
        panelControles = new PanelControles();
        panelJuego.add(panelBatalla, BorderLayout.CENTER);
        panelJuego.add(panelControles, BorderLayout.SOUTH);

        // Agregar pantallas al mainPanel
        Menu menu = new Menu(this::cambiarPantalla);
        ModoNormal modoNormal = new ModoNormal(this::cambiarPantalla);
        PlayerVsPlayer playerVsPlayer = new PlayerVsPlayer();
        MaquinaVsMaquina maquinaVsMaquina = new MaquinaVsMaquina();
        PlayerVsMaquina playerVsMaquina = new PlayerVsMaquina(this);

        mainPanel.add(menu, "menu");
        mainPanel.add(modoNormal, "modoNormal");
        mainPanel.add(playerVsPlayer, "playerVsPlayer");   
        mainPanel.add(playerVsMaquina, "playerVsMaquina");      
        mainPanel.add(maquinaVsMaquina, "maquinaVsMaquina");
        mainPanel.add(panelJuego, "batalla");

        // Crear la pantalla de imagen
        PantallaImagen pantallaImagen = new PantallaImagen("/resources/imagenes/pantalla_de_inicio/POOBKemon.png");
        mainPanel.add(pantallaImagen, "pantallaImagen");

        add(mainPanel);

        // Mostrar PantallaImagen al inicio
        layout.show(mainPanel, "pantallaImagen");

        // Cargar música y comenzar fade
        cargarCancionFondo();
        mostrarFadeInicial();

        setVisible(true);
    }

    private void mostrarFadeInicial() {
        overlayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.BLACK);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        overlayPanel.setOpaque(false);
        overlayPanel.setBounds(0, 0, getWidth(), getHeight());
        overlayPanel.setLayout(null);

        getLayeredPane().add(overlayPanel, JLayeredPane.PALETTE_LAYER);

        Timer fadeIn = new Timer(20, e -> {
            alpha -= 0.01f;
            if (alpha <= 0f) {
                alpha = 0f;
                ((Timer)e.getSource()).stop();
                getLayeredPane().remove(overlayPanel);
                getLayeredPane().repaint();

                Timer espera = new Timer(2000, ev -> iniciarSegundoFade());
                espera.setRepeats(false);
                espera.start();
            } else {
                overlayPanel.repaint();
            }
        });
        fadeIn.start();
    }

    private void iniciarSegundoFade() {
        overlayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.BLACK);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        overlayPanel.setOpaque(false);
        overlayPanel.setBounds(0, 0, getWidth(), getHeight());
        overlayPanel.setLayout(null);

        getLayeredPane().add(overlayPanel, JLayeredPane.PALETTE_LAYER);

        Timer fade = new Timer(20, e -> {
            alpha += 0.0067f;
            if (alpha >= 1f) {
                alpha = 1f;
                ((Timer)e.getSource()).stop();
                getLayeredPane().remove(overlayPanel);
                getLayeredPane().repaint();
                layout.show(mainPanel, "menu");
            } else {
                overlayPanel.repaint();
            }
        });
        fade.start();
    }

    private void cambiarPantalla(String nombre) {
        layout.show(mainPanel, nombre);
    }

    private void cargarCancionFondo() {
        try {
            URL songURL = getClass().getResource("/resources/sonidos/opening.wav");
            if (songURL != null) {
                AudioInputStream audioInSong = AudioSystem.getAudioInputStream(songURL);
                clipCancion = AudioSystem.getClip();
                clipCancion.open(audioInSong);
                clipCancion.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                System.err.println("No se encontró la canción: /resources/sonidos/opening.wav");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void detenerCancion() {
        if (clipCancion != null && clipCancion.isRunning()) {
            clipCancion.stop();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        detenerCancion();
    }

    public void iniciarBatalla(Batalla batalla) {
        panelBatalla.actualizarBatalla(batalla);
        panelControles.actualizarControles(batalla);
        mostrarPanelBatalla();
    }

    public void mostrarPanelBatalla() {
        layout.show(mainPanel, "batalla");
    }
}
