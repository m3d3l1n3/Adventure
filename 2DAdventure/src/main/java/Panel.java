import entity.Player;
import rendering.Drawable;
import rendering.SpriteEngine;
import rendering.SpriteSheet;
import rendering.SpriteSheetBuilder;
import saving.Progress;
import tiles.TileManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*; // abstract window toolkit
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Panel extends JPanel implements Drawable {

    private final int tileSize = 16; // calssic 2 D adventure games had 16x16 sized characters
    private final int scale = 3;
    private final int finalTileSize = tileSize * scale;
    private final int maxScreenWidth = 16 * finalTileSize; //768 pixels
    private final int maxScreenHeight = 16 * finalTileSize;//576 pixels
    Player player;
    private String mapName;
    KeyHandler keyHandler = new KeyHandler();
    private SpriteSheet spriteSheet;
    private SpriteEngine spriteEngine = new SpriteEngine(60);
    private SpriteSheet[] playerSpriteSheets;
    TileManager tileManager;
    private int numberDesign = getNumberDesign(readData);
    public static int readData;
    public Progress progress = new Progress();


    public int getNumberDesign(int readData) {
        if (readData == 49)
            return 1;
        if (readData == 50)
            return 2;
        if (readData == 51)
            return 3;
        if (readData == 52)
            return 4;
        if (readData == 53)
            return 5;
        if (readData == 54)
            return 6;
        return 1;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getMapName() {
        return mapName;
    }

    public Panel(String mapName, int positionX, int positionY, String playerName) {
        this.setPreferredSize(new Dimension(maxScreenWidth, maxScreenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); //for rendering performance
        this.setFocusable(true);
        this.keyHandler.setInitialKeyState();
        this.setMapName(mapName);
        this.tileManager = new TileManager(maxScreenWidth, maxScreenHeight, getMapName());
        this.player = new Player(positionX, positionY, 4, 100, null,playerName);
        progress.saveProgressToDatabase(player.getName(),1,player.getPositionX(),player.getPositionY());

    }

    // wanna construct here the checker of collisions. buffered image offers the possiblity to getHeight and getWidth
    // therefore the size of the tile can be determined
    // the math: if the size of the player and the future possible step is stepping over a tile with collision it is
    // not allowed (text will be printed in the terminal for that)
    // How do I make sure that the step is not allowed, without stopping the gameloop?
    public void update() {
        int tileX = player.getPositionX();
        int tileY = player.getPositionY();
        boolean moved = false;
        int directionX,directionY;
        int futureX,futureY,futureTileX,futureTileY;
//        if (keyHandler.keyState[0] == 1
//        ) {
//            futureY = player.getPositionY()- player.getSpeed();
//            futureTileY = futureY/finalTileSize;
//            //has to be divided so it gets to proper size(the map is not the size of the screen)
//            if (isTileWalkable(tileX, futureTileY)) {
//                player.setPositionY(futureY);
//                //System.out.println("W: " + tileManager.getMapTile(player.getPositionX(), player.getPositionY()));
//                //return;
//                moved = true;
//            } else {
//                System.out.println("Collision detected: up");}
//        }
//        if (keyHandler.keyState[1] == 1
//        ) {
//            futureY = player.getPositionY()+ player.getSpeed();
//            futureTileY = futureY/finalTileSize;
//            if (isTileWalkable(tileX,futureTileY)) {
//                player.setPositionY(futureY);
//                moved = true;
//            } else {
//                System.out.println("Collision detected: down");}
//        }
//        if (keyHandler.keyState[2] == 1
//        ) {
//            futureX = player.getPositionX()- player.getSpeed();
//            futureTileX = futureX/finalTileSize;
//            if (isTileWalkable(futureTileX,tileY)) {
//                player.setPositionX(futureX);
//                moved = true;
//            } else{
//                System.out.println("Collision detected: left");}
//        }
//        if (keyHandler.keyState[3] == 1
//        ) {
//            futureX = player.getPositionX()+ player.getSpeed();
//            futureTileX = futureX/finalTileSize;
//            if (isTileWalkable(futureTileX,tileY)) {
//                player.setPositionX(futureX);
//                moved=true;
//            } else {
//                System.out.println("Collision detected: right");}
//        }
        //if(!moved){
        //    System.out.println("Player didnt move");
        //}

        if (keyHandler.keyState[0] == 1){
            directionX = 0;
            directionY = -1;
    } else if (keyHandler.keyState[1] == 1) {
        directionX = 0;
        directionY = 1;
    } else if (keyHandler.keyState[2] == 1) {
        directionX = -1;
        directionY = 0;
    } else if (keyHandler.keyState[3] == 1) {
        directionX = 1;
        directionY = 0;
    } else {
        directionX = 0;
        directionY = 0;
    }
         futureTileX = (player.getPositionX() + player.getSpeed() * directionX) / finalTileSize;
         futureTileY = (player.getPositionY() + player.getSpeed() * directionY) / finalTileSize;

        if (isTileWalkable(futureTileX, futureTileY)) {
            player.setPositionX(player.getPositionX() + player.getSpeed() * directionX);
            player.setPositionY(player.getPositionY() + player.getSpeed() * directionY);
        } else {
            System.out.println("Collision detected!");
        }

    }
    public boolean isTileWalkable(int tileX, int tileY) {
        int tileValue = tileManager.getMapTile(tileX, tileY);
        System.out.println("Checking tile [" + tileX + ", " + tileY + "] = " + tileValue);
        return tileValue == 0; // Walkable tiles are 0
    }

    @Override
    public SpriteSheet loadAnimation(int gridX, int gridY, int designNumber) {

        try {
            System.out.println("Loading animation:" + designNumber);
            String pathName = "src/main/java/sprites/player/Char_00" + designNumber + ".png";
            BufferedImage sheet = ImageIO.read(new File(pathName));
            SpriteSheet spriteSheet = new SpriteSheetBuilder().
                    withSheet(sheet).
                    withColumns(4).
                    withRows(4).
                    withSpriteCount(16).
                    build(gridX, gridY);
            return spriteSheet;
        } catch (IOException | NullPointerException ex) {
            System.out.println("Charcter design could not be loaded.\n");
            ex.printStackTrace();
        }
        return null;
    }

    private SpriteSheet sheetDown = loadAnimation(0, 0, numberDesign);
    private SpriteSheet sheetUp = loadAnimation(0, 0, numberDesign);
    private SpriteSheet sheetLeft = loadAnimation(0, 0, numberDesign);
    private SpriteSheet sheetRight = loadAnimation(0, 0, numberDesign);
    private SpriteSheet sheetIdle = loadAnimation(0, 0, numberDesign);

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        if (!tileManager.getStatus())
            tileManager.draw2(g2d, maxScreenHeight, maxScreenHeight, finalTileSize);
        else
            tileManager.draw1(g2d, maxScreenHeight, maxScreenHeight, finalTileSize);


        if (keyHandler.keyState[0] == 0 && keyHandler.keyState[1] == 0 && keyHandler.keyState[2] == 0 && keyHandler.keyState[3] == 0) {
            BufferedImage sprite = sheetIdle.getSprite(spriteEngine.getCycleProgress());
            //BufferedImage sprite = sheet.getSprite(spriteEngine.getCycleProgress());
            g2d.drawImage(sprite, player.getPositionX(), player.getPositionY(), this);
        }
        if (keyHandler.keyState[0] == 1) {
            BufferedImage sprite = sheetUp.getSprite(spriteEngine.getCycleProgress());
            //BufferedImage sprite = sheet.getSprite(spriteEngine.getCycleProgress());
            g2d.drawImage(sprite, player.getPositionX(), player.getPositionY(), this);
        } else if (keyHandler.keyState[1] == 1) {
            BufferedImage sprite = sheetDown.getSprite(spriteEngine.getCycleProgress());
            //BufferedImage sprite = sheet.getSprite(spriteEngine.getCycleProgress());
            g2d.drawImage(sprite, player.getPositionX(), player.getPositionY(), this);
        } else if (keyHandler.keyState[2] == 1) {
            BufferedImage sprite = sheetLeft.getSprite(spriteEngine.getCycleProgress());
            //BufferedImage sprite = sheet.getSprite(spriteEngine.getCycleProgress());
            g2d.drawImage(sprite, player.getPositionX(), player.getPositionY(), this);
        } else if (keyHandler.keyState[3] == 1) {
            BufferedImage sprite = sheetRight.getSprite(spriteEngine.getCycleProgress());
            //BufferedImage sprite = sheet.getSprite(spriteEngine.getCycleProgress());
            g2d.drawImage(sprite, player.getPositionX(), player.getPositionY(), this);
        }

        g2d.dispose();
    }


    @Override
    public void paint(Graphics2D g2d, double progress) {
        g2d.drawImage(
                spriteSheet.getSprite(progress),
                player.getPositionX(),
                player.getPositionY(),
                null);

    }

}
