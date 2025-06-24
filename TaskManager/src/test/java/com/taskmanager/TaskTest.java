package com.taskmanager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Task class.
 */
public class TaskTest {

    @Test
    public void testTaskCreation() {
        Task task = new Task("Test task");
        assertEquals("Test task", task.getDescription());
        assertFalse(task.isCompleted());
    }

    @Test
    public void testMarkTaskCompleted() {
        Task task = new Task("Test task");
        task.markCompleted();
        assertTrue(task.isCompleted());
    }
}
