package rendering;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SpriteSheetTest {
@Test
    void testConstructorSpriteSheet(){
    String pathName = "src/main/java/sprites/player/Char_002.png";
    BufferedImage sheet = null;
    try {
        sheet = ImageIO.read(new File(pathName));

    } catch (IOException e) {
        throw new RuntimeException(e);
    }
//    SpriteSheet sheetDown =new SpriteSheetBuilder().
//            withSheet(sheet).
//            withColumns(4).
//            withRows(4).
//            withSpriteCount(16).
//            build(0, 0);

//    assertNotNull(spriteSheet);

}
}