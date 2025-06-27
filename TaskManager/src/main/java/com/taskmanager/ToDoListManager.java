package com.taskmanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Arrays;

/**
 * Manages an array of users and provides save/load functionality with JSON.
 */
public class ToDoListManager {

    private static final int MAX_USERS = 100;
    private User[] users;
    private int userCount;

    public ToDoListManager() {
        users = new User[MAX_USERS];
        userCount = 0;
    }

    public boolean addUser(String name) {
        if (name == null || name.trim().isEmpty() || userCount >= MAX_USERS || findUser(name) != null) {
            return false;
        }
        users[userCount++] = new User(name.trim());
        return true;
    }

    public User findUser(String name) {
        if (name == null) return null;
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().equalsIgnoreCase(name.trim())) {
                return users[i];
            }
        }
        return null;
    }

    public void printAllUsers() {
        if (userCount == 0) {
            System.out.println("No users found.");
            return;
        }
        System.out.println("Registered users:");
        for (int i = 0; i < userCount; i++) {
            System.out.println("- " + users[i].getName());
        }
    }

    /**
     * Saves all users and tasks to a JSON file.
     */
    public void saveToFile(String filename) {
        try (Writer writer = new FileWriter(filename)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(Arrays.copyOf(users, userCount), writer);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    /**
     * Loads users and tasks from a JSON file.
     */
    public void loadFromFile(String filename) {
        try (Reader reader = new FileReader(filename)) {
            Gson gson = new Gson();
            User[] loadedUsers = gson.fromJson(reader, User[].class);

            if (loadedUsers != null) {
                for (User u : loadedUsers) {
                    if (u != null && addUser(u.getName())) {
                        User existing = findUser(u.getName());
                        copyTasks(existing, u);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("No existing data found, starting fresh.");
        }
    }

    private void copyTasks(User target, User source) {
        TaskNode current = source.getTaskListHead();
        while (current != null) {
            target.addTask(current.task.getDescription(),
                    current.task.getDueDate(),
                    current.task.getPriority());
            if (current.task.isCompleted()) {
                target.markTaskCompleted(target.getTaskListSize() - 1);
            }
            current = current.next;
        }
    }
}
