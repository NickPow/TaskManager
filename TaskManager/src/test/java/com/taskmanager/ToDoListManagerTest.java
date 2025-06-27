package com.taskmanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for core ToDoListManager functionality.
 */
public class ToDoListManagerTest {

    private ToDoListManager manager;

    @BeforeEach
    public void setUp() {
        manager = new ToDoListManager();
    }

    @Test
    public void testAddUser() {
        assertTrue(manager.addUser("Alice"));
        assertFalse(manager.addUser("Alice")); // Duplicate
    }

    @Test
    public void testFindUser() {
        manager.addUser("Bob");
        assertNotNull(manager.findUser("Bob"));
        assertNull(manager.findUser("Charlie"));
    }

    @Test
    public void testDeleteUser() {
        manager.addUser("Dave");
        assertTrue(manager.deleteUser("Dave"));
        assertFalse(manager.deleteUser("Dave")); // Already deleted
    }

    @Test
    public void testTaskAdditionAndCompletion() {
        manager.addUser("Eve");
        User user = manager.findUser("Eve");

        user.addTask("Finish Project", "2025-07-01", Task.Priority.HIGH);
        assertEquals(1, user.getTaskListSize());

        assertTrue(user.markTaskCompleted(0));
    }
}
