package tiles;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class TileManager {
    Tile[] tiles;
    boolean status;
    int[][] mapTile;
    String mapName;

    ImageObserver imageObserver = new ImageObserver() {
        @Override
        public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
            return false;
        }
    };

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public TileManager(int maxScreenWidth, int maxScreenHeight, String mapName) {

        tiles = new Tile[2];
        getTileImage();
        this.mapTile = new int[maxScreenWidth][maxScreenHeight];
        setMapName(mapName);
        loadMap();

    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapTile(int col, int row, int val) {
        this.mapTile[col][row] = val;
    }

    public int getMapTile(int col, int row) {
        return this.mapTile[col][row];
    }

    private void getTileImage() {
        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(TileManager.class.getResource("../sprites/map/land.png")));
            tiles[0] = new Tile();
            tiles[0].setImage(image);
            BufferedImage image1 = ImageIO.read(Objects.requireNonNull(TileManager.class.getResource("../sprites/map/flooring.png")));
            tiles[1] = new Tile();
            tiles[1].setImage(image1);
            setStatus(true);
            //System.out.println("Image loaded\n.");
        } catch (IOException | IllegalArgumentException | NullPointerException e) {
            if (e.getClass() == IOException.class) {
            }
            System.out.println("Tiles could not be loaded.\n");
            if (e.getClass() == IllegalArgumentException.class)
                System.out.println("Invalid path\n");
            if (e.getClass() == NullPointerException.class)
                System.out.println("There are no tiles to be loaded.\n");
            setStatus(false);
        }
    }

    public void loadMap() {
        try {
            int col;
            int row = 0;
            List<String> lines = new ArrayList<>();
            File mapFile = new File("2DAdventure/src/sprites/map/" + getMapName() + ".txt");

            Scanner scanner = new Scanner(mapFile);
            String line;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                String[] numbers = line.split(" ");
                lines.add(line);
                for (col = 0; col < numbers.length; col++) {
                    if (Objects.equals(numbers[col], "0"))
                        setMapTile(col, row, 0);
                    if (Objects.equals(numbers[col], "1"))
                        setMapTile(col, row, 1);
                    if (Objects.equals(numbers[col], "2"))
                        setMapTile(col, row, 2);
                }
                row++;
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println("Map couldn't be found.\n");
        }
    }

    public void draw1(Graphics2D g2d, int maxScreenCol, int maxScreenRow, int tileSize) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        while (col < maxScreenCol && row < maxScreenRow) {
            int tileNum = getMapTile(col, row);
            g2d.drawImage(tiles[tileNum].getImage(), x, y, imageObserver);
            col++;
            x += tileSize;
            if (col == maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += tileSize;
            }

        }
    }

    public void draw2(Graphics2D g2d, int maxScreenCol, int maxScreenRow, int tileSize) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        while (col < maxScreenCol && row < maxScreenRow) {
            int tileNum = getMapTile(col, row);
            if (tileNum == 0) {
                g2d.setColor(Color.GREEN);
                g2d.fillRect(x, y, tileSize, tileSize);
            } else if (tileNum == 1) {
                g2d.setColor(Color.BLACK);
                g2d.fillRect(x, y, tileSize, tileSize);
            }
            col++;
            x += tileSize;
            if (col == maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += tileSize;
            }

        }

          /*  for(row=0;row<maxScreenRow;row++){
                for(col=0;col<maxScreenCol;col++){
                if(getMapTile(col,row) ==0) {
                    g2d.setColor(Color.GREEN);
                    g2d.fillRect(x, y, tileSize, tileSize);
                }
                if(getMapTile(col,row)==1) {
                    g2d.setColor(Color.BLACK);
                    g2d.fillRect(x, y, tileSize, tileSize);
                }
                x+=tileSize;
                if(col==maxScreenCol-1){
                    y+=tileSize;
                }
            }

        }*/
    }
}
