package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.function.Consumer;

import javax.sound.sampled.*;

public class ModoNormal extends JPanel {
    private BufferedImage imgPvP, imgPvM, imgMvM, imgVolver, fondo, imgTitulo;
    private JButton[] botones;
    private JLabel lblTitulo;
    private int selectedIndex = 0;
    private Clip clip;
    private final Consumer<String> onModeSelect;

    public ModoNormal(Consumer<String> onModeSelect) {
        this.onModeSelect = onModeSelect;
        setLayout(new BorderLayout());

        // Cargar imágenes
        fondo = cargarImagen("/resources/imagenes/pantalla_de_inicio/fondoPantallaInicio.png");
        imgVolver = cargarImagen("/resources/imagenes/pantalla_normal/botonVolver.png");
        imgPvP = cargarImagen("/resources/imagenes/pantalla_normal/botonPlayerVSPlayer.png");
        imgPvM = cargarImagen("/resources/imagenes/pantalla_normal/botonPlayerVSMaquina.png");
        imgMvM = cargarImagen("/resources/imagenes/pantalla_normal/botonMaquinaVSMaquina.png");
        imgTitulo = cargarImagen("/resources/imagenes/pantalla_de_inicio/POOBKemon.png");

        cargarSonido();

        // Título
        lblTitulo = new JLabel(new ImageIcon(imgTitulo));
        add(lblTitulo, BorderLayout.NORTH);

        // Botones
        botones = new JButton[] {
            crearBoton(imgPvP),
            crearBoton(imgPvM),
            crearBoton(imgMvM),
            crearBoton(imgVolver)
        };

        for (JButton b : botones) add(botonConHover(b));

        // Accion del boton PvP
        botones[0].addActionListener(e-> playerPlayer());
        // Accion del boton PvM
        botones[1].addActionListener(e -> playerMaquina());
        // Accion del boton MvM
        botones[2].addActionListener(e -> maquinaMaquina());
        // Acción del botón Volver
        botones[3].addActionListener(e -> volverAlMenuPrincipal());

        // Redimensionar componentes
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                ajustarComponentes();
                repaint();
            }
        });

        // Soporte para teclado
        setFocusable(true);
        requestFocusInWindow();
        setupKeyBindings();

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        buttonPanel.add(botones[0]);
        buttonPanel.add(botones[1]);
        buttonPanel.add(botones[2]);

        add(buttonPanel, BorderLayout.CENTER);
        add(botones[3], BorderLayout.SOUTH);
    }

    private JButton crearBoton(BufferedImage img) {
        JButton boton = new JButton(new ImageIcon(img));
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        return boton;
    }

    private JButton botonConHover(JButton boton) {
        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                boton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
                boton.setBorderPainted(true);
                reproducirSonido();
            }
            public void mouseExited(MouseEvent e) {
                boton.setBorder(null);
                boton.setBorderPainted(false);
            }
        });
        return boton;
    }

    private void setupKeyBindings() {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "siguiente");
        inputMap.put(KeyStroke.getKeyStroke("UP"), "anterior");
        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "seleccionar");

        actionMap.put("siguiente", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                cambiarSeleccion(1);
            }
        });

        actionMap.put("anterior", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                cambiarSeleccion(-1);
            }
        });

        actionMap.put("seleccionar", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                botones[selectedIndex].doClick();
                reproducirSonido();
            }
        });

        actualizarSeleccionVisual();
    }

    private void cambiarSeleccion(int direccion) {
        selectedIndex = (selectedIndex + direccion + botones.length) % botones.length;
        actualizarSeleccionVisual();
        reproducirSonido();
    }

    private void actualizarSeleccionVisual() {
        for (int i = 0; i < botones.length; i++) {
            botones[i].setBorder(i == selectedIndex ? BorderFactory.createLineBorder(Color.WHITE, 3) : null);
            botones[i].setBorderPainted(i == selectedIndex);
        }
    }

    private void ajustarComponentes() {
        int w = getWidth(), h = getHeight();
        int btnW = w / 4, btnH = h / 12, centerX = w / 2 - btnW / 2, startY = h / 2 - btnH * 2;

        lblTitulo.setIcon(new ImageIcon(imgTitulo.getScaledInstance((int)(w * 0.6), (int)(h * 0.15), Image.SCALE_SMOOTH)));
        lblTitulo.setBounds(w / 2 - (int)(w * 0.6) / 2, startY - (int)(h * 0.15) - 10, (int)(w * 0.6), (int)(h * 0.15));

        for (int i = 0; i < 3; i++) {
            botones[i].setBounds(centerX, startY + i * (btnH + 10), btnW, btnH);
            Image img = ((ImageIcon) botones[i].getIcon()).getImage();
            botones[i].setIcon(new ImageIcon(img.getScaledInstance(btnW, btnH, Image.SCALE_SMOOTH)));
        }
        
        Image imgVolverEscalada = ((ImageIcon) botones[3].getIcon()).getImage();
        botones[3].setBounds(10, h - 60, 100, 40);
        botones[3].setIcon(new ImageIcon(imgVolverEscalada.getScaledInstance(100, 40, Image.SCALE_SMOOTH)));
        
    }

    private BufferedImage cargarImagen(String ruta) {
        try {
            URL url = getClass().getResource(ruta);
            return url != null ? javax.imageio.ImageIO.read(url) : null;
        } catch (Exception e) {
            e.printStackTrace(); return null;
        }
    }

    private void cargarSonido() {
        try {
            URL soundURL = getClass().getResource("/resources/sonidos/menus/pirin.wav");
            if (soundURL != null) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
                clip = AudioSystem.getClip();
                clip.open(audioIn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reproducirSonido() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    private void volverAlMenuPrincipal() {
        onModeSelect.accept("menu");
    }

    private void playerPlayer(){
        onModeSelect.accept("playerVsPlayer");
    }

    private void playerMaquina(){
        onModeSelect.accept("playerVsMaquina");
    }

    private void maquinaMaquina(){
        onModeSelect.accept("maquinaVsMaquina");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo != null) {
            g.drawImage(fondo.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, this);
        }
    }
}
