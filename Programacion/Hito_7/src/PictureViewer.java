import org.jdesktop.swingx.JXDatePicker;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
public class PictureViewer extends JFrame {
    public PictureViewer(){
        JFrame Ventana = new JFrame();
        Ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Ventana.setSize(500,500);
        Ventana.setLayout(new GridLayout(3,2));
        Conexion con = new Conexion();
        JButton awardButton = new JButton("AWARD");
        JButton removeButton = new JButton("REMOVE");
        awardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                int n = Integer.parseInt(JOptionPane.showInputDialog("Enter the minimum number of visits:"));
                try {
                    HashMap<Integer, Integer> visitsMap = createVisitsMap(con);
                    for (int photographerId : visitsMap.keySet()) {
                        int visits = visitsMap.get(photographerId);
                        if (visits >= n) {
                            Statement st= con.getConn().createStatement();
                            ResultSet rs= st.executeQuery("Update Photographers set Awarded=1 where photographerId='"+ photographerId +"';");
                        }
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    HashMap<Integer, Integer> visitsMap = createVisitsMap(con);
                        Statement st= con.getConn().createStatement();
                        ResultSet rs1= st.executeQuery("Select ph.Awarded, pi.PictureId, pi.Visits  from Photographers ph inner join Pictures pi on ph.photographerId=pi.photographerId;");
                        while (rs1.next()) {
                            int Awarded = rs1.getInt("Awarded");
                            String pictureId= rs1.getString("PictureId");
                            int visits = rs1.getInt("visits");
                            if (visits == 0 && Awarded == 0) {
                                int option = JOptionPane.showConfirmDialog(null, "Do you want to delete the picture with ID " + pictureId + "?");
                                if (option == JOptionPane.YES_OPTION) {
                                ResultSet rs2 = st.executeQuery("Delete from Pictures where PictureId='"+pictureId+"';");
                                }
                            }
                        }
                        ResultSet rs3 = st.executeQuery("DELETE FROM Photographers WHERE PhotographerId NOT IN (SELECT DISTINCT PhotographerId FROM Pictures)");
                } catch (SQLException ex){
                    throw new RuntimeException(ex);
                }
            }
        });
        Ventana.add(awardButton);
        Ventana.add(removeButton);
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
    public HashMap<Integer, Integer> createVisitsMap(Conexion con) throws SQLException {
        HashMap<Integer, Integer> visitsMap = new HashMap<>();
        Statement st= con.getConn().createStatement();
        ResultSet rs= st.executeQuery("Select PhotographerID, SUM(Visits) AS totalvisits From Pictures Group by PhotographerId;");
        while (rs.next()) {
            int photographerId = rs.getInt("PhotographerId");
            int totalVisits = rs.getInt("totalvisits");
            visitsMap.put(photographerId, totalVisits);
        }
        return visitsMap;
    };
}