package mauleenndlovu.libsystem.functionalities;

import javax.swing.*;

public class userView extends JFrame {
    private JPanel mainPanel;
    private JTable table1;
    private JTextField bookID_TxtField;
    private JTextField bookTitleTxtField;
    private JTextField authorNameTxtField;
    private JTextField bookQuantityTxtField;
    private JButton searchButton;
    private JButton clearButton;
    private JButton returnButton;
    private JButton checkOutBookButton;
    private JButton signOutButton;
    private JLabel bookID_Label;
    private JLabel bookTitleLabel;
    private JLabel bookAuthorLabel;
    private JLabel bookQuantityLabel;


    public userView() {
        setContentPane(mainPanel);
        setTitle("User View");
        setSize(700,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        userView myFrame = new userView();
    }
}
