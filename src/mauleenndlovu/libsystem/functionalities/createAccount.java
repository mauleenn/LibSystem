package mauleenndlovu.libsystem.functionalities;

import mauleenndlovu.libsystem.DAO.LibraryDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class createAccount extends JFrame {
    private JPasswordField passwordTxtField;
    private JTextField usernameTxtField;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JButton submitButton;
    private JPanel mainPanel;
    private JCheckBox librarianCheckBox;

    public createAccount() {
        setContentPane(mainPanel);
        setTitle("Library Management System");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        passwordTxtField.setEchoChar('*');
        passwordTxtField.setColumns(10);

        submitButton.addActionListener(new ActionListener() {
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

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login window = new login();
            }
        });


    }

    public static void main(String[] args) {
        createAccount myFrame = new createAccount();
    }
}
