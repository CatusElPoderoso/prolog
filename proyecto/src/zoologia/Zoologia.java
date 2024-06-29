package zoologia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Zoologia extends JFrame {
    private Gui recommendationSystem;
    private JTextField userInput;
    private JTextField preferencesInput;
    private JTextArea outputArea;
    private JComboBox<String> userDropdown;

    public Zoologia() {
        recommendationSystem = new Gui();

        setTitle("Sistema de Recomendación");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel para añadir preferencias de usuario
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Usuario:"));
        userInput = new JTextField(10);
        inputPanel.add(userInput);
        inputPanel.add(new JLabel("Preferencias (separadas por comas):"));
        preferencesInput = new JTextField(20);
        inputPanel.add(preferencesInput);
        JButton addButton = new JButton("Añadir Preferencias");
        inputPanel.add(addButton);

        // Panel para obtener recomendaciones
        JPanel recommendationPanel = new JPanel();
        recommendationPanel.add(new JLabel("Seleccionar Usuario:"));
        userDropdown = new JComboBox<>();
        recommendationPanel.add(userDropdown);
        JButton recommendButton = new JButton("Obtener Recomendaciones");
        recommendationPanel.add(recommendButton);

        // Área de salida
        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Añadir listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = userInput.getText();
                String preferences = preferencesInput.getText();
                List<String> preferencesList = Arrays.asList(preferences.split(","));
                recommendationSystem.addUserPreferences(user, preferencesList);
                updateDropdown();
                outputArea.setText("Preferencias añadidas para el usuario " + user);
            }
        });

        recommendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = (String) userDropdown.getSelectedItem();
                if (user != null) {
                    List<String> recommendations = recommendationSystem.recommend(user);
                    outputArea.setText("Recomendaciones para " + user + ":\n" + recommendations.toString());
                }
            }
        });

        // Disposición de los componentes en el frame
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(inputPanel, BorderLayout.NORTH);
        container.add(recommendationPanel, BorderLayout.CENTER);
        container.add(scrollPane, BorderLayout.SOUTH);
    }

    private void updateDropdown() {
        Set<String> users = recommendationSystem.getAllUsers();
        userDropdown.removeAllItems();
        for (String user : users) {
            userDropdown.addItem(user);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Zoologia().setVisible(true);
            }
        });
    }
}