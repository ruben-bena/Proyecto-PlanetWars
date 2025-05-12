package ddbb;

// This class (and the others alike) receives a Database object at creation (to receive the Connection attribute) and in each method receives the "Gamedata" object

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlanetStatsTable implements Table {
    private Database db; // Reference for Connection
    // Table columns
    private int planet_id;
    private String name;
    private int resource_metal_amount;
    private int resource_deuterion_amount;
    private int technology_defense_level;
    private int technology_attack_level;
    private int battles_counter;
    private int missile_launcherremaining;
    private int ion_cannon_remaining;
    private int plasma_cannon_remaining;
    private int light_hunter_remaining;
    private int heavy_hunter_remaining;
    private int battleship_remaining;
    private int armored_ship_remaining;
    
    public static void main(String[] args) {
        // String url = "jdbc:oracle:thin:@//localhost:1521/freepdb1"; // Local VM Oracle ddbb
        String url = "jdbc:oracle:thin:@//192.168.1.39:1521/freepdb1"; // Local VM Oracle ddbb
        String username = "planetWars";
        String pass = "planetWars";
        PlanetStatsTable pst = new PlanetStatsTable(new Database(url, username, pass), 1, "prueba", 1,1,1,1,1,1,1,1,1,1,1,1);
        pst.insertRow();
    }

    
    public PlanetStatsTable(Database db) {
        this.db = db;
    }

    public PlanetStatsTable(Database db, int planet_id, String name, int resource_metal_amount,
			int resource_deuterion_amount, int technology_defense_level, int technology_attack_level,
			int battles_counter, int missile_launcherremaining, int ion_cannon_remaining, int plasma_cannon_remaining,
			int light_hunter_remaining, int heavy_hunter_remaining, int battleship_remaining,
			int armored_ship_remaining) {
		super();
		this.db = db;
		this.planet_id = planet_id;
		this.name = name;
		this.resource_metal_amount = resource_metal_amount;
		this.resource_deuterion_amount = resource_deuterion_amount;
		this.technology_defense_level = technology_defense_level;
		this.technology_attack_level = technology_attack_level;
		this.battles_counter = battles_counter;
		this.missile_launcherremaining = missile_launcherremaining;
		this.ion_cannon_remaining = ion_cannon_remaining;
		this.plasma_cannon_remaining = plasma_cannon_remaining;
		this.light_hunter_remaining = light_hunter_remaining;
		this.heavy_hunter_remaining = heavy_hunter_remaining;
		this.battleship_remaining = battleship_remaining;
		this.armored_ship_remaining = armored_ship_remaining;
	}


    @Override
    public void insertRow() {
        String insertQuery = "INSERT INTO Planet_stats (" +
            "name, resource_metal_amount, resource_deuterion_amount, " +
            "technology_defense_level, technology_attack_level, battles_counter, " +
            "missile_launcher_remaining, ion_cannon_remaining, plasma_cannon_remaining, " +
            "light_hunter_remaining, heavy_hunter_remaining, battleship_remaining, armored_ship_remaining" +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        System.out.println("query para insertar creada");
        try {
            // inserting data in ddbb
            // PreparedStatement ps = db.getConnection().prepareStatement(insertQuery);
            PreparedStatement ps = db.getConnection().prepareStatement(insertQuery, new String[] { "planet_id" });
            System.out.println("PreparedStatement creado");
            ps.setString(1, name);
            ps.setInt(2, resource_metal_amount);
            ps.setInt(3, resource_deuterion_amount);
            ps.setInt(4, technology_defense_level);
            ps.setInt(5, technology_attack_level);
            ps.setInt(6, battles_counter);
            ps.setInt(7, missile_launcherremaining);
            ps.setInt(8, ion_cannon_remaining);
            ps.setInt(9, plasma_cannon_remaining);
            ps.setInt(10, light_hunter_remaining);
            ps.setInt(11, heavy_hunter_remaining);
            ps.setInt(12, battleship_remaining);
            ps.setInt(13, armored_ship_remaining);

            int affectedRows = ps.executeUpdate();
            System.out.println("Inserción ejecutada correctamente");

            // saving the id in the attribute for modifying table later
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        planet_id = generatedKeys.getInt(1);
                        System.out.println("new planet_id: " + planet_id);
                    } else {
                        System.out.println("Could not obtain generated planet_id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getRow() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRow'");
    }

    @Override
    public void deleteRow() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteRow'");
    }

    @Override
    public void modifyRow() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modifyRow'");
    }
    
}
