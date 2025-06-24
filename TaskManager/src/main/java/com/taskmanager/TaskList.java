package com.taskmanager;

/**
 * Single linked list to store tasks for a user.
 */
public class TaskList {
    private TaskNode head;

    public void addTask(String description, String dueDate, Task.Priority priority) {
        Task newTask = new Task(description, dueDate, priority);
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

    public boolean deleteTask(int position) {
        if (head == null || position < 0) {
            return false;
        }

        if (position == 0) {
            head = head.next;
            return true;
        }

        TaskNode current = head;
        int index = 0;

        while (current.next != null) {
            if (index == position - 1) {
                current.next = current.next.next;
                return true;
            }
            current = current.next;
            index++;
        }

        return false;
    }

    /**
     * Prints all tasks with status icons.
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

    /**
     * Prints tasks sorted by priority (HIGH -> LOW).
     */
    public void printTasksByPriority() {
        if (head == null) {
            System.out.println("No tasks found.");
            return;
        }

        for (Task.Priority p : new Task.Priority[]{Task.Priority.HIGH, Task.Priority.MEDIUM, Task.Priority.LOW}) {
            TaskNode current = head;
            int index = 0;
            while (current != null) {
                if (current.task.getPriority() == p) {
                    String statusIcon = current.task.isCompleted() ? "[✓]" : "[ ]";
                    System.out.println(index + ". " + statusIcon + " " + current.task.getDescription()
                            + " | Due: " + (current.task.getDueDate().isEmpty() ? "N/A" : current.task.getDueDate())
                            + " | Priority: " + current.task.getPriority());
                }
                current = current.next;
                index++;
            }
        }
    }
}
