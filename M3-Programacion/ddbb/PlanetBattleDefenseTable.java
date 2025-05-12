package ddbb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlanetBattleDefenseTable implements Table {
    private Database db;
    private int planet_battle_defense_id;
    private int num_battle;
    private int missile_launcher_built;
    private int missile_launcher_destroyed;
    private int ion_cannon_built;
    private int ion_cannon_destroyed;
    private int plasma_canon_built;
    private int plasma_canon_destroyed;

    public static void main(String[] args) {
        // String url = "jdbc:oracle:thin:@//localhost:1521/freepdb1"; // Local VM Oracle ddbb
        String url = "jdbc:oracle:thin:@//localhost:1521/freepdb1"; // Local VM Oracle ddbb
        String username = "planetWars";
        String pass = "planetWars";
        PlanetBattleDefenseTable pbdt = new PlanetBattleDefenseTable(new Database(url, username, pass),1,1,1,1,1,1,1,1);
        
        // insertion test
        pbdt.insertRow();

        // modifying test
        pbdt.setIon_cannon_built(400);
        pbdt.modifyRow();

        // // getRow test
        // pbdt.getRow(1);
    }

    public PlanetBattleDefenseTable(Database db, int planet_battle_defense_id, int num_battle,
			int missile_launcher_build, int missile_launcher_destroyed, int ion_cannon_built, int ion_cannon_destroyed,
			int plasma_cannon_built, int plasma_canon_destroyed) {
		super();
		this.db = db;
		this.planet_battle_defense_id = planet_battle_defense_id;
		this.num_battle = num_battle;
		this.missile_launcher_built = missile_launcher_build;
		this.missile_launcher_destroyed = missile_launcher_destroyed;
		this.ion_cannon_built = ion_cannon_built;
		this.ion_cannon_destroyed = ion_cannon_destroyed;
		this.plasma_canon_built = plasma_cannon_built;
		this.plasma_canon_destroyed = plasma_canon_destroyed;
	}

    @Override
    public void insertRow() {
        String insertQuery = "INSERT INTO Planet_battle_defense (" +
            "num_battle, missile_launcher_built, missile_launcher_destroyed, " +
            "ion_cannon_built, ion_cannon_destroyed, " +
            "plasma_canon_built, plasma_canon_destroyed" +
            ") VALUES (?, ?, ?, ?, ?, ?, ?)";
        System.out.println("query for inserting data executed");
        try (PreparedStatement ps = db.getConnection().prepareStatement(insertQuery, new String[] { "planet_battle_defense_id" });) {
            // inserting data in ddbb
            System.out.println("PreparedStatement created");
            ps.setInt(1, num_battle);
            ps.setInt(2, missile_launcher_built);
            ps.setInt(3, missile_launcher_destroyed);
            ps.setInt(4, ion_cannon_built);
            ps.setInt(5, ion_cannon_destroyed);
            ps.setInt(6, plasma_canon_built);
            ps.setInt(7, plasma_canon_destroyed);

            int affectedRows = ps.executeUpdate();
            System.out.println("insertion in Planet_battle_defense executed");

            // saving the id in the attribute for modifying table later
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        planet_battle_defense_id = generatedKeys.getInt(1);
                        System.out.println("new planet_battle_defense_id: " + planet_battle_defense_id);
                    } else {
                        System.out.println("could not obtain generated planet_battle_defense_id");
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
        String updateQuery = "UPDATE Planet_battle_defense SET " +
            "num_battle = ?, missile_launcher_built = ?, missile_launcher_destroyed = ?, " +
            "ion_cannon_built = ?, ion_cannon_destroyed = ?, " +
            "plasma_canon_built = ?, plasma_canon_destroyed = ?" +
            "WHERE planet_battle_defense_id = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(updateQuery)) {
            ps.setInt(1, num_battle);
            ps.setInt(2, missile_launcher_built);
            ps.setInt(3, missile_launcher_destroyed);
            ps.setInt(4, ion_cannon_built);
            ps.setInt(5, ion_cannon_destroyed);
            ps.setInt(6, plasma_canon_built);
            ps.setInt(7, plasma_canon_destroyed);
            ps.setInt(8, planet_battle_defense_id);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("success modifying the row with planet_battle_defense_id=" + planet_battle_defense_id);
            } else {
                System.out.println("row with planet_battle_defense_id = " + planet_battle_defense_id + " not found. no row has been modified");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setPlanet_battle_defense_id(int planet_battle_defense_id) {
		this.planet_battle_defense_id = planet_battle_defense_id;
	}

	public void setNum_battle(int num_battle) {
		this.num_battle = num_battle;
	}

	public void setMissile_launcher_build(int missile_launcher_build) {
		this.missile_launcher_build = missile_launcher_build;
	}

	public void setMissile_launcher_destroyed(int missile_launcher_destroyed) {
		this.missile_launcher_destroyed = missile_launcher_destroyed;
	}

	public void setIon_cannon_built(int ion_cannon_built) {
		this.ion_cannon_built = ion_cannon_built;
	}

	public void setIon_cannon_destroyed(int ion_cannon_destroyed) {
		this.ion_cannon_destroyed = ion_cannon_destroyed;
	}

	public void setPlasma_cannon_built(int plasma_cannon_built) {
		this.plasma_cannon_built = plasma_cannon_built;
	}

	public void setPlasma_canon_destroyed(int plasma_canon_destroyed) {
		this.plasma_canon_destroyed = plasma_canon_destroyed;
	}
}
