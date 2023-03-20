import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Ventana extends JFrame{
    public static void main(String[] args) {
        JFrame f = new JFrame("Test Events: Files");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout());
        f.setSize(700,500);
        f.setLocationRelativeTo(null);

        JPanel box = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] archivo = {"python.txt", "c.txt", "java.txt"};
        JComboBox comboBox = new JComboBox<>(archivo);
        comboBox.setPreferredSize(new Dimension(250,25));
        box.add(comboBox);
        JButton boton = new JButton("Clear");
        box.add(boton);
        JTextArea textArea = new JTextArea("");
        textArea.setEditable(false);
        JScrollPane panel = new JScrollPane(textArea);
        panel.setPreferredSize(new Dimension(300,300));

        JOptionPane op = new JOptionPane();
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String archivo = (String) comboBox.getSelectedItem();
                try {
                    String contenido = new String(Files.readAllBytes(Paths.get(archivo)));
                    textArea.setText(contenido);
                }catch (IOException ex){
                    JOptionPane.showMessageDialog(op, "Error al leer el archivo", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    textArea.setText("");
            }
        });
        JButton boton2 = new JButton("Close");
        boton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
            }
        });
        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.add(panel);
        text.add(boton2);
        f.add(box, BorderLayout.WEST);
        f.add(text, BorderLayout.EAST);
        f.setVisible(true);
    }
}
