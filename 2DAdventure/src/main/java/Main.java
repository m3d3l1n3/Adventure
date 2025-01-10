import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;

import rendering.SpriteEngine;
import rendering.SpriteSheet;
import rendering.SpriteSheetBuilder;
import saving.Progress;

public class Main {

    public static void main(String[] args) {

        MainMenu(args);

        //validateTerminalArgs(args);
        // setUp(mapName);

    }

    private static void MainMenu(String[] args) {
        JFrame frame = new JFrame();

        JButton submitButton = new JButton("Submit");
        JButton startGameButton = new JButton("Start");

        submitButton.setBounds(50,50,50,50);
        startGameButton.setBounds(50,100,50,50);

        JTextField textField = new JTextField("Enter your name");
        textField.setBounds(50,150,50,50);
        JPanel panel = new JPanel();
        panel.add(textField);
        panel.add(submitButton);
        panel.add(startGameButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(400,400);
        frame.setResizable(false);
        frame.setTitle("Menu");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.add(panel);
        setUpButtonListeners(startGameButton,submitButton, args,frame,textField);
    }

    public static void setUpButtonListeners(JButton startGameButton, JButton submitButton,String[] args, JFrame frame,JTextField textField) {
        ActionListener startGameButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField.getText().equals("Enter your name")) {
                    validateTerminalArgs(args, "Player1");
                }
                else {
                    validateTerminalArgs(args, textField.getText());
                }
                frame.dispose();
            }
        };
        startGameButton.addActionListener(startGameButtonListener);

        ActionListener submitButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredName = textField.getText();
                if (enteredName.equals("Enter your name")) {
                    enteredName = "Player1";
                }

                if (enteredName.equals("admin")) {
                    openAdminKeyFrame();
                } else {
                    checkTheDatabase(enteredName);
                }
            }
        };
        submitButton.addActionListener(submitButtonListener);
    }
    private static boolean checkTheDatabase(String playerName) {
        Progress progress = new Progress();
        String sql = "select * from players where name=?";
        try{
        Connection conn = progress.connect();
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
    private static void validateTerminalArgs(String[] args,String playerName) {
        try {
            if (args.length == 0) {
                throw new ArgumentsException("Not enough arguments");
            } else if (args.length == 1) {
                if (!args[0].equals("play")) {
                    throw new ArgumentsException("Incorrect argument");
                }
            } else if (args.length == 2) {
                if (args[0].equals("play")) {
                    int option=1;
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

                    setUp("map" + option,playerName);

                } else throw new ArgumentsException("Incorrect argument");
            }
        } catch (ArgumentsException | NotInOptions e) {
            System.err.println(e.getMessage());
        }
    }

    private static void setUp(String mapName, String playerName) {
        System.out.println("Setting up stuff.");
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Adventure");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //here needs to validate that
        Progress progress = new Progress();
        int[] initialPositions = progress.loadLastKnownPosition("");

        System.out.println("Choose a character design(number between 1 and 6): ");
        Panel.readData = getValidatedData();
        Panel panel = new Panel(mapName,initialPositions[0],initialPositions[1],playerName);

        GameThread gameThread = new GameThread();
        Controller controller = new Controller(panel);
        frame.addKeyListener(panel.keyHandler);
        frame.add(panel);
        frame.pack(); // sets the size properly

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

    private static void openAdminKeyFrame() {
        JFrame adminFrame = new JFrame("Admin Key Required");
        adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adminFrame.setSize(300, 150);
        adminFrame.setLayout(new GridLayout(3, 1));

        JLabel keyLabel = new JLabel("Enter Admin Key:", SwingConstants.CENTER);
        JPasswordField keyField = new JPasswordField();
        JButton submitKeyButton = new JButton("Submit");

        adminFrame.add(keyLabel);
        adminFrame.add(keyField);
        adminFrame.add(submitKeyButton);

        submitKeyButton.addActionListener(e -> {
            String enteredKey = new String(keyField.getPassword());
            if (validateAdminKey(enteredKey)) {
                adminFrame.dispose();
                openAdminPanel();
            } else {
                JOptionPane.showMessageDialog(adminFrame, "Invalid Key", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        adminFrame.setLocationRelativeTo(null);
        adminFrame.setVisible(true);
    }

    private static boolean validateAdminKey(String key) {
        String sql = "SELECT * FROM Admin WHERE key = ?";
        try (Connection conn = new Progress().connect();
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


    private static void openAdminPanel() {

        JFrame adminPanel = new JFrame("Admin Panel");
        adminPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adminPanel.setSize(600, 400);




        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name", "TimesPlayed"});
        table.setModel(model);

        JScrollPane scrollPane = new JScrollPane(table);
        adminPanel.add(scrollPane, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel();
        JButton updateButton = new JButton("Update Player");
        JButton deleteButton = new JButton("Delete Player");
        bottomPanel.add(updateButton);
        bottomPanel.add(deleteButton);
        adminPanel.add(bottomPanel, BorderLayout.SOUTH);

        loadPlayerData(model);



        updateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) model.getValueAt(selectedRow, 0);
                String name = (String) model.getValueAt(selectedRow, 1);
                int timesPlayed = (int) model.getValueAt(selectedRow, 2);
                updatePlayer(id, name, timesPlayed);
                loadPlayerData(model);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) model.getValueAt(selectedRow, 0);
                deletePlayer(id);
                loadPlayerData(model);
            }
        });

        adminPanel.setLocationRelativeTo(null);
        adminPanel.setVisible(true);
    }

    private static void loadPlayerData(DefaultTableModel model) {
        String sql = "SELECT * FROM players";
        model.setRowCount(0);
        try (Connection conn = new Progress().connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("id"), rs.getString("name"), rs.getInt("timesplayed")});
            }
        } catch (SQLException e) {
            System.out.println("Error loading player data: " + e.getMessage());
        }
    }

    private static void updatePlayer(int id, String name, int timesPlayed) {
        String sql = "UPDATE players SET name = ?, timesplayed = ? WHERE id = ?";
        try (Connection conn = new Progress().connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, timesPlayed);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();

            System.out.println("Player updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating player: " + e.getMessage());

        }

    }

    private static void deletePlayer(int id) {
        String sql = "DELETE FROM players WHERE id = ?";
        try (Connection conn = new Progress().connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            System.out.println("Player deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting player: " + e.getMessage());
        }
    }
}