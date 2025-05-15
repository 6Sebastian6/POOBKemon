package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.function.Consumer;

public class Menu extends JPanel {
    private BufferedImage fondo, imgTitulo;
    private BufferedImage imgNormal, imgSupervivencia, imgSalir;
    private JButton[] botones;
    private JLabel lblTitulo;
    private int selectedIndex = 0;
    private Consumer<String> onModeSelect;

    public Menu(Consumer<String> onModeSelect) {
        this.onModeSelect = onModeSelect;
        setLayout(null);

        // Cargar imágenes
        fondo = cargarImagen("/resources/imagenes/pantalla_de_inicio/fondoPantallaInicio.png");
        imgTitulo = cargarImagen("/resources/imagenes/pantalla_de_inicio/POOBKemon.png");
        imgNormal = cargarImagen("/resources/imagenes/pantalla_de_inicio/botonModoNormal.png");
        imgSupervivencia = cargarImagen("/resources/imagenes/pantalla_de_inicio/botonModoSupervivencia.png");
        imgSalir = cargarImagen("/resources/imagenes/pantalla_de_inicio/botonSalir.png");

        // Título
        lblTitulo = new JLabel(new ImageIcon(imgTitulo));
        add(lblTitulo);

        // Botones
        botones = new JButton[] {
            crearBoton(imgNormal),
            crearBoton(imgSupervivencia),
            crearBoton(imgSalir)
        };

        for (JButton b : botones) add(botonConHover(b));

        // Acciones de los botones
        botones[0].addActionListener(e -> onModeSelect.accept("modoNormal"));
        botones[1].addActionListener(e -> onModeSelect.accept("modoSupervivencia"));
        botones[2].addActionListener(e -> System.exit(0));

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
            }
        });

        actualizarSeleccionVisual();
    }

    private void cambiarSeleccion(int direccion) {
        selectedIndex = (selectedIndex + direccion + botones.length) % botones.length;
        actualizarSeleccionVisual();
    }

    private void actualizarSeleccionVisual() {
        for (int i = 0; i < botones.length; i++) {
            botones[i].setBorder(i == selectedIndex ? BorderFactory.createLineBorder(Color.WHITE, 3) : null);
            botones[i].setBorderPainted(i == selectedIndex);
        }
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

    private void ajustarComponentes() {
        int w = getWidth(), h = getHeight();
        int btnW = w / 4, btnH = h / 12;
        int centerX = w / 2 - btnW / 2;
        int startY = h / 2 - btnH;

        lblTitulo.setIcon(new ImageIcon(imgTitulo.getScaledInstance((int)(w * 0.6), (int)(h * 0.15), Image.SCALE_SMOOTH)));
        lblTitulo.setBounds(w / 2 - (int)(w * 0.6) / 2, 20, (int)(w * 0.6), (int)(h * 0.15));

        for (int i = 0; i < botones.length; i++) {
            botones[i].setBounds(centerX, startY + i * (btnH + 10), btnW, btnH);
            Image img = ((ImageIcon) botones[i].getIcon()).getImage();
            botones[i].setIcon(new ImageIcon(img.getScaledInstance(btnW, btnH, Image.SCALE_SMOOTH)));
        }
    }
}
