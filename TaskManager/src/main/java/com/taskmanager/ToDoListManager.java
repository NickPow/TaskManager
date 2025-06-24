package com.taskmanager;

/**
 * Manages an array of users and provides lookup functionality.
 */
public class ToDoListManager {
    private static final int MAX_USERS = 100;
    private User[] users;
    private int userCount;

    public ToDoListManager() {
        users = new User[MAX_USERS];
        userCount = 0;
    }

    /**
     * Adds a new user if space allows and username is unique.
     */
    public boolean addUser(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false; 
        }

        if (userCount >= MAX_USERS || findUser(name) != null) {
            return false;
        }

        users[userCount] = new User(name.trim());
        userCount++;
        return true;
    }

    /**
     * Finds and returns a user by name (case-insensitive).
     */
    public User findUser(String name) {
        if (name == null) {
            return null;
        }

        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().equalsIgnoreCase(name.trim())) {
                return users[i];
            }
        }
        return null;
    }

    /**
     * Prints all registered users.
     */
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
}
