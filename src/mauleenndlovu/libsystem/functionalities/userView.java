package mauleenndlovu.libsystem.functionalities;

import mauleenndlovu.libsystem.main.SqlConnection;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class userView extends JFrame {
    private JPanel mainPanel;
    private JTable table1;
    private JTextField bookID_TxtField;
    private JTextField bookTitleTxtField;
    private JTextField authorNameTxtField;
    private JTextField bookQuantityTxtField;
    private JButton searchButton;
    private JButton clearButton;
    private JButton returnBookButton;
    private JButton checkOutBookButton;
    private JButton signOutButton;
    private JLabel bookID_Label;
    private JLabel bookTitleLabel;
    private JLabel bookAuthorLabel;
    private JLabel bookQuantityLabel;
    private JButton viewBooksButton;
    private JButton viewUsersButton;
    private JButton deleteUsersButton;
    private JButton viewIssuedBooksButton;


    public userView() {
        setContentPane(mainPanel);
        setTitle("User View");
        setSize(700,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table1.setFillsViewportHeight(true);
        table1.setFocusable(false);
        //Setting background colour of the table
        table1.setBackground(Color.orange);
        //Setting foreground colour of the table
        table1.setForeground(Color.blue);


        checkOutBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                issueBook issueBookWindow = new issueBook();
                setVisible(false);
                issueBookWindow.setVisible(true);
            }
        });

        viewIssuedBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
/*
                viewIssuedBooks viewIssuedBooksWindow = new viewIssuedBooks();
                setVisible(false);
                viewIssuedBooksWindow.setVisible(true);
 */
            }
        });

    }

    public static void main(String[] args) {
        userView myFrame = new userView();
    }
}
