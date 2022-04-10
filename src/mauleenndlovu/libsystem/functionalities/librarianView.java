package mauleenndlovu.libsystem.functionalities;

import mauleenndlovu.libsystem.main.SqlConnection;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class librarianView extends JFrame {
    private JPanel mainPanel;
    private JTextField bookID_TxtField;
    private JTextField bookTitleTxtField;
    private JTextField authorNameTxtField;
    private JTextField bookQuantityTxtField;
    private JLabel authorName;
    private JLabel bookTitle;
    private JLabel bookQuantity;
    private JLabel bookCallNO;
    private JButton searchButton;
    private JButton addButton;
    private JButton removeButton;
    private JButton clearButton;
    private JButton updateButton;
    private JButton viewBooksButton;
    private JTable bookTable;
    private JButton issueBookButton;
    private JButton viewIssuedBooksButton;
    private JButton returnBookButton;
    private JButton logoutButton;

    Connection connection = null;


    public librarianView() {

        connection = SqlConnection.dbConnect();

        setContentPane(mainPanel);
        setTitle("Librarian View");
        setSize(700,500);
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
                ((DefaultTableModel)bookTable.getModel()).removeRow(2);

                remove();
            }
        });


        viewBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewBooks();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
    }


    private void search() {

        try {
            bookTable.setModel(new DefaultTableModel( new Object[][] {},
                    new String[] {"Book ID", "Book Name", "Book Author", "Quantity"}));

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

        // Autofill in the txt boxes
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

    /**
     *  Adding a new book to the database.
     */
    private void add() {

        try {
            String callno = bookID_TxtField.getText();
            String book_name = bookTitleTxtField.getText();
            String book_author = authorNameTxtField.getText();
            String quantity = bookQuantityTxtField.getText();
            int book_quantity = Integer.parseInt(quantity);

            int x = mauleenndlovu.libsystem.DAO.LibraryDAO.addBook(callno, book_name, book_author, book_quantity);
            if (x > 0) {
                JOptionPane.showMessageDialog(librarianView.this, "Book has been successfully added!");
            }
            else {
                JOptionPane.showMessageDialog(librarianView.this, "An error has occurred, book has not been added.");
            }
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        updateTable();
    }

    private void remove() {

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

    private void clearFields() {
        bookID_TxtField.setText("");
        bookTitleTxtField.setText("");
        authorNameTxtField.setText("");
        bookQuantityTxtField.setText("");
    }

    private void clearTable() {

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
    }

    private void viewBooks() {
        String data[][]=null;
        String column[]=null;
        try{
            Connection con=SqlConnection.dbConnect();
            PreparedStatement ps=con.prepareStatement("SELECT * FROM Books",ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=ps.executeQuery();

            ResultSetMetaData rsmd=rs.getMetaData();
            int cols=rsmd.getColumnCount();
            column=new String[cols];
            for(int i=1;i<=cols;i++){
                column[i-1]=rsmd.getColumnName(i);
            }

            rs.last();
            int rows=rs.getRow();
            rs.beforeFirst();

            data=new String[rows][cols];
            int count=0;
            while(rs.next()){
                for(int i=1;i<=cols;i++){
                    data[count][i-1]=rs.getString(i);
                }
                count++;
            }
            con.close();
        }catch(Exception e){System.out.println(e);}

        bookTable = new JTable(data,column);
        JScrollPane sp=new JScrollPane(bookTable);
    }

    private void logout() {
        account accountWindow = new account();
        setVisible(false);
        accountWindow.setVisible(true);
    }


    private void showTableColumns() {

        try {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Code");
            model.addColumn("Name");
            model.addColumn("Quantity");
            model.addColumn("Unit Price");
            model.addColumn("Price");
            bookTable = new JTable(model);

            String[] rowNames = {"Book ID: ", "Book Call #: ", "Book Title: ", "Book Author: ", "Book Quantity: ", "Date Added: "};

            String[][] value = new String[1][6];
            JTable bookTable = new JTable(value, rowNames);
            bookTable.setBounds(800, 450, 1100, 80);
            bookTable.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
            bookTable.setRowHeight(20);
            bookTable.setForeground(Color.BLACK);
            bookTable.setBackground(Color.lightGray);

            JFrame bookFrame = new JFrame();
            bookFrame.setSize(700,500);
            bookFrame.add(new JScrollPane(bookTable));
            bookFrame.setVisible(true);

        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void main(String[] args) {
        librarianView myFrame = new librarianView();
    }
}


