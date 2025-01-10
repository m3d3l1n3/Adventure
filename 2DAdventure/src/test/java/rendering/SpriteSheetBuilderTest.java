package rendering;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
class SpriteSheetBuilderTest {
  @Test
    void testConstructorSpriteSheetBuilder(){
      SpriteSheetBuilder builder = new SpriteSheetBuilder();
      assertNotNull(builder);
      builder = builder.withColumns(20);
      assertEquals(20,builder.getCols());
    builder = builder.withRows(40);
    assertEquals(40,builder.getRows());
    String pathName = "src/main/java/sprites/player/Char_002.png";
    BufferedImage sheet = null;
    try {
      sheet = ImageIO.read(new File(pathName));

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    builder = builder.withSheet(sheet);
    assertEquals(sheet,builder.getSpriteSheet());

  }
}