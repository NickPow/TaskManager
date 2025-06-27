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
        frame.setLayout(new BorderLayout());

        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        JScrollPane scrollPane = new JScrollPane(userList);
        frame.add(scrollPane, BorderLayout.CENTER);

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

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                manager.saveToFile("data.json");
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }
}
