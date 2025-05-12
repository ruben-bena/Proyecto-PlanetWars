package ddbb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnemyArmyTable implements Table {
    private Database db;
    private int enemy_army_id;
    private int num_battle;
    private int light_hunter_threat;
    private int light_hunter_destroyed;
    private int heavy_hunter_threat;
    private int heavy_hunter_destroyed;
    private int battleship_threat;
    private int battleship_destroyed;
    private int armored_ship_threat;
    private int armored_ship_destroyed;

    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@//localhost:1521/freepdb1";
        String username = "planetWars";
        String pass = "planetWars";
        EnemyArmyTable eat = new EnemyArmyTable(
            new Database(url, username, pass),
            1, 1,
            1, 1,
            1, 1,
            1, 1,
            1, 1
        );

        // insertion test
        eat.insertRow();

        // modifying test
        eat.setHeavy_hunter_threat(20);
        eat.modifyRow();

        // getRow test
        eat.getRow(1);
    }

    public EnemyArmyTable(Database db,
                          int enemy_army_id,
                          int num_battle,
                          int light_hunter_threat,
                          int light_hunter_destroyed,
                          int heavy_hunter_threat,
                          int heavy_hunter_destroyed,
                          int battleship_threat,
                          int battleship_destroyed,
                          int armored_ship_threat,
                          int armored_ship_destroyed) {
        this.db = db;
        this.enemy_army_id = enemy_army_id;
        this.num_battle = num_battle;
        this.light_hunter_threat = light_hunter_threat;
        this.light_hunter_destroyed = light_hunter_destroyed;
        this.heavy_hunter_threat = heavy_hunter_threat;
        this.heavy_hunter_destroyed = heavy_hunter_destroyed;
        this.battleship_threat = battleship_threat;
        this.battleship_destroyed = battleship_destroyed;
        this.armored_ship_threat = armored_ship_threat;
        this.armored_ship_destroyed = armored_ship_destroyed;
    }

    @Override
    public void insertRow() {
        String insertQuery = "INSERT INTO Enemy_army (" +
            "num_battle, light_hunter_threat, light_hunter_destroyed, " +
            "heavy_hunter_threat, heavy_hunter_destroyed, " +
            "battleship_threat, battleship_destroyed, " +
            "armored_ship_threat, armored_ship_destroyed" +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        System.out.println("query for inserting data executed");
        try (PreparedStatement ps = db.getConnection().prepareStatement(insertQuery, new String[] { "enemy_army_id" })) {
            System.out.println("PreparedStatement created");
            ps.setInt(1, num_battle);
            ps.setInt(2, light_hunter_threat);
            ps.setInt(3, light_hunter_destroyed);
            ps.setInt(4, heavy_hunter_threat);
            ps.setInt(5, heavy_hunter_destroyed);
            ps.setInt(6, battleship_threat);
            ps.setInt(7, battleship_destroyed);
            ps.setInt(8, armored_ship_threat);
            ps.setInt(9, armored_ship_destroyed);

            int affectedRows = ps.executeUpdate();
            System.out.println("insertion in Enemy_army executed");

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        enemy_army_id = generatedKeys.getInt(1);
                        System.out.println("new enemy_army_id: " + enemy_army_id);
                    } else {
                        System.out.println("could not obtain generated enemy_army_id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getRow(int enemy_army_id) {
        String selectQuery = "SELECT num_battle, light_hunter_threat, light_hunter_destroyed, " +
            "heavy_hunter_threat, heavy_hunter_destroyed, battleship_threat, battleship_destroyed, " +
            "armored_ship_threat, armored_ship_destroyed " +
            "FROM Enemy_army WHERE enemy_army_id = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(selectQuery)) {
            ps.setInt(1, enemy_army_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    num_battle = rs.getInt("num_battle");
                    light_hunter_threat = rs.getInt("light_hunter_threat");
                    light_hunter_destroyed = rs.getInt("light_hunter_destroyed");
                    heavy_hunter_threat = rs.getInt("heavy_hunter_threat");
                    heavy_hunter_destroyed = rs.getInt("heavy_hunter_destroyed");
                    battleship_threat = rs.getInt("battleship_threat");
                    battleship_destroyed = rs.getInt("battleship_destroyed");
                    armored_ship_threat = rs.getInt("armored_ship_threat");
                    armored_ship_destroyed = rs.getInt("armored_ship_destroyed");
                    System.out.println("recovered row for enemy_army_id=" + enemy_army_id);
                    
                    if (this.enemy_army_id != enemy_army_id) {
                        System.out.println("enemy_army_id before = " + this.enemy_army_id + ", enemy_army_id now = " + enemy_army_id);
                        this.enemy_army_id = enemy_army_id;
                    }
                } else {
                    System.out.println("No row found with enemy_army_id=" + enemy_army_id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifyRow() {
        String updateQuery = "UPDATE Enemy_army SET " +
            "num_battle = ?, light_hunter_threat = ?, light_hunter_destroyed = ?, " +
            "heavy_hunter_threat = ?, heavy_hunter_destroyed = ?, " +
            "battleship_threat = ?, battleship_destroyed = ?, " +
            "armored_ship_threat = ?, armored_ship_destroyed = ? " +
            "WHERE enemy_army_id = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(updateQuery)) {
            ps.setInt(1, num_battle);
            ps.setInt(2, light_hunter_threat);
            ps.setInt(3, light_hunter_destroyed);
            ps.setInt(4, heavy_hunter_threat);
            ps.setInt(5, heavy_hunter_destroyed);
            ps.setInt(6, battleship_threat);
            ps.setInt(7, battleship_destroyed);
            ps.setInt(8, armored_ship_threat);
            ps.setInt(9, armored_ship_destroyed);
            ps.setInt(10, enemy_army_id);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("success modifying the row with enemy_army_id=" + enemy_army_id);
            } else {
                System.out.println("row with enemy_army_id = " + enemy_army_id + " not found. no row has been modified");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getters and setters
    public int getEnemy_army_id() {
        return enemy_army_id;
    }

    public void setEnemy_army_id(int enemy_army_id) {
        this.enemy_army_id = enemy_army_id;
    }

    public void setNum_battle(int num_battle) {
        this.num_battle = num_battle;
    }

    public void setLight_hunter_threat(int light_hunter_threat) {
        this.light_hunter_threat = light_hunter_threat;
    }

    public void setLight_hunter_destroyed(int light_hunter_destroyed) {
        this.light_hunter_destroyed = light_hunter_destroyed;
    }

    public void setHeavy_hunter_threat(int heavy_hunter_threat) {
        this.heavy_hunter_threat = heavy_hunter_threat;
    }

    public void setHeavy_hunter_destroyed(int heavy_hunter_destroyed) {
        this.heavy_hunter_destroyed = heavy_hunter_destroyed;
    }

    public void setBattleship_threat(int battleship_threat) {
        this.battleship_threat = battleship_threat;
    }

    public void setBattleship_destroyed(int battleship_destroyed) {
        this.battleship_destroyed = battleship_destroyed;
    }

    public void setArmored_ship_threat(int armored_ship_threat) {
        this.armored_ship_threat = armored_ship_threat;
    }

    public void setArmored_ship_destroyed(int armored_ship_destroyed) {
        this.armored_ship_destroyed = armored_ship_destroyed;
    }
}