package com.taskmanager;

/**
 * Represents a user with a unique name and their to-do list.
 */
public class User {
    private String name;
    private TaskList taskList;

    public User(String name) {
        this.name = name;
        this.taskList = new TaskList();
    }

    public String getName() {
        return name;
    }

    public void addTask(String description, String dueDate, Task.Priority priority) {
        taskList.addTask(description, dueDate, priority);
    }

    public boolean markTaskCompleted(int position) {
        return taskList.markTaskCompleted(position);
    }

    public boolean deleteTask(int position) {
        return taskList.deleteTask(position);
    }

    public void printTasks() {
        System.out.println("Tasks for " + name + ":");
        taskList.printTasks();
    }

    /**
     * Prints tasks sorted by priority.
     */
    public void printTasksByPriority() {
        System.out.println("Tasks for " + name + " (sorted by priority):");
        taskList.printTasksByPriority();
    }
}
