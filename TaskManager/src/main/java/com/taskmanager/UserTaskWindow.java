package com.taskmanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI window for viewing, adding, completing, and deleting tasks for a user.
 */
public class UserTaskWindow {

    private JFrame frame;
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private User user;

    public UserTaskWindow(User user) {
        this.user = user;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Tasks for " + user.getName());
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(taskList);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Button panel with better layout
        JPanel bottomPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        JButton addTaskButton = new JButton("Add Task");
        JButton completeTaskButton = new JButton("Mark Completed");
        JButton deleteTaskButton = new JButton("Delete Task");

        addTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String desc = JOptionPane.showInputDialog(frame, "Task description:");
                if (desc == null || desc.trim().isEmpty()) {
                    return;
                }

                String dueDate = JOptionPane.showInputDialog(frame, "Due date (optional):");
                if (dueDate == null) {
                    dueDate = "";
                }

                String[] priorities = {"LOW", "MEDIUM", "HIGH"};
                String priorityInput = (String) JOptionPane.showInputDialog(
                        frame, "Select priority:", "Priority",
                        JOptionPane.PLAIN_MESSAGE, null, priorities, "LOW"
                );

                Task.Priority priority = Task.Priority.LOW;
                if (priorityInput != null) {
                    switch (priorityInput.toUpperCase()) {
                        case "MEDIUM":
                            priority = Task.Priority.MEDIUM;
                            break;
                        case "HIGH":
                            priority = Task.Priority.HIGH;
                            break;
                        default:
                            priority = Task.Priority.LOW;
                    }
                }

                user.addTask(desc.trim(), dueDate.trim(), priority);
                refreshTaskList();
            }
        });

        completeTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex >= 0) {
                    if (user.markTaskCompleted(selectedIndex)) {
                        refreshTaskList();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid task selection.");
                    }
                }
            }
        });

        deleteTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex >= 0) {
                    if (user.deleteTask(selectedIndex)) {
                        refreshTaskList();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid task selection.");
                    }
                }
            }
        });

        bottomPanel.add(addTaskButton);
        bottomPanel.add(completeTaskButton);
        bottomPanel.add(deleteTaskButton);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        refreshTaskList();
        frame.setVisible(true);
    }

    private void refreshTaskList() {
        taskListModel.clear();
        TaskNode current = getTaskListHead();

        int index = 0;
        while (current != null) {
            String displayText = index + ". " + current.task;
            if (current.task.getPriority() == Task.Priority.HIGH) {
                displayText = "<html><font color='red'>" + displayText + "</font></html>";
            } else if (current.task.getPriority() == Task.Priority.MEDIUM) {
                displayText = "<html><font color='orange'>" + displayText + "</font></html>";
            }
            taskListModel.addElement(displayText);
            current = current.next;
            index++;
        }
    }

    private TaskNode getTaskListHead() {
        return user.getTaskListHead();
    }
}
