package ddbb;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// This class manages the connection to the DDBB and pass Connection object to the other database classes
public class Database  {
    private Connection conn;

    public static void main(String[] args) {
        // String url = "jdbc:oracle:thin:@//localhost:1521/freepdb1";
        String basePath = System.getProperty("user.dir"); // Obtiene la ruta al directorio base del proyecto
        String walletPath = basePath + "/Wallet_GPR65DX8TG9BAU0O"; // Ruta relativa al wallet dentro del proyecto
        System.out.println(walletPath);
        String url = "jdbc:oracle:thin:@gpr65dx8tg9bau0o_high?TNS_ADMIN=" + walletPath;
        String username = "ADMIN";
        String pass = "PlanetWarsMolaMogollon1";
        Database db = new Database(url, username, pass);
        System.out.println(db.isClosed());
        db.close();
        System.out.println(db.isClosed());
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