package saving;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Progress {

    public File fileForWritting() {
        File file = new File("2DAdventure/src/saving/playerProgress.txt");
        if (file.exists()) {
            return file;
        } else {
            try {
                file.createNewFile();
                return file;
            } catch (IOException e) {
                System.out.println("File could not be created.");
                return null;
            }

        }
    }
    public File fileForReading() {
        File file = new File("2DAdventure/src/saving/playerProgress.txt");
        if (file.exists()) {
            return file;
        }
        return null;
    }

    public void writeProgress(File progressFile, int positionX, int positionY) {

        FileWriter fw;
        try {
            fw = new FileWriter(progressFile);
            fw.append(String.valueOf(positionX)).append(" ").append(String.valueOf(positionY)).append("\n");
            fw.close();
            //System.out.println("Progress has been saved.");
        } catch (IOException e) {
            System.out.println("File could not be written.");        }
    }
    public int[] readProgress(File progressFile) {
        Scanner fr;
        String line;
        String[] values;
        int[] intValues = {0,0};
        try {
            fr = new Scanner(progressFile);
            while(fr.hasNext()) {
                line = fr.nextLine();
                values = line.split(" ");
                intValues[0] = Integer.parseInt(values[0]);
                intValues[1] = Integer.parseInt(values[1]);
            }
            System.out.println("Progress has been loaded.");
            return intValues;
        } catch (FileNotFoundException e) {
            System.out.println("Progress file not found (reading).");
            return intValues;
        }
    }
}
