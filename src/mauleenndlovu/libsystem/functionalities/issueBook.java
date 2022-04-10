package mauleenndlovu.libsystem.functionalities;

import mauleenndlovu.libsystem.DAO.LibraryDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class issueBook extends JFrame {
    private JTextField bookCallNO_TxtField;
    private JTextField studentID_TxtField;
    private JTextField studentUsername_TxtField;
    private JLabel bookCallNO_Label;
    private JLabel studentID_Label;
    private JLabel studentUsernameLabel;
    private JButton issueBookButton;
    private JPanel mainPanel;

    public issueBook() {
        setContentPane(mainPanel);
        setTitle("Issue Book");
        setSize(700,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        issueBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String bookcallno = bookCallNO_TxtField.getText();
                int userID = Integer.parseInt(studentID_TxtField.getText());
                String username = studentUsername_TxtField.getText();

                if (LibraryDAO.checkBook(bookcallno)) {

                    int x = LibraryDAO.saveIssuedBook(bookcallno, userID, username);
                    if (x > 0) {
                        JOptionPane.showMessageDialog(issueBook.this, "Book issued sucessfully");
                    }
                    else {
                        JOptionPane.showMessageDialog(issueBook.this, "Sorry, unable to issue");
                    }
                }
                    else {
                        JOptionPane.showMessageDialog(issueBook.this, "Sorry, this call no. does not exist.");
                    }
            }
        });


    }

    public static void main(String[] args) {
        userView myFrame = new userView();
    }
}
