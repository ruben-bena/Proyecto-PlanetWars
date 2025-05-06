package ddbb;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// This class manages the connection to the DDBB and pass Connection object to the other database classes
public class Database  {
    private Connection conn;

    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@//localhost:1521/freepdb1";
        String username = "planetWars";
        String pass = "planetWars";
        new Database(url, username, pass);
    }

    public Database(String url, String username, String pass) {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            System.out.println("Driver cargado");
            conn = DriverManager.getConnection(url, username, pass);
            System.out.println("Conexión establecida");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Driver no encontrado.");
            e.printStackTrace();
        }
    }
    
    public void close() {
    	try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public boolean isClosed() {
    	try {
			return conn.isClosed();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}
    }
}