import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeyHandlerTest {
@Test
    void testConstructorKeyHandler() {
    KeyHandler keyHandler = new KeyHandler();
    assertNotNull(keyHandler);
}
@Test void testFunctionalityKeyHandler(){
    KeyHandler keyHandler = new KeyHandler();
    assertNotNull(keyHandler.getKeyState());
}
}