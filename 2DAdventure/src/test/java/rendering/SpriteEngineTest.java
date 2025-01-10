package rendering;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpriteEngineTest {
@Test
    void testConstructorSpriteEngine() {
    SpriteEngine engine = new SpriteEngine(60);
    assertNotNull(engine);
    assertEquals(60,engine.getFramesPerSecond());
}
}