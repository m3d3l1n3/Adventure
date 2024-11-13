package entity;

import rendering.Drawable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Player implements Entity//, Drawable
{

    private int positionX;
    private int positionY;
    private int speed;
    private int health;
    private BufferedImage image;

    public Player(int x, int y, int speed, int health, BufferedImage image) {
        setPositionX(x);
        setPositionY(y);
        setHealth(health);
        setSpeed(speed);
        setImage(image);
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int x) {
        this.positionX = x;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int y) {
        this.positionY = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int s) {
        this.speed = s;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int h) {
        this.health = h;
    }

    @Override
    public BufferedImage getImage() {
        try {
            //image = ImageIO.read(getClass().getResourceAsStream(""))
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
