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

                try {
                    String username = usernameTxtField.getText();
                    String password = String.copyValueOf(passwordTxtField.getPassword());

                    int x = mauleenndlovu.libsystem.DAO.LibraryDAO.saveLibrarian(username, password);

                    if (x > 0 && librarianCheckBox.isSelected()) {
                        JOptionPane.showMessageDialog(account.this, "Hello librarian! You have successfully created a new account.");
                        librarianView libWindow = new librarianView();
                        setVisible(false);
                        libWindow.setVisible(true);
                    }
                    else if (x > 0 && !librarianCheckBox.isSelected()) {
                        JOptionPane.showMessageDialog(account.this, "Hello user! You have successfully created a new account.");
                        userView userWindow = new userView();
                        setVisible(false);
                        userWindow.setVisible(true);
                    }
                    else if (password.equals("")) {
                        JOptionPane.showMessageDialog(account.this, "Please enter a password!");
                    }
                    else if (LibraryDAO.checkUsername(username)) {
                        JOptionPane.showMessageDialog(account.this, "This username already exists.");
                    }
                    else if (username.contentEquals("")) {
                        JOptionPane.showMessageDialog(account.this, "Please enter a username!");
                    }
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(account.this, ex);
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = usernameTxtField.getText();
                String password = String.copyValueOf(passwordTxtField.getPassword());

                if (username.contentEquals("")) {
                    JOptionPane.showMessageDialog(account.this, "Please enter a username!");
                }
                else if (password.equals("")) {
                    JOptionPane.showMessageDialog(account.this, "Please enter a password!");
                }

                try {
                    boolean x = LibraryDAO.validateLibrarian(username, password);
                    if (LibraryDAO.validateLibrarian(username, password) && librarianCheckBox.isSelected()) {
                        JOptionPane.showMessageDialog(account.this, "Username and password are correct!");
                        librarianView window = new librarianView();
                        setVisible(false);
                        window.setVisible(true);

                    }
                    else if (x == true && !librarianCheckBox.isSelected()) {
                        JOptionPane.showMessageDialog(account.this, "Username and password are correct!");
                        userView window = new userView();
                        //setVisible(false);
                        //window.setVisible(true);
                    }
                    else {
                        JOptionPane.showMessageDialog(account.this, "Username and password are not correct! Please try again.");
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
