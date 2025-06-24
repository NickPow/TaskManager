package com.taskmanager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Task class.
 */
public class TaskTest {

    @Test
    public void testTaskCreation() {
        Task task = new Task("Test task", "2025-07-01", Task.Priority.MEDIUM);

        assertEquals("Test task", task.getDescription());
        assertEquals("2025-07-01", task.getDueDate());
        assertEquals(Task.Priority.MEDIUM, task.getPriority());
        assertFalse(task.isCompleted());
    }

    @Test
    public void testMarkTaskCompleted() {
        Task task = new Task("Test task", "", Task.Priority.LOW);
        task.markCompleted();
        assertTrue(task.isCompleted());
    }
}
