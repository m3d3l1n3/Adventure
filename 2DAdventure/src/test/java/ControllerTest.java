import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
@Test
    void testConstructorController() {
    Panel panel = new Panel("map1",0,0,"Player1");
    Controller controller = new Controller(panel);
    assertNotNull(controller);
    assertNotNull(controller.panel);
    assertNotNull(controller.gameThread);
}
}