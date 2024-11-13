package tiles;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;
    public boolean collision = false;

    public void setImage(BufferedImage image) {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        GraphicsConfiguration config = device.getDefaultConfiguration();
        BufferedImage buffy = config.createCompatibleImage(image.getWidth(), image.getHeight(), Transparency.OPAQUE);
        buffy.getGraphics().drawImage(image, 0, 0, null);
        this.image = buffy;
    }

    public BufferedImage getImage() {
        return this.image;
    }
}
