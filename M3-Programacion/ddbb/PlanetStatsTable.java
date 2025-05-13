package ddbb;
import classes.*;
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
    private int missile_launcher_remaining;
    private int ion_cannon_remaining;
    private int plasma_cannon_remaining;
    private int light_hunter_remaining;
    private int heavy_hunter_remaining;
    private int battleship_remaining;
    private int armored_ship_remaining;
    
    public static void main(String[] args) {
        // String url = "jdbc:oracle:thin:@//localhost:1521/freepdb1"; // Local VM Oracle ddbb
        String url = "jdbc:oracle:thin:@//localhost:1521/freepdb1"; // Local VM Oracle ddbb
        String username = "planetWars";
        String pass = "planetWars";
        PlanetStatsTable pst = new PlanetStatsTable(new Database(url, username, pass), 1, "prueba", 1,1,1,1,1,1,1,1,1,1,1,1);
        
        // insertion test
        pst.insertRow();

        // modifying test
        pst.setBattles_counter(3);
        pst.modifyRow();

        // getRow test
        pst.getRow(1);
    }
    
    private PlanetStatsTable() {
        
    }

    public PlanetStatsTable(Database db, Planet planet) {
        this.db = db;
        this.name = "prueba"; // TODO: needs to be well implemented in class Planet
        this.resource_metal_amount = planet.getMetal();
        this.resource_deuterion_amount = planet.getDeuterium();
        this.technology_defense_level = planet.getTechnologyDefense();
        this.technology_attack_level = planet.getTechnologyAttack();
        this.battles_counter = planet.getNBattles();
        this.missile_launcher_remaining = planet.getArmy()[4].size();
        this.ion_cannon_remaining = planet.getArmy()[5].size();
        this.plasma_cannon_remaining = planet.getArmy()[6].size();
        this.light_hunter_remaining = planet.getArmy()[0].size();
        this.heavy_hunter_remaining = planet.getArmy()[1].size();
        this.battleship_remaining = planet.getArmy()[2].size();
        this.armored_ship_remaining = planet.getArmy()[3].size();
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
		this.missile_launcher_remaining = missile_launcherremaining;
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
        System.out.println("query for inserting data executed");
        try (PreparedStatement ps = db.getConnection().prepareStatement(insertQuery, new String[] { "planet_id" });) {
            // inserting data in ddbb
            System.out.println("PreparedStatement created");
            ps.setString(1, name);
            ps.setInt(2, resource_metal_amount);
            ps.setInt(3, resource_deuterion_amount);
            ps.setInt(4, technology_defense_level);
            ps.setInt(5, technology_attack_level);
            ps.setInt(6, battles_counter);
            ps.setInt(7, missile_launcher_remaining);
            ps.setInt(8, ion_cannon_remaining);
            ps.setInt(9, plasma_cannon_remaining);
            ps.setInt(10, light_hunter_remaining);
            ps.setInt(11, heavy_hunter_remaining);
            ps.setInt(12, battleship_remaining);
            ps.setInt(13, armored_ship_remaining);

            int affectedRows = ps.executeUpdate();
            System.out.println("insertion in Planet_stats executed");

            // saving the id in the attribute for modifying table later
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        planet_id = generatedKeys.getInt(1);
                        GlobalContext.planet_id = planet_id;
                        System.out.println("new planet_id: " + planet_id);
                    } else {
                        System.out.println("could not obtain generated planet_id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getRow(int planet_id) {
        String selectQuery = "SELECT name, resource_metal_amount, resource_deuterion_amount, "
            + "technology_defense_level, technology_attack_level, battles_counter, "
            + "missile_launcher_remaining, ion_cannon_remaining, plasma_cannon_remaining, "
            + "light_hunter_remaining, heavy_hunter_remaining, battleship_remaining, armored_ship_remaining "
            + "FROM Planet_stats WHERE planet_id = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(selectQuery)) {
            ps.setInt(1, planet_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("name");
                    resource_metal_amount = rs.getInt("resource_metal_amount");
                    resource_deuterion_amount = rs.getInt("resource_deuterion_amount");
                    technology_defense_level = rs.getInt("technology_defense_level");
                    technology_attack_level = rs.getInt("technology_attack_level");
                    battles_counter = rs.getInt("battles_counter");
                    missile_launcher_remaining = rs.getInt("missile_launcher_remaining");
                    ion_cannon_remaining = rs.getInt("ion_cannon_remaining");
                    plasma_cannon_remaining = rs.getInt("plasma_cannon_remaining");
                    light_hunter_remaining = rs.getInt("light_hunter_remaining");
                    heavy_hunter_remaining = rs.getInt("heavy_hunter_remaining");
                    battleship_remaining = rs.getInt("battleship_remaining");
                    armored_ship_remaining = rs.getInt("armored_ship_remaining");
                    System.out.println("recovered row for planet_id=" + planet_id);
                    
                    // refresh planet_id in case it has changed (for example id we loaded another planet)
                    if (this.planet_id != planet_id) {
                        System.out.println("planet_id before = " + this.planet_id + "planet_id now = " + planet_id);
                        this.planet_id = planet_id;
                    }
                } else {
                    System.out.println("no row with planet_id=" + planet_id + " in the ddbb");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifyRow() {
        String updateQuery = "UPDATE Planet_stats SET "
            + "name = ?, resource_metal_amount = ?, resource_deuterion_amount = ?, "
            + "technology_defense_level = ?, technology_attack_level = ?, battles_counter = ?, "
            + "missile_launcher_remaining = ?, ion_cannon_remaining = ?, plasma_cannon_remaining = ?, "
            + "light_hunter_remaining = ?, heavy_hunter_remaining = ?, battleship_remaining = ?, armored_ship_remaining = ? "
            + "WHERE planet_id = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(updateQuery)) {
            ps.setString(1, name);
            ps.setInt(2, resource_metal_amount);
            ps.setInt(3, resource_deuterion_amount);
            ps.setInt(4, technology_defense_level);
            ps.setInt(5, technology_attack_level);
            ps.setInt(6, battles_counter);
            ps.setInt(7, missile_launcher_remaining);
            ps.setInt(8, ion_cannon_remaining);
            ps.setInt(9, plasma_cannon_remaining);
            ps.setInt(10, light_hunter_remaining);
            ps.setInt(11, heavy_hunter_remaining);
            ps.setInt(12, battleship_remaining);
            ps.setInt(13, armored_ship_remaining);
            ps.setInt(14, planet_id);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("success modifying the row with planet_id=" + planet_id);
            } else {
                System.out.println("row with planet_id = " + planet_id + " not found. no row has been modified");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAttributes(Planet planet) {

        // Update the attributes

        if (this.resource_metal_amount != planet.getMetal()) {
            this.resource_metal_amount = planet.getMetal();
        }
        if (this.resource_deuterion_amount != planet.getDeuterium()) {
            this.resource_deuterion_amount = planet.getDeuterium();
        }
        if (this.technology_defense_level != planet.getTechnologyDefense()) {
            this.technology_defense_level = planet.getTechnologyDefense();
        }
        if (this.technology_attack_level != planet.getTechnologyAttack()) {
            this.technology_attack_level = planet.getTechnologyAttack();
        }
        if (this.battles_counter != planet.getNBattles()) {
            this.battles_counter = planet.getNBattles();
        }
        if (this.missile_launcher_remaining != planet.getArmy()[4].size()) {
            this.missile_launcher_remaining = planet.getArmy()[4].size();
        }
        if (this.ion_cannon_remaining != planet.getArmy()[5].size()) {
            this.ion_cannon_remaining = planet.getArmy()[5].size();
        }
        if (this.plasma_cannon_remaining != planet.getArmy()[6].size()) {
            this.plasma_cannon_remaining = planet.getArmy()[6].size();
        }
        if (this.light_hunter_remaining != planet.getArmy()[0].size()) {
            this.light_hunter_remaining = planet.getArmy()[0].size();
        }
        if (this.heavy_hunter_remaining != planet.getArmy()[1].size()) {
            this.heavy_hunter_remaining = planet.getArmy()[1].size();
        }
        if (this.battleship_remaining != planet.getArmy()[2].size()) {
            this.battleship_remaining = planet.getArmy()[2].size();
        }
        if (this.armored_ship_remaining != planet.getArmy()[3].size()) {
            this.armored_ship_remaining = planet.getArmy()[3].size();
        }

        // Save changes in ddbb

        modifyRow();
    }
    
    public void setPlanet_id(int planet_id) {
		this.planet_id = planet_id;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setResource_metal_amount(int resource_metal_amount) {
		this.resource_metal_amount = resource_metal_amount;
	}


	public void setResource_deuterion_amount(int resource_deuterion_amount) {
		this.resource_deuterion_amount = resource_deuterion_amount;
	}


	public void setTechnology_defense_level(int technology_defense_level) {
		this.technology_defense_level = technology_defense_level;
	}


	public void setTechnology_attack_level(int technology_attack_level) {
		this.technology_attack_level = technology_attack_level;
	}


	public void setBattles_counter(int battles_counter) {
		this.battles_counter = battles_counter;
	}


	public void setMissile_launcherremaining(int missile_launcherremaining) {
		this.missile_launcher_remaining = missile_launcherremaining;
	}


	public void setIon_cannon_remaining(int ion_cannon_remaining) {
		this.ion_cannon_remaining = ion_cannon_remaining;
	}


	public void setPlasma_cannon_remaining(int plasma_cannon_remaining) {
		this.plasma_cannon_remaining = plasma_cannon_remaining;
	}


	public void setLight_hunter_remaining(int light_hunter_remaining) {
		this.light_hunter_remaining = light_hunter_remaining;
	}


	public void setHeavy_hunter_remaining(int heavy_hunter_remaining) {
		this.heavy_hunter_remaining = heavy_hunter_remaining;
	}


	public void setBattleship_remaining(int battleship_remaining) {
		this.battleship_remaining = battleship_remaining;
	}


	public void setArmored_ship_remaining(int armored_ship_remaining) {
		this.armored_ship_remaining = armored_ship_remaining;
	}
    
    public int getPlanet_id() {
        return planet_id;
    }
}
