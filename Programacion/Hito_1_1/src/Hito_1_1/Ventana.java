package Hito_1_1;
import javax.swing.*;
import java.awt.*;
public class Ventana extends JFrame{
    public static void main(String[] args) {
        JFrame f = new JFrame("Try yourself");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout());
        f.setSize(300,300);
        f.setVisible(true);
        JPanel box = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JCheckBox c1 = new JCheckBox("Katniss");
        JCheckBox c2 = new JCheckBox("Peeta");
        c1.setSelected(true);
        c2.setSelected(true);
        box.add(c1);
        box.add(c2);
        f.add(box, BorderLayout.NORTH);
        JPanel radio = new JPanel();
        radio.setLayout(new BoxLayout(radio,BoxLayout.Y_AXIS));
        JRadioButton[] boton = {new JRadioButton("OPT 1", true), new JRadioButton("OPT 2"), new JRadioButton("OPT 3")};
        radio.add(Box.createVerticalGlue());
        ButtonGroup grupo = new ButtonGroup();
        for (JRadioButton r : boton) {
            grupo.add(r);
            radio.add(r);
        }
        f.add(radio, BorderLayout.EAST);
        radio.add(Box.createVerticalGlue());
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton b1 = new JButton("But 1");
        JButton b2 = new JButton("But 2");
        b1.setSize(80, 80);
        b2.setSize(80, 80);
        botones.add(b1);
        botones.add(b2);
        f.add(botones, BorderLayout.SOUTH);
        JPanel img = new JPanel(new GridLayout(2,2,0,0));
        ImageIcon icon = new ImageIcon("chopper.jpg");
        Image imagen = icon.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
        ImageIcon newImageIcon = new ImageIcon(imagen);
        JLabel l1 = new JLabel(newImageIcon);
        JLabel l2 = new JLabel(newImageIcon);
        JLabel l3 = new JLabel(newImageIcon);
        JLabel l4 = new JLabel(newImageIcon);
        img.add(l1);
        img.add(l2);
        img.add(l3);
        img.add(l4);
        f.add(img, BorderLayout.CENTER);
    }
}
