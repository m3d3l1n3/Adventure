import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PanelTest {
@Test
    void testConstructorPanel() {
    Panel  panel = new Panel("map1",0,0,"Player1");
    assertEquals("map1",panel.getMapName());
    assertEquals(0,panel.getX());
    assertEquals(0,panel.getY());
}
@Test
    void testFunctionalityPanel() {
    Panel  panel = new Panel("map1",0,0,"Player1");
    assertTrue(panel.isTileWalkable(0, 0));
}
}