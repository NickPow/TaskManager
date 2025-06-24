package com.taskmanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Basic Swing GUI for Task Manager - user management window.
 */
public class TaskManagerGUI {

    private JFrame frame;
    private DefaultListModel<String> userListModel;
    private JList<String> userList;
    private ToDoListManager manager;

    public TaskManagerGUI(ToDoListManager manager) {
        this.manager = manager;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Task Manager");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // User list display
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        JScrollPane scrollPane = new JScrollPane(userList);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel with Add User button
        JPanel bottomPanel = new JPanel();
        JButton addUserButton = new JButton("Add User");

        addUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userName = JOptionPane.showInputDialog(frame, "Enter user's name:");

                if (userName != null && !userName.trim().isEmpty()) {
                    if (manager.addUser(userName.trim())) {
                        userListModel.addElement(userName.trim());
                    } else {
                        JOptionPane.showMessageDialog(frame, "User already exists or invalid name.");
                    }
                }
            }
        });

        bottomPanel.add(addUserButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Open task window when user is clicked
        userList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedUser = userList.getSelectedValue();
                if (selectedUser != null) {
                    User user = manager.findUser(selectedUser);
                    if (user != null) {
                        new UserTaskWindow(user);
                    }
                }
            }
        });

        frame.setVisible(true);
    }
}
