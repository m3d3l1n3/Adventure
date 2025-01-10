package entity;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
@Test
    void testConstructorPlayer(){
    String pathName = "src/main/java/sprites/player/Char_002.png";
    BufferedImage image = null;
    try {
        image = ImageIO.read(new File(pathName));

    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    Player p1 = new Player(0,0,4,100,image,"Player1");
    assertNotNull(p1);
    assertEquals(0,p1.getPositionX());
    assertEquals(0,p1.getPositionY());
    assertEquals(100,p1.getHealth());
    assertEquals(image,p1.getImage());

}
}