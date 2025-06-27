package com.taskmanager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *  GUI window for viewing, adding, completing, and deleting tasks for a user.
 */
public class UserTaskWindow {

    private JFrame frame;
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private User user;
    private boolean darkMode;

    public UserTaskWindow(User user, boolean darkMode) {
        this.user = user;
        this.darkMode = darkMode;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Tasks for " + user.getName());
        frame.setSize(600, 450);
        frame.setLayout(new BorderLayout(15, 15));
        frame.getContentPane().setBackground(new Color(240, 240, 240));

        JLabel title = new JLabel("Tasks for " + user.getName(), SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setBorder(new EmptyBorder(10, 0, 10, 0));
        frame.add(title, BorderLayout.NORTH);

        // Task list panel
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        taskList.setSelectionBackground(new Color(173, 216, 230));
        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Task List"));
        frame.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 15, 15));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton addTaskButton = new JButton("Add Task");
        JButton completeTaskButton = new JButton("Mark Completed");
        JButton deleteTaskButton = new JButton("Delete Task");
        JButton backButton = new JButton("Back");

        addTaskButton.setBackground(new Color(200, 255, 200));
        completeTaskButton.setBackground(new Color(200, 200, 255));
        deleteTaskButton.setBackground(new Color(255, 200, 200));
        backButton.setBackground(new Color(220, 220, 220));

        buttonPanel.add(addTaskButton);
        buttonPanel.add(completeTaskButton);
        buttonPanel.add(deleteTaskButton);
        buttonPanel.add(backButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Button functionality
        addTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String desc = JOptionPane.showInputDialog(frame, "Task description:");
                if (desc == null || desc.trim().isEmpty()) return;

                String dueDate = JOptionPane.showInputDialog(frame, "Due date (optional):");
                if (dueDate == null) dueDate = "";

                String[] priorities = {"LOW", "MEDIUM", "HIGH"};
                String priorityInput = (String) JOptionPane.showInputDialog(frame, "Select priority:", "Priority",
                        JOptionPane.PLAIN_MESSAGE, null, priorities, "LOW");

                Task.Priority priority = Task.Priority.LOW;
                if (priorityInput != null) {
                    switch (priorityInput.toUpperCase()) {
                        case "MEDIUM": priority = Task.Priority.MEDIUM; break;
                        case "HIGH": priority = Task.Priority.HIGH; break;
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
                    }
                }
            }
        });

        deleteTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex >= 0) {
                    int confirm = JOptionPane.showConfirmDialog(frame, "Delete this task?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        if (user.deleteTask(selectedIndex)) {
                            refreshTaskList();
                        }
                    }
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        refreshTaskList();
        applyDarkMode();
        frame.setVisible(true);
    }

    private void refreshTaskList() {
        taskListModel.clear();
        TaskNode current = user.getTaskListHead();
        int index = 0;
        while (current != null) {
            String statusIcon = current.task.isCompleted() ? "[✓]" : "[ ]";
            String text = index + ". " + statusIcon + " " + current.task.getDescription()
                    + " | Due: " + (current.task.getDueDate().isEmpty() ? "N/A" : current.task.getDueDate())
                    + " | Priority: " + current.task.getPriority();

            String displayText = text;
            if (current.task.getPriority() == Task.Priority.HIGH) {
                displayText = "<html><font color='red'>" + text + "</font></html>";
            } else if (current.task.getPriority() == Task.Priority.MEDIUM) {
                displayText = "<html><font color='orange'>" + text + "</font></html>";
            }

            taskListModel.addElement(displayText);
            current = current.next;
            index++;
        }
    }

    private void applyDarkMode() {
        if (darkMode) {
            frame.getContentPane().setBackground(Color.DARK_GRAY);
            taskList.setBackground(Color.BLACK);
            taskList.setForeground(Color.LIGHT_GRAY);
        }
    }
}
