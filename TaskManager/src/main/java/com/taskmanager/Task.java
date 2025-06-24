package com.taskmanager;

/**
 * Represents a single task with description, completion status, due date, and priority.
 */
public class Task {

    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    private String description;
    private boolean completed;
    private String dueDate;
    private Priority priority;

    public Task(String description, String dueDate, Priority priority) {
        this.description = description;
        this.completed = false;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    public void markCompleted() {
        this.completed = true;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    /**
     * Returns task details with status icon.
     */
    @Override
    public String toString() {
        String statusIcon = completed ? "[✓]" : "[ ]";
        return statusIcon + " " + description
                + " | Due: " + (dueDate.isEmpty() ? "N/A" : dueDate)
                + " | Priority: " + priority;
    }
}
