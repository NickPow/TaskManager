package com.taskmanager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ToDoListManager class.
 */
public class ToDoListManagerTest {

    @Test
    public void testAddUserAndFindUser() {
        ToDoListManager manager = new ToDoListManager();

        assertTrue(manager.addUser("Charlie"));
        assertNotNull(manager.findUser("Charlie"));

        assertFalse(manager.addUser("Charlie"));
        assertNull(manager.findUser("Unknown"));
    }
}
