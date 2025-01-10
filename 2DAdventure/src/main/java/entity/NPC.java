package entity;

import java.awt.image.BufferedImage;

public class NPC implements Entity {

    private int positionX;
    private int positionY;
    private String message;
    private int speed;
    private int health;
    private BufferedImage image;

    public NPC(int x, int y, int speed, int health, BufferedImage image) {
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
        return this.image;
    }

    @Override
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
