package mauleenndlovu.libsystem.functionalities;

import mauleenndlovu.libsystem.main.SqlConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class viewIssuedBooks extends JFrame {
    private JPanel mainPanel;
    private JTable issuedBooksTable;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {

                try {
                    viewIssuedBooks viewIssuedBooksFrame = new viewIssuedBooks();
                    viewIssuedBooksFrame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public viewIssuedBooks() {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainPanel.setLayout(new BorderLayout(0, 0));
        setContentPane(mainPanel);

        String data[][]=null;
        String column[]= {"Book ID", "Book Call #", "Book Title", "Book Author", "Book Quantity", "Date Added"};
        try{
            Connection con= SqlConnection.dbConnect();
            PreparedStatement ps=con.prepareStatement("SELECT * from Issue_Books", ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
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

        issuedBooksTable = new JTable(data,column);
        JScrollPane sp=new JScrollPane(issuedBooksTable);

        mainPanel.add(sp, BorderLayout.CENTER);
    }
}
