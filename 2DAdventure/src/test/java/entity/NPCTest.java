package entity;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class NPCTest {
@Test
    void testConstructorNPC() {
    String pathName = "src/main/java/sprites/player/Char_002.png";
    BufferedImage image = null;
    try {
        image = ImageIO.read(new File(pathName));

    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    NPC n1 = new NPC(0,0,4,100,image);
    assertNotNull(n1);
    assertEquals(0,n1.getPositionX());
    assertEquals(0,n1.getPositionY());
    assertEquals(100,n1.getHealth());
     assertEquals(image,n1.getImage());

}
}