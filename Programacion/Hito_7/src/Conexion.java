import org.mariadb.jdbc.Connection;
import org.mariadb.jdbc.Statement;
import com.sun.tools.javac.Main;
import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.*;
public class Conexion {
        static final String SERVER_IP="localhost";
        static final String DB_NAME="Hito_6";
        static final String JDBC_DRIVE="org.mariadb.jdbc.Driver";
        static final String DB_URL="jdbc:mariadb://"+SERVER_IP+":3306/"+DB_NAME;
        static final String USER="root";
        static final String PASS="root";
        private Connection conn;
        private Statement st;
        private ResultSet rs;
        public Conexion(){
            try {
                Class.forName(JDBC_DRIVE);
                System.out.println("Connecting to database...");
                conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
                System.out.println("Connected");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    public java.sql.Connection getConn() {
        return conn;
    }
    public java.sql.Statement getSt() {
        return st;
    }
    public ResultSet getRs() {
        return rs;
    }
}