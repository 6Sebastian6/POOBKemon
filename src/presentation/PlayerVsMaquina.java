package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.sound.sampled.*;
import domain.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PlayerVsMaquina extends JPanel {
    private BufferedImage fondo, imgTitulo;
    private BufferedImage imgAgresivo, imgDefensivo, imgExperto, imgVolver;
    private JButton[] botones;
    private JLabel lblTitulo;
    private int selectedIndex = 0;
    private Clip clip;
    private VentanaPrincipal ventanaPrincipal;

    public PlayerVsMaquina(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        setLayout(null);

        // Cargar imágenes
        fondo = cargarImagen("/resources/imagenes/pantalla_de_inicio/fondoPantallaInicio.png");
        imgVolver = cargarImagen("/resources/imagenes/pantalla_normal/botonVolver.png");
        imgAgresivo = cargarImagen("/resources/imagenes/Pantalla_elegir_maquina/botonEntrenadorAgresivo.png");
        imgDefensivo = cargarImagen("/resources/imagenes/Pantalla_elegir_maquina/botonEntrenadorDefensivo.png");
        imgExperto = cargarImagen("/resources/imagenes/Pantalla_elegir_maquina/botonEntrenadorExperto.png");
        imgTitulo = cargarImagen("/resources/imagenes/pantalla_de_inicio/POOBKemon.png");

        cargarSonido();

        // Título
        lblTitulo = new JLabel(new ImageIcon(imgTitulo));
        add(lblTitulo);

        // Botones (3 tipos de entrenador)
        botones = new JButton[] {
            crearBoton(imgAgresivo),
            crearBoton(imgDefensivo),
            crearBoton(imgExperto),
            crearBoton(imgVolver)
        };

        for (JButton b : botones) add(botonConHover(b));

        // Acciones de los botones
        botones[0].addActionListener(e -> seleccionarAgresivo());
        botones[1].addActionListener(e -> seleccionarDefensivo());
        botones[2].addActionListener(e -> seleccionarExperto());
        botones[3].addActionListener(e -> volverAlMenuPrincipal());

        // Redimensionar 
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                ajustarComponentes();
                repaint();
            }
        });

        // Para el teclado
        setFocusable(true);
        requestFocusInWindow();
        setupKeyBindings();
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
        int btnW = w / 4, btnH = h / 12, centerX = w / 2 - btnW / 2, startY = h / 2 - btnH;

        lblTitulo.setIcon(new ImageIcon(imgTitulo.getScaledInstance((int)(w * 0.6), (int)(h * 0.15), Image.SCALE_SMOOTH)));
        lblTitulo.setBounds(w / 2 - (int)(w * 0.6) / 2, 20, (int)(w * 0.6), (int)(h * 0.15));

        // Tener la posicion de los botones de los entrenadores
        for (int i = 0; i < 3; i++) {
            botones[i].setBounds(centerX, startY + i * (btnH + 10), btnW, btnH);
            Image img = ((ImageIcon) botones[i].getIcon()).getImage();
            botones[i].setIcon(new ImageIcon(img.getScaledInstance(btnW, btnH, Image.SCALE_SMOOTH)));
        }
        
        // Tener la posicion del boton volver
        Image imgVolverEscalada = ((ImageIcon) botones[3].getIcon()).getImage();
        botones[3].setBounds(10, h - 60, 100, 40);
        botones[3].setIcon(new ImageIcon(imgVolverEscalada.getScaledInstance(100, 40, Image.SCALE_SMOOTH)));
    }

    private BufferedImage cargarImagen(String ruta) {
        try {
            URL url = getClass().getResource(ruta);
            return url != null ? javax.imageio.ImageIO.read(url) : null;
        } catch (Exception e) {
            e.printStackTrace(); 
            return null;
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
        Container parent = getParent();
        if (parent instanceof JPanel && parent.getLayout() instanceof CardLayout) {
            ((CardLayout) parent.getLayout()).show(parent, "menu");
        }
    }

    private void seleccionarAgresivo() {
        iniciarBatalla(new AttackingTrainer("Rival", Color.RED, crearEquipoInicial()));
    }

    private void seleccionarDefensivo() {
        iniciarBatalla(new DefensiveTrainer("Rival", Color.RED, crearEquipoInicial()));
    }

    private void seleccionarExperto() {
        iniciarBatalla(new ExpertTrainer("Rival", Color.RED, crearEquipoInicial()));
    }

    private void iniciarBatalla(Entrenador rival) {
        // Crear equipo del jugador
        ArrayList<Pokemon> equipoJugador = crearEquipoInicial();
        Entrenador jugador = new Jugador("Jugador", Color.BLUE, equipoJugador);

        // Crear batalla
        Batalla batalla = new Batalla(jugador, rival);
        ventanaPrincipal.iniciarBatalla(batalla);
    }

    private ArrayList<Pokemon> crearEquipoInicial() {
        // Por ahora solo creamos un Pokémon inicial, pero esto se puede expandir
        Pokemon pokemon = PokemonFactory.crearPokemon("pikachu", 50);
        return new ArrayList<>(Arrays.asList(pokemon));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo != null) {
            g.drawImage(fondo.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, this);
        }
    }

    @Override
    public void addNotify() {
        super.addNotify();
        ajustarComponentes();
    }
}