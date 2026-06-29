package gui;
import javax.swing.*;

import service.UserService;
import database.DatabaseManager;
import model.CurrentUser;
import model.Role;
import model.User;


import java.awt.*;
import java.awt.event.*;

public class LoginGUI {

    private UserService userService;

    public LoginGUI() {

        userService = new UserService();

        JFrame frame = new JFrame("Excel-Prüfungssystem");
        frame.setSize(600, 400);
        frame.getContentPane().setBackground(Style.BACKGROUND);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //LOGO

        JLabel logoLabel = Style.createLogo();

        JPanel headerPanel = new JPanel(new BorderLayout());

        headerPanel.setBackground(Style.BACKGROUND);

        headerPanel.add(
                logoLabel,
                BorderLayout.EAST);

        //Titel

        JLabel titleLabel =
        new JLabel(
                "Excel-Prüfungssystem",
                SwingConstants.CENTER);

        titleLabel.setFont(Style.TITLE_FONT);

        titleLabel.setForeground(Style.PRIMARY);

        //Formularpanel

        JPanel formPanel = new JPanel();

        formPanel.setLayout(
                new BoxLayout(
                        formPanel,
                        BoxLayout.Y_AXIS));

        formPanel.setBackground(Style.BACKGROUND);


        //Labels und Fields

        JLabel userLabel =new JLabel("Benutzername:");
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField userField = new JTextField();
        userField.setAlignmentX(Component.CENTER_ALIGNMENT);
        userField.setPreferredSize(new Dimension(250, 35));
        userField.setMaximumSize(new Dimension(250, 35));
        

        JLabel passwordLabel = new JLabel("Passwort:");
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setPreferredSize(new Dimension(250, 35));
        passwordField.setMaximumSize(new Dimension(250, 35));

        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setPreferredSize(new Dimension(250, 50));
        Style.styleButton(loginButton);

        JLabel infoLabel = new JLabel("");

        formPanel.add(userLabel);
        formPanel.add(Box.createVerticalStrut(5));

        formPanel.add(userField);
        formPanel.add(Box.createVerticalStrut(15));

        formPanel.add(passwordLabel);
        formPanel.add(Box.createVerticalStrut(5));

        formPanel.add(passwordField);
        formPanel.add(Box.createVerticalStrut(20));

        formPanel.add(loginButton);


        JPanel wrapperPanel = new JPanel();

        wrapperPanel.setBackground(Style.BACKGROUND);

        wrapperPanel.add(formPanel);

        JPanel centerPanel =
        new JPanel(
                new BorderLayout());

        centerPanel.add(wrapperPanel,BorderLayout.CENTER);

        centerPanel.add(
        titleLabel,
        BorderLayout.NORTH);

        centerPanel.add(
        formPanel,
        BorderLayout.CENTER);

        centerPanel.add(
        infoLabel,
        BorderLayout.SOUTH);

        frame.add(
        headerPanel,
        BorderLayout.NORTH);

        frame.add(
        centerPanel,
        BorderLayout.CENTER);

        loginButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                String username =
                        userField.getText();

                String password =
                        new String(
                                passwordField.getPassword());

                User user =
                        userService.login(
                                username,
                                password);

                if (user != null) {

                        CurrentUser.setCurrentUser(user);

                        frame.dispose();

                        if (user.getRole() == Role.STUDENT) {

                        new StudentGUI();

                } else if (user.getRole() == Role.DOZENT) {

                        new DozentGUI();

                }

                } else {

                        infoLabel.setText(
                                "Login fehlgeschlagen");
                }         
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        DatabaseManager db = new DatabaseManager();

        db.connect();

        db.createUserTable();
        db.createPruefungTable();
        db.createPruefungsteilnehmerTable();

        db.disconnect();

        new LoginGUI();
;
    }
}