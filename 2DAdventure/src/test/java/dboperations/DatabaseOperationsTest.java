package dboperations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseOperationsTest {
@Test
    void testConstructorDatabaseOperations() {
    DatabaseOperations databaseOperations = new DatabaseOperations();
    assertNotNull(databaseOperations);
    assertNotNull(databaseOperations.conn);
}
@Test
    void testGetDatabaseOperations() {
    DatabaseOperations databaseOperations = new DatabaseOperations();
    assertTrue(databaseOperations.checkTheDatabase("Player1"));
    assertFalse(databaseOperations.validateAdminKey("4321"));

}
}