package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;

public class PantallaImagen extends JPanel {
    private BufferedImage imagen;

    public PantallaImagen(String rutaImagen) {
        try {
            URL url = getClass().getResource(rutaImagen);
            if (url != null) {
                imagen = ImageIO.read(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setBackground(Color.BLACK); // Fondo negro
        setLayout(new BorderLayout()); // Utilizar BorderLayout para centrar la imagen
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            g.drawImage(imagen.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, this);
        }
    }

    // Método para actualizar la imagen si es necesario
    public void setImagen(String rutaImagen) {
        try {
            URL url = getClass().getResource(rutaImagen);
            if (url != null) {
                imagen = ImageIO.read(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        repaint(); // Redibujar el panel con la nueva imagen
    }
}
