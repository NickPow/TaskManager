package com.taskmanager;

import java.util.Scanner;

/**
 * Main class providing CLI for Task Manager application with improved input handling.
 */
public class Main {
    public static void main(String[] args) {

        ToDoListManager manager = new ToDoListManager();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        System.out.println("=== To-Do List Manager ===");

        while (!exit) {
            System.out.println("\nOptions:");
            System.out.println("1. Add User");
            System.out.println("2. Add Task to User");
            System.out.println("3. Mark Task as Completed");
            System.out.println("4. View User's Tasks");
            System.out.println("5. View All Users");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Enter new user's name: ");
                    String name = scanner.nextLine().trim();

                    if (name.isEmpty()) {
                        System.out.println("Name cannot be empty.");
                    } else if (manager.addUser(name)) {
                        System.out.println("User added successfully.");
                    } else {
                        System.out.println("User already exists or maximum users reached.");
                    }
                    break;

                case "2":
                    System.out.print("Enter user's name: ");
                    name = scanner.nextLine().trim();
                    User user = manager.findUser(name);

                    if (user != null) {
                        System.out.print("Enter task description: ");
                        String taskDesc = scanner.nextLine().trim();

                        if (taskDesc.isEmpty()) {
                            System.out.println("Task description cannot be empty.");
                        } else {
                            user.addTask(taskDesc);
                            System.out.println("Task added.");
                        }
                    } else {
                        System.out.println("User not found.");
                    }
                    break;

                case "3":
                    System.out.print("Enter user's name: ");
                    name = scanner.nextLine().trim();
                    user = manager.findUser(name);

                    if (user != null) {
                        user.printTasks();
                        System.out.print("Enter task number to mark as completed: ");
                        String positionInput = scanner.nextLine().trim();
                        int position;

                        try {
                            position = Integer.parseInt(positionInput);

                            if (user.markTaskCompleted(position)) {
                                System.out.println("Task marked as completed.");
                            } else {
                                System.out.println("Invalid task number.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid number.");
                        }
                    } else {
                        System.out.println("User not found.");
                    }
                    break;

                case "4":
                    System.out.print("Enter user's name: ");
                    name = scanner.nextLine().trim();
                    user = manager.findUser(name);

                    if (user != null) {
                        user.printTasks();
                    } else {
                        System.out.println("User not found.");
                    }
                    break;

                case "5":
                    manager.printAllUsers();
                    break;

                case "6":
                    exit = true;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}
