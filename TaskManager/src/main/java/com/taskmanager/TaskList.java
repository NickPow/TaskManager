package com.taskmanager;

/**
 * Single linked list to store tasks for a user.
 */
public class TaskList {
    private TaskNode head;

    /**
     * Adds a new task to the end of the list.
     */
    public void addTask(String description) {
        Task newTask = new Task(description);
        TaskNode newNode = new TaskNode(newTask);

        if (head == null) {
            head = newNode;
        } else {
            TaskNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    /**
     * Marks task at given position as completed.
     */
    public boolean markTaskCompleted(int position) {
        TaskNode current = head;
        int index = 0;

        while (current != null) {
            if (index == position) {
                current.task.markCompleted();
                return true;
            }
            current = current.next;
            index++;
        }
        return false; 
    }

    /**
     * Prints all tasks with their completion status.
     */
    public void printTasks() {
        TaskNode current = head;
        int index = 0;

        if (current == null) {
            System.out.println("No tasks found.");
            return;
        }

        while (current != null) {
            System.out.println(index + ". " + current.task);
            current = current.next;
            index++;
        }
    }
}
