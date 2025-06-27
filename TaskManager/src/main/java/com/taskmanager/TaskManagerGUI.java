package com.taskmanager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI for Task Manager
 */
public class TaskManagerGUI {

    private JFrame frame;
    private DefaultListModel<String> userListModel;
    private JList<String> userList;
    private ToDoListManager manager;
    private boolean darkMode = false;

    public TaskManagerGUI(ToDoListManager manager) {
        this.manager = manager;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Task Manager - Users");
        frame.setSize(600, 450);
        frame.setLayout(new BorderLayout(15, 15));
        frame.getContentPane().setBackground(new Color(240, 240, 240));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JLabel title = new JLabel("Task Manager", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setBorder(new EmptyBorder(10, 0, 10, 0));
        frame.add(title, BorderLayout.NORTH);

        // User list panel with border
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        userList.setSelectionBackground(new Color(173, 216, 230));
        JScrollPane scrollPane = new JScrollPane(userList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Registered Users"));
        frame.add(scrollPane, BorderLayout.CENTER);

        // Button panel with padding and spacing
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 15, 15));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton addUserButton = new JButton("Add User");
        JButton deleteUserButton = new JButton("Delete User");
        JButton manageTasksButton = new JButton("Manage Tasks");
        JButton toggleDarkModeButton = new JButton("Toggle Dark Mode");

        addUserButton.setBackground(new Color(200, 255, 200));
        deleteUserButton.setBackground(new Color(255, 200, 200));
        manageTasksButton.setBackground(new Color(200, 200, 255));
        toggleDarkModeButton.setBackground(new Color(220, 220, 220));

        buttonPanel.add(addUserButton);
        buttonPanel.add(deleteUserButton);
        buttonPanel.add(manageTasksButton);
        buttonPanel.add(toggleDarkModeButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Button functionality
        addUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(frame, "Enter new user's name:");
                if (name != null && !name.trim().isEmpty()) {
                    if (manager.addUser(name.trim())) {
                        refreshUserList();
                    } else {
                        JOptionPane.showMessageDialog(frame, "User already exists or invalid name.");
                    }
                }
            }
        });

        deleteUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedUser = userList.getSelectedValue();
                if (selectedUser != null) {
                    int confirm = JOptionPane.showConfirmDialog(frame, "Delete user " + selectedUser + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        manager.deleteUser(selectedUser);
                        refreshUserList();
                    }
                }
            }
        });

        manageTasksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedUser = userList.getSelectedValue();
                if (selectedUser != null) {
                    User user = manager.findUser(selectedUser);
                    if (user != null) {
                        new UserTaskWindow(user, darkMode);
                    }
                }
            }
        });

        toggleDarkModeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                darkMode = !darkMode;
                applyDarkMode();
            }
        });

        // Exit confirmation on close
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(frame, "Exit the Task Manager?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    manager.saveToFile("data.json");
                    System.exit(0);
                }
            }
        });

        refreshUserList();
        frame.setVisible(true);
    }

    private void refreshUserList() {
        userListModel.clear();
        for (int i = 0; i < manager.getUserCount(); i++) {
            userListModel.addElement(manager.getUserAt(i).getName());
        }
    }

    private void applyDarkMode() {
        Color bg = darkMode ? Color.DARK_GRAY : new Color(240, 240, 240);
        Color fg = darkMode ? Color.LIGHT_GRAY : Color.BLACK;

        frame.getContentPane().setBackground(bg);
        userList.setBackground(darkMode ? Color.BLACK : Color.WHITE);
        userList.setForeground(fg);
    }
}
