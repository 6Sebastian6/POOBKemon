package presentation;

import javax.swing.*;
import java.awt.*;

public class ModoSupervivencia extends JPanel {
    public ModoSupervivencia() {
        setLayout(null);

        JLabel label = new JLabel("Modo Supervivencia");
        label.setBounds(300, 100, 200, 50);
        add(label);

        // Aquí puedes agregar los botones o opciones correspondientes al modo supervivencia
        JButton btnSiguiente = new JButton("Comenzar Supervivencia");
        btnSiguiente.setBounds(300, 200, 200, 50);
        add(btnSiguiente);
    }
}
