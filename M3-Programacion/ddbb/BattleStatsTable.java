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

        // modifying test
        bst.setResource_deuterion_acquired(100);
        bst.modifyRow();

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
        try (PreparedStatement ps = db.getConnection().prepareStatement(insertQuery, new String[] { "num_battle" });) {
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
                        System.out.println("new num_battle: " + num_battle);
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
        String updateQuery = "UPDATE Battle_stats SET "
            + "planet_id = ?, resource_metal_acquired = ?, resource_deuterion_acquired = ? "
            + "WHERE num_battle = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(updateQuery)) {
            ps.setInt(1, planet_id);
            ps.setInt(2, resource_metal_acquired);
            ps.setInt(3, resource_deuterion_acquired);
            ps.setInt(4, num_battle);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("success modifying the row with num_battle=" + num_battle);
            } else {
                System.out.println("row with num_battle = " + num_battle + " not found. no row has been modified");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setNum_battle(int num_battle) {
		this.num_battle = num_battle;
	}

	public void setPlanet_id(int planet_id) {
		this.planet_id = planet_id;
	}

	public void setResource_metal_acquired(int resource_metal_acquired) {
		this.resource_metal_acquired = resource_metal_acquired;
	}

	public void setResource_deuterion_acquired(int resource_deuterion_acquired) {
		this.resource_deuterion_acquired = resource_deuterion_acquired;
	}

}
