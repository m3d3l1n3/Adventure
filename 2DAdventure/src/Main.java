import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import rendering.SpriteEngine;
import rendering.SpriteSheet;
import rendering.SpriteSheetBuilder;
import saving.Progress;

public class Main {

    public static void main(String[] args) {

        try {
            if (args.length == 0) {
                throw new ArgumentsException("Not enough arguments");
            } else if (args.length == 1) {
                if (!args[0].equals("play")) {
                    throw new ArgumentsException("Incorrect argument");
                }
            } else if (args.length == 2) {
                if (args[0].equals("play")) {
                    int option;
                    if (args[1].equals(MapOptions.map1.toString()))
                        option = 1;
                    else if (args[1].equals(MapOptions.map2.toString()))
                        option = 2;
                    else if (args[1].equals(MapOptions.map3.toString()))
                        option = 3;
                    else if (args[1].equals(MapOptions.random.toString())) {
                        Random rand = new Random();
                        option = (rand.nextInt()) % 3;
                        option++;
                    } else throw new NotInOptions("Incorrect map option");

                    setUp("map" + option);
                } else throw new ArgumentsException("Incorrect argument");
            }
        } catch (ArgumentsException | NotInOptions e) {
            System.err.println(e.getMessage());
        }
        // setUp(mapName);

    }

    private static void setUp(String mapName) {
        System.out.println("Setting up stuff.");
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Adventure");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Progress progress = new Progress();
        int[] initialPositions = progress.readProgress(progress.fileForReading());
        System.out.println("Choose a character design(number between 1 and 6): ");
        Panel.readData = getValidatedData();
        Panel panel = new Panel(mapName,initialPositions[0],initialPositions[1]);

        GameThread gameThread = new GameThread();
        Controller controller = new Controller(panel);

        frame.add(panel);
        frame.pack(); // sets the size properly
        frame.addKeyListener(panel.keyHandler);


    }

    private static int getValidatedData() {

        try {
            int asciiDesignNumber = System.in.read();
            if (asciiDesignNumber == 49 || asciiDesignNumber == 50 || asciiDesignNumber == 51 || asciiDesignNumber == 52 || asciiDesignNumber == 53 || asciiDesignNumber == 54)
                return asciiDesignNumber;
            throw new NotInOptions("Incorrect character design");

        } catch (NotInOptions | IOException e) {
            System.err.println(e.getMessage());
            return 1;
        }
        // return designNumber;
    }

}