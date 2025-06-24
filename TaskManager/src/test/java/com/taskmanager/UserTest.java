package com.taskmanager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for User class.
 */
public class UserTest {

    @Test
    public void testUserCreation() {
        User user = new User("Alice");
        assertEquals("Alice", user.getName());
    }

    @Test
    public void testAddAndCompleteTask() {
        User user = new User("Bob");
        user.addTask("Test Task");

        assertTrue(user.markTaskCompleted(0));
        assertFalse(user.markTaskCompleted(2));
    }
}
