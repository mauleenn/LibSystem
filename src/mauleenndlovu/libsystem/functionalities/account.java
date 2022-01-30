package mauleenndlovu.libsystem.functionalities;

import mauleenndlovu.libsystem.DAO.LibraryDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class account extends JFrame {
    private JPasswordField passwordTxtField;
    private JTextField usernameTxtField;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JButton signUpButton;
    private JPanel mainPanel;
    private JCheckBox librarianCheckBox;
    private JButton loginButton;
    private JLabel welcomeSignLabel;
    private JLabel guideLabel;

    public account() {
        setContentPane(mainPanel);
        setTitle("Library Management System");
        setSize(600,550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        passwordTxtField.setEchoChar('*');
        passwordTxtField.setColumns(10);

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTxtField.getText();
                String password = String.copyValueOf(passwordTxtField.getPassword());

                if (username.contentEquals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter a username!");
                }
                else if (password.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter a password!");
                }
                else if (LibraryDAO.checkUsername(username)) {
                    JOptionPane.showMessageDialog(null, "This username already exists.");
                }
                // User has successfully created a new account
                else if (username != "" && password != "") {
                    JOptionPane.showMessageDialog(null, "You have successfully created a new account.");

                    // Once user clicks the sign-up button, the username and password text fields are automatically erased.
                    usernameTxtField.setText(null);
                    passwordTxtField.setText(null);
                }

                else {
                    try {

                        // Functionality is included in the LibraryDAO class
                        int x = LibraryDAO.createAccount(username,password);
                        int y = LibraryDAO.createAccount(username, password);

                        if (x > 0 && librarianCheckBox.isSelected()) {
                            JOptionPane.showMessageDialog(null,"New user added!");
                            librarianView window = new librarianView();
                           // window.UIFrame.setVisible(true);

                        }

                        // Opens up the librarian view for the new added user.
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login window = new login();
            }
        });


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String username = usernameTxtField.getText();
                    String password = passwordTxtField.getText();


                    boolean x = LibraryDAO.login(username, password);
                    if (x == true && librarianCheckBox.isSelected()) {
                        JOptionPane.showMessageDialog(null, "Username and password are correct!");
                        librarianView window = new librarianView();
                        setVisible(true);

                    }
                    else if (x == true && !librarianCheckBox.isSelected()) {
                        JOptionPane.showMessageDialog(null, "Username and password are correct!");
                        userView window = new userView();
                        setVisible(true);

                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Username and password are not correct! Please try again.");
                    }

                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        });


    }

    public static void main(String[] args) {
        account myFrame = new account();
    }
}
