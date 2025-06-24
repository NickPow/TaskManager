package com.taskmanager;

/**
 * Node class for the linked list, holds a Task and reference to next node.
 */
public class TaskNode {
    Task task;
    TaskNode next;

    public TaskNode(Task task) {
        this.task = task;
        this.next = null;
    }
}
