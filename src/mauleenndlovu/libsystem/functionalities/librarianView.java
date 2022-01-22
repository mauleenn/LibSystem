package mauleenndlovu.libsystem.functionalities;

import javax.swing.*;

public class librarianView extends JFrame {
    private JPanel mainPanel;
    private JTextField bookID_TxtField;
    private JTextField bookTitleTxtField;
    private JTextField authorNameTxtField;
    private JTextField bookQuantityTxtField;
    private JLabel authorName;
    private JLabel bookTitle;
    private JLabel bookQuantity;
    private JLabel bookID;
    private JButton searchButton;
    private JButton addButton;
    private JButton removeButton;
    private JButton clearButton;
    private JButton updateButton;
    private JButton viewUsersButton;
    private JTable bookTable;

    public librarianView() {
        setContentPane(mainPanel);
        setTitle("Librarian View");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        librarianView myFrame = new librarianView();
    }
}
