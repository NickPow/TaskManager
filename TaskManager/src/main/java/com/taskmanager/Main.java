package com.taskmanager;

import java.util.Scanner;

/**
 * Main class providing CLI and GUI options for Task Manager application.
 */
public class Main {
    public static void main(String[] args) {
        ToDoListManager manager = new ToDoListManager();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Select mode: 1 for CLI, 2 for GUI");
        String mode = scanner.nextLine().trim();

        if (mode.equals("1")) {
            runCLI(manager, scanner);
        } else {
            new TaskManagerGUI(manager);
        }
    }

    /**
     * Runs the CLI interface.
     */
    private static void runCLI(ToDoListManager manager, Scanner scanner) {
        boolean exit = false;

        System.out.println("=== To-Do List Manager (CLI Mode) ===");

        while (!exit) {
            System.out.println("\nOptions:");
            System.out.println("1. Add User");
            System.out.println("2. Add Task to User");
            System.out.println("3. Mark Task as Completed");
            System.out.println("4. View User's Tasks");
            System.out.println("5. View All Users");
            System.out.println("6. Exit");
            System.out.println("7. Delete Task");
            System.out.println("8. View User's Tasks by Priority");
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
                            System.out.print("Enter due date (or leave blank): ");
                            String dueDate = scanner.nextLine().trim();

                            System.out.print("Select priority (Low, Medium, High): ");
                            String priorityInput = scanner.nextLine().trim().toUpperCase();

                            Task.Priority priority;
                            switch (priorityInput) {
                                case "LOW":
                                    priority = Task.Priority.LOW;
                                    break;
                                case "MEDIUM":
                                    priority = Task.Priority.MEDIUM;
                                    break;
                                case "HIGH":
                                    priority = Task.Priority.HIGH;
                                    break;
                                default:
                                    System.out.println("Invalid priority. Defaulting to LOW.");
                                    priority = Task.Priority.LOW;
                            }

                            user.addTask(taskDesc, dueDate, priority);
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

                case "7":
                    System.out.print("Enter user's name: ");
                    name = scanner.nextLine().trim();
                    user = manager.findUser(name);

                    if (user != null) {
                        user.printTasks();
                        System.out.print("Enter task number to delete: ");
                        String deleteInput = scanner.nextLine().trim();
                        int delPosition;

                        try {
                            delPosition = Integer.parseInt(deleteInput);

                            if (user.deleteTask(delPosition)) {
                                System.out.println("Task deleted.");
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

                case "8":
                    System.out.print("Enter user's name: ");
                    name = scanner.nextLine().trim();
                    user = manager.findUser(name);

                    if (user != null) {
                        user.printTasksByPriority();
                    } else {
                        System.out.println("User not found.");
                    }
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}
