package tiles;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {
@Test
    void testConstructorTile() {
    Tile tile = new Tile();
    assertNotNull(tile);
}
}