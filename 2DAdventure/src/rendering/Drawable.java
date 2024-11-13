package rendering;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface Drawable {
    public void paintComponent(Graphics g2d);

    public SpriteSheet loadAnimation(int gridX, int gridY, int designNumber);

    void paint(Graphics2D g2d, double progress);
}
