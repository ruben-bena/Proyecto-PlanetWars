package ddbb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BattleStatsTable implements Table {
    private Database db;
    private int num_battle;
    private int planet_id;
    private int resource_metal_acquired;
    private int resource_deuterion_acquired;
    public static void main(String[] args) {
        // String url = "jdbc:oracle:thin:@//localhost:1521/freepdb1"; // Local VM Oracle ddbb
        String url = "jdbc:oracle:thin:@//localhost:1521/freepdb1"; // Local VM Oracle ddbb
        String username = "planetWars";
        String pass = "planetWars";
        BattleStatsTable bst = new BattleStatsTable(new Database(url, username, pass),1,1,1,1);
        
        // insertion test
        bst.insertRow();

        // // modifying test
        // bst.modifyRow();

        // // getRow test
        // bst.getRow(1);
    }

    public BattleStatsTable(Database db, int num_battle, int planet_id, int resource_metal_acquired,
			int resource_deuterion_acquired) {
		super();
		this.db = db;
		this.num_battle = num_battle;
		this.planet_id = planet_id;
		this.resource_metal_acquired = resource_metal_acquired;
		this.resource_deuterion_acquired = resource_deuterion_acquired;
	}

    @Override
    public void insertRow() {
        String insertQuery = "INSERT INTO Battle_stats (" +
            "planet_id, resource_metal_acquired, resource_deuterion_acquired" +
            ") VALUES (?, ?, ?)";
        System.out.println("query for inserting data executed");
        try (PreparedStatement ps = db.getConnection().prepareStatement(insertQuery, new String[] { "planet_id" });) {
            // inserting data in ddbb
            System.out.println("PreparedStatement created");
            ps.setInt(1, planet_id);
            ps.setInt(2, resource_metal_acquired);
            ps.setInt(3, resource_deuterion_acquired);

            int affectedRows = ps.executeUpdate();
            System.out.println("insertion in Battle_stats executed");

            // saving the id in the attribute for modifying table later
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        num_battle = generatedKeys.getInt(1);
                        System.out.println("new num_battle: " + planet_id);
                    } else {
                        System.out.println("could not obtain generated num_battle");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getRow(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRow'");
    }

    @Override
    public void modifyRow() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modifyRow'");
    }

}
