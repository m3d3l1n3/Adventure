package dboperations;

import saving.Progress;

import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class DatabaseOperations {
    public Connection conn;
    public DatabaseOperations() {
        connect();
    }
        public void connect() {
            String url = "jdbc:sqlite:c:/Users/madag/p3/AdventureDatabase.db";
            try {
                this.conn= DriverManager.getConnection(url);
            } catch (SQLException e) {
                System.out.println("Couldn't connect to the database: " + e.getMessage());
                this.conn = null;
            }
        }
        public boolean updatePlayerTable(String playerName, int timesPlayed, int positionX, int positionY) {
            String updateQuery = """
                INSERT INTO players (name, timesplayed, positionX, positionY)
                VALUES (?, ?, ?, ?)
                ON CONFLICT(name) DO UPDATE SET
                    timesplayed = timesplayed + excluded.timesplayed,
                    positionX = excluded.positionX,
                    positionY = excluded.positionY;
                """;
            try (
                 PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

                pstmt.setString(1, playerName); // The name of the player
                pstmt.setInt(2, timesPlayed);  // Number of times played
                pstmt.setInt(3, positionX);    // Player's X position
                pstmt.setInt(4, positionY);    // Player's Y position

                pstmt.executeUpdate();
                System.out.println("Progress saved successfully for player: " + playerName);
                return true;
            } catch (SQLException e) {
                System.out.println("Error saving progress: " + e.getMessage());
                return false;
            }
        }
        public int[] lastKnownPosition(String playerName) {
            String selectQuery = "SELECT positionX, positionY FROM players WHERE name = ?";
            int[] lastKnownPosition = {0, 0}; // Default position: {positionX, positionY}

            try (
                 PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {

                pstmt.setString(1, playerName);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    lastKnownPosition[0] = rs.getInt("positionX");
                    lastKnownPosition[1] = rs.getInt("positionY");
                    System.out.println("Last known position loaded for player: " + playerName);
                } else {
                    System.out.println("No progress found for player: " + playerName);
                }
            } catch (SQLException e) {
                System.out.println("Error loading last known position: " + e.getMessage());
            }

            return lastKnownPosition;
        }
        public boolean updatePlayerTable(String name, int timesPlayed, int id){
            String sql = "UPDATE players SET name = ?, timesplayed = ? WHERE id = ?";
            try (
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, name);
                pstmt.setInt(2, timesPlayed);
                pstmt.setInt(3, id);
                int res = pstmt.executeUpdate();
                if(res!=0) {return true;}
                System.out.println("Player updated successfully.");
            } catch (SQLException e) {
                System.out.println("Error updating player: " + e.getMessage());
                return false;

            }
            return true;
        }
        public boolean deletePlayer(int id){
            String sql = "DELETE FROM players WHERE id = ?";
            try (
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, id);
                pstmt.executeUpdate();

                System.out.println("Player deleted successfully.");
            } catch (SQLException e) {
                System.out.println("Error deleting player: " + e.getMessage());
            }
            return false;
        }

    public void loadPlayerData(DefaultTableModel model) {
        String sql = "SELECT * FROM players";
        model.setRowCount(0);
        try (
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("id"), rs.getString("name"), rs.getInt("timesplayed")});
            }
        } catch (SQLException e) {
            System.out.println("Error loading player data: " + e.getMessage());
        }
    }
    public boolean validateAdminKey(String key) {
        String sql = "SELECT * FROM Admin WHERE key = ?";
        System.out.println("Key"+key);
        try (
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(key)); // Convert string key to integer
            ResultSet rs = pstmt.executeQuery();

            return rs.next(); // If there’s a result, the key is valid

        } catch (NumberFormatException e) {
            System.out.println("Invalid key format: " + e.getMessage());
            return false; // Key wasn't a valid number
        } catch (SQLException e) {
            System.out.println("Error validating admin key: " + e.getMessage());
            return false;
        }
    }
    public boolean checkTheDatabase(String playerName) {
        Progress progress = new Progress();
        String sql = "select * from players where name=?";
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, playerName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Exists player: " + playerName);
                return true;
            } else {
                progress.saveProgressToDatabase(playerName,1,0,0);
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error loading last known position: " + e.getMessage());
            return false;
        }
    }


}
