package saving;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProgressTest {
@Test
    void testConstructorProgress() {
    Progress progress = new Progress();
    assertNotNull(progress);
}
@Test
    void testConnection(){
    Progress progress = new Progress();
    assertNotNull(progress.connect());
}
}