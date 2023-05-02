import org.jdesktop.swingx.JXDatePicker;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
public class PictureViewer extends JFrame {
    public PictureViewer(){
        JFrame Ventana = new JFrame();
        Ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Ventana.setSize(500,500);
        Ventana.setLayout(new GridLayout(2,2));
        Conexion con = new Conexion();
        JPanel panel1 = new JPanel();
        Statement st1;
        ArrayList<String> nombres = new ArrayList<>();
        try {
            st1 = con.getConn().createStatement();
            ResultSet rs = st1.executeQuery("select Name from Photographers;");
            while (rs.next()){
                nombres.add(rs.getString("Name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //Panel1//
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        JLabel texto1 = new JLabel("Photographer: ");
        JComboBox cb = new JComboBox<>(nombres.toArray());
        cb.setMaximumSize(new Dimension(100,20));
        panel1.add(texto1);
        panel1.add(cb);
        Ventana.add(panel1);
        //Panel2//
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
        JLabel texto2 = new JLabel("Photos after: ");
        JXDatePicker data = new JXDatePicker();
        data.setMaximumSize(new Dimension(100,20));
        panel2.add(texto2);
        panel2.add(data);
        Ventana.add(panel2);
        //Panel3//
        JPanel panel3 = new JPanel();
        JList list=new JList();
        DefaultListModel modelo = new DefaultListModel();
        list.setModel(modelo);
        list.setPreferredSize(new Dimension(250,250));
        panel3.add(list);
        Ventana.add(panel3);
        //Panel4//
        JPanel panel4 = new JPanel();
        JLabel img = new JLabel();
        panel4.add(img);
        Ventana.add(panel4);

        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fecha;
                String nombre = cb.getSelectedItem().toString();
                Statement st2;
                ResultSet rs;
                try {
                    modelo.removeAllElements();
                    st2 = con.getConn().createStatement();
                    if (data.getDate()==null){
                         rs = st2.executeQuery("select Title from Pictures where PhotographerID in (select PhotographerID from Photographers where Name='"+ nombre+"');");
                    }
                    else {
                        fecha = new SimpleDateFormat("yyyy-MM-dd").format(data.getDate());
                        rs = st2.executeQuery("select Title from Pictures where Date > '" + fecha + "' and PhotographerID in (select PhotographerID from Photographers where Name='"+nombre+"');");
                    }
                    while (rs.next()){
                        modelo.addElement(rs.getString("Title"));
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String file = "";
                    String title = (String) list.getSelectedValue();
                    Statement st3;
                    try {
                        st3=con.getConn().createStatement();
                        ResultSet rs = st3.executeQuery("select File from Pictures where Title = '" + title + "'");
                        while(rs.next()){
                            file=rs.getString("File");
                        }
                        img.setIcon(new ImageIcon(file));
                        incrementVisits(file);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        Ventana.setVisible(true);
    }
    public void incrementVisits(String s){
        try{
            Conexion con = new Conexion();
            Statement st4=con.getConn().createStatement();
            ResultSet rs = st4.executeQuery("update Pictures set Visits = Visits + 1 where File='"+ s +"';");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    };
}