package saving;

import java.sql.*;

public class Progress {

    public Connection connect() {
        String url = "jdbc:sqlite:c:/Users/madag/p3/AdventureDatabase.db";
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Couldn't connect to the database: " + e.getMessage());
            return null;
        }
    }

    public void saveProgressToDatabase(String playerName, int timesPlayed, int positionX, int positionY) {
        String updateQuery = """
                INSERT INTO players (name, timesplayed, positionX, positionY)
                VALUES (?, ?, ?, ?)
                ON CONFLICT(name) DO UPDATE SET
                    timesplayed = timesplayed + excluded.timesplayed,
                    positionX = excluded.positionX,
                    positionY = excluded.positionY;
                """;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

            pstmt.setString(1, playerName); // The name of the player
            pstmt.setInt(2, timesPlayed);  // Number of times played
            pstmt.setInt(3, positionX);    // Player's X position
            pstmt.setInt(4, positionY);    // Player's Y position

            pstmt.executeUpdate();
            System.out.println("Progress saved successfully for player: " + playerName);
        } catch (SQLException e) {
            System.out.println("Error saving progress: " + e.getMessage());
        }
    }

    public int[] loadLastKnownPosition(String playerName) {
        String selectQuery = "SELECT positionX, positionY FROM players WHERE name = ?";
        int[] lastKnownPosition = {0, 0}; // Default position: {positionX, positionY}

        try (Connection conn = connect();
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
}
