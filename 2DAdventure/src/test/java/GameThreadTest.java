import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameThreadTest {
@Test
    void testConstructorGameThread() {
    GameThread gameThread = new GameThread();
    assertNotNull(gameThread);
}
}