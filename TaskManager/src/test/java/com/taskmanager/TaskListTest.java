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
        list.addTask("Task 1");
        list.addTask("Task 2");

        list.printTasks();  
    }

    @Test
    public void testMarkTaskCompleted() {
        TaskList list = new TaskList();
        list.addTask("Task 1");

        assertTrue(list.markTaskCompleted(0));
        assertFalse(list.markTaskCompleted(5));
    }
}
