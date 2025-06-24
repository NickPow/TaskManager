package com.taskmanager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for TaskList class.
 */
public class TaskListTest {

    @Test
    public void testAddAndRetrieveTasks() {
        TaskList list = new TaskList();
        list.addTask("Task 1", "2025-07-01", Task.Priority.HIGH);
        list.addTask("Task 2", "", Task.Priority.LOW);

        list.printTasks();  // Visual confirmation
    }

    @Test
    public void testMarkTaskCompleted() {
        TaskList list = new TaskList();
        list.addTask("Task 1", "", Task.Priority.LOW);

        assertTrue(list.markTaskCompleted(0));
        assertFalse(list.markTaskCompleted(5));
    }

    @Test
    public void testDeleteTask() {
        TaskList list = new TaskList();
        list.addTask("Task 1", "", Task.Priority.LOW);
        list.addTask("Task 2", "", Task.Priority.MEDIUM);

        assertTrue(list.deleteTask(1));
        assertTrue(list.deleteTask(0));
        assertFalse(list.deleteTask(0));  // Nothing left to delete
    }
}
