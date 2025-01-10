package tiles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileManagerTest {
@Test
    void testConstructorTileManager() {
    int tileSize = 16; // calssic 2 D adventure games had 16x16 sized characters
    int scale = 3;
    int finalTileSize = tileSize * scale;
    int maxScreenWidth = 16 * finalTileSize; //768 pixels
    int maxScreenHeight = 16 * finalTileSize;
    TileManager tileManager = new TileManager(maxScreenWidth,  maxScreenHeight, "map1");
    assertNotNull(tileManager);
    assertEquals("map1", tileManager.getMapName());

}
}