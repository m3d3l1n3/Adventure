package entity;

import rendering.Drawable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player implements Entity//, Drawable
{

    private int positionX;
    private int positionY;
    private int speed;
    private int health;
    private BufferedImage image;
    private String name;

    private Rectangle collisionBox;
    private boolean collisionOn = true;

    public Player(int x, int y, int speed, int health, BufferedImage image, String name) {
        setPositionX(x);
        setPositionY(y);
        setHealth(health);
        setSpeed(speed);
        setImage(image);
        setName(name);
        this.collisionBox = new Rectangle(8, 16, 32, 32);

    }
public void setName(String name) {
        this.name = name;
}
public String getName() {
        return name;
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
        return this.image;
    }

    @Override
    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
