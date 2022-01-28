package mauleenndlovu.libsystem.functionalities;

import mauleenndlovu.libsystem.main.SqlConnection;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    private JButton viewBooksButton;
    private JTable bookTable;

    Connection connection = null;

    public librarianView() {

        connection = SqlConnection.dbConnect();

        setContentPane(mainPanel);
        setTitle("Librarian View");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);



        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove();
            }
        });


        viewBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });
    }


    public void search() {

        try {
            bookTable.setModel(new DefaultTableModel( new Object[][] {},
                    new String[] { "Book ID", "Book Name", "Book Author", "Quantity"}));

            bookTitleTxtField.setColumns(10);
            authorNameTxtField.setColumns(10);

            String bookName = bookTitleTxtField.getText();
            String bookAuthor = authorNameTxtField.getText();

            // Actual SQL SELECT statement
            String sql = "SELECT * FROM Books WHERE (book_name LIKE '%" + bookName + "%') OR (book_author LIKE '%"
                    + bookAuthor + "%');";

            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            bookTable.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

        // Auto fill in the txt boxex
        bookTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                DefaultTableModel model = (DefaultTableModel) bookTable.getModel();
                int selectedRow = bookTable.getSelectedRow();

                if (selectedRow > -1) {
                    bookID_TxtField.setText(model.getValueAt(selectedRow, 0).toString());
                    bookTitleTxtField.setText(model.getValueAt(selectedRow, 1).toString());
                    authorNameTxtField.setText(model.getValueAt(selectedRow, 2).toString());
                    bookQuantityTxtField.setText(model.getValueAt(selectedRow, 3).toString());
                }
            }
        });
    }

    private void printUsers() {

        try {
            String sql = "select user_ID, Username from Users";
            PreparedStatement pst = connection.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();
            bookTable.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void add() {

        try {
            String bookName = bookTitleTxtField.getText();
            String bookAuthor = authorNameTxtField.getText();
            String quantity = bookQuantityTxtField.getText();
            int bookQuantity = Integer.parseInt(quantity);

            int x = mauleenndlovu.libsystem.DAO.LibraryDAO.addBook(bookName, bookAuthor, bookQuantity);
            if (x > 0) {
                JOptionPane.showMessageDialog(null, "Book has been successfully added!");
            } else {
                JOptionPane.showMessageDialog(null, "An error has occurred, book has not been added.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        updateTable();
    }

    public void remove() {

        String ID = bookID_TxtField.getText();
        int bookID = Integer.parseInt(ID);

        int row = bookTable.getSelectedRow();
        if (row != -1) {
            int modelRow = bookTable.convertRowIndexToModel(row);
            DefaultTableModel model = (DefaultTableModel) bookTable.getModel();
            String selected = model.getValueAt(row, 0).toString();
            model.removeRow(modelRow);

            int x = mauleenndlovu.libsystem.DAO.LibraryDAO.removeBook(bookID);

            if (x > 0) {
                JOptionPane.showMessageDialog(null, "Book has been successfully deleted!");
            }
        }
        clearFields();
    }

    // UPDATE TABLE AFTER MODIFYING IT
    private void updateTable() {

        try {
            String sql = "select * from Books";
            PreparedStatement pst = connection.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();
            bookTable.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void clearFields() {
        bookID_TxtField.setText(null);
        bookTitleTxtField.setText(null);
        authorNameTxtField.setText(null);
        bookQuantityTxtField.setText(null);
    }
    public static void main(String[] args) {
        librarianView myFrame = new librarianView();
    }
}


