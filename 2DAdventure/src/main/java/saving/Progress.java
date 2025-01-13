package saving;
import dboperations.DatabaseOperations;

public class Progress {
    DatabaseOperations db = new DatabaseOperations();
    public void saveProgressToDatabase(String playerName, int timesPlayed, int positionX, int positionY) {
        db.updatePlayerTable(playerName, timesPlayed, positionX, positionY);
    }

    public int[] loadLastKnownPosition(String playerName) {
        return db.lastKnownPosition(playerName);
    }
}
