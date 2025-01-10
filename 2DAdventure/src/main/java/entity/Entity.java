package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface Entity {
    public void setPositionX(int x);

    public void setPositionY(int y);

    public void setHealth(int h);

    public void setSpeed(int s);

    public int getPositionX();

    public int getPositionY();

    public int getHealth();

    public int getSpeed();

    public BufferedImage getImage();

    public void setImage(BufferedImage image);
}
