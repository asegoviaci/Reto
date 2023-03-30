import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Ventana extends JFrame{
    public static void main(String[] args) throws IOException {

        JPanel sesion = new JPanel();
        String pass = JOptionPane.showInputDialog("Input password");
        if (!pass.equals("damocles")){
            JPanel error= new JPanel();
            JOptionPane.showMessageDialog(error,"Incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
            sesion.setVisible(true);
            JFrame f = new JFrame("Test Events: Files");
            f.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            f.setLayout(new BorderLayout());
            f.setSize(700, 500);
            f.setLocationRelativeTo(null);

            JPanel box = new JPanel();
            JPanel box2 = new JPanel();
            box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
            String[] archivo = {"chopper.jpg", "chopper2.jpg", "ryu.jpg"};
            JComboBox comboBox = new JComboBox<>(archivo);
            comboBox.setPreferredSize(new Dimension(250, 25));
            Image img = new ImageIcon("chopper.jpg").getImage();
            ImageIcon img2 = new ImageIcon(img.getScaledInstance(100, 150, Image.SCALE_SMOOTH));
            JLabel hola = new JLabel();
            hola.setIcon(img2);
            JCheckBox check = new JCheckBox("Save your comment", true);
            JButton boton = new JButton("SAVE");
            box.add(comboBox);
            box.add(hola);
            box.add(check);
            box.add(boton);
            JPanel text = new JPanel();
            JTextArea area = new JTextArea();
            JScrollPane panel = new JScrollPane(area);
            panel.setPreferredSize(new Dimension(200, 30));
            comboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String archivo = (String) comboBox.getSelectedItem();
                    load_combo(archivo, hola);
                }
            });
            FileWriter fw=new FileWriter("comentarios.txt");
            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String archivo = (String) comboBox.getSelectedItem();
                    if(check.isSelected()){
                        try {
                            BufferedWriter bw=new BufferedWriter(fw);
                            bw.write(archivo + " "+area.getText());
                            bw.newLine();
                            bw.flush();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            });
            f.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    f.dispose();
                    JOptionPane.showMessageDialog(f, "Adios");
                }
            });
            text.add(panel);
            box2.add(box);
            box2.setSize(100, 100);
            f.add(box2, BorderLayout.WEST);
            f.add(text, BorderLayout.EAST);
            f.setVisible(true);
        }
    }
    public static void load_combo(String a,JLabel hola) {
        Image img = new ImageIcon(a).getImage();
        ImageIcon img2 = new ImageIcon(img.getScaledInstance(80,120,Image.SCALE_SMOOTH));
        hola.setIcon(img2);
    }
}