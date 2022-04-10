package mauleenndlovu.libsystem.DAO;

import mauleenndlovu.libsystem.main.SqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LibraryDAO {

    public static int createAccount(String username, String password) {
        int status = 0;

        try {
            Connection conn = SqlConnection.dbConnect();
            PreparedStatement preparedstm = conn.prepareStatement("INSERT INTO Users(username, password) VALUES(?,?)");

            preparedstm.setString(1, username);
            preparedstm.setString(2, password);

            status = preparedstm.executeUpdate();
            conn.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public static boolean checkUsername(String username) {

        PreparedStatement preparedstm;
        ResultSet resultset;
        boolean check = false;

        String query = "SELECT * FROM Users WHERE username =?";

        try {
            Connection conn = SqlConnection.dbConnect();
            preparedstm = conn.prepareStatement(query);
            preparedstm.setString(1, username);

            resultset = preparedstm.executeQuery();

            if (resultset.next()) {
                check = true;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return check;
    }

    public static int addBook(String callno, String book_name, String book_author, int book_quantity) {

        int status = 0;

        try {
            Connection conn = SqlConnection.dbConnect();
            PreparedStatement preparedstm = conn
                    .prepareStatement("INSERT INTO Books (callno, book_name, book_author,book_quantity) VALUES (?, ?, ?, ?)");

            preparedstm.setString(1, callno);
            preparedstm.setString(2, book_name);
            preparedstm.setString(3, book_author);
            preparedstm.setInt(4, book_quantity);

            status = preparedstm.executeUpdate();
            conn.close();

        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        return status;
    }

    public static int updateBook(String bookcallno) {
        int status = 0;
        int book_quantity = 0,issued = 0;

        try {
            Connection conn =SqlConnection.dbConnect();

            PreparedStatement preparedstm = conn.prepareStatement("select book_quantity,issued from books where callno=?");
            preparedstm.setString(1, bookcallno);
            ResultSet rs = preparedstm.executeQuery();

            if (rs.next()) {
                book_quantity = rs.getInt("book_quantity");
                issued = rs.getInt("issued");
            }

            if (book_quantity > 0) {
                PreparedStatement preparedstm2 = conn.prepareStatement("update books set book_quantity=?,issued=? where callno=?");
                preparedstm2.setInt(1,book_quantity - 1);
                preparedstm2.setInt(2,issued+1);
                preparedstm2.setString(3, bookcallno);

                status=preparedstm2.executeUpdate();
            }
            conn.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        return status;
    }

    public static int removeBook(int book_id) {

        int status = 0;

        try {
            Connection conn = SqlConnection.dbConnect();
            PreparedStatement preparedstm = conn.prepareStatement("DELETE FROM Books WHERE book_id = ?");

            preparedstm.setInt(1, book_id);
            status = preparedstm.executeUpdate();
            conn.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public static boolean login(String username, String password) {
        boolean status = false;

        try {
            Connection conn = SqlConnection.dbConnect();
            PreparedStatement preparedstm = conn.prepareStatement("SELECT * FROM Users WHERE username=? AND password=? ");

            preparedstm.setString(1, username);
            preparedstm.setString(2, password);

            ResultSet rs = preparedstm.executeQuery();
            status = rs.next();
            conn.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return status;
    }

    public static boolean checkBook(String bookcallno) {

        boolean status = false;

        try {
            Connection conn = SqlConnection.dbConnect();
            PreparedStatement preparedstm = conn.prepareStatement("SELECT * FROM Books where callno=?");
            preparedstm.setString(1, bookcallno);

            ResultSet rs = preparedstm.executeQuery();
            status = rs.next();
            conn.close();
        }

        catch(Exception ex) {
            System.out.println(ex);
        }
        return status;
    }

    public static int saveLibrarian(String username, String password) {

        int status = 0;

        try {
            Connection conn = SqlConnection.dbConnect();
            PreparedStatement preparedstm = conn.prepareStatement("INSERT INTO Librarian(username, password) VALUES(?,?)");

            preparedstm.setString(1, username);
            preparedstm.setString(2, password);

            status = preparedstm.executeUpdate();
            conn.close();

        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        return status;
    }

    public static int deleteLibrarian(int id) {

        int status = 0;

        try {
            Connection conn = SqlConnection.dbConnect();

            PreparedStatement preparedstm = conn.prepareStatement("DELETE FROM Librarian WHERE id=?");
            preparedstm.setInt(1, id);
            status = preparedstm.executeUpdate();
            conn.close();
        }
        catch(Exception ex) {
            System.out.println(ex);
        }
        return status;
    }

    public static boolean validateLibrarian(String username, String password) {

        boolean status = false;

        try {
            Connection conn = SqlConnection.dbConnect();
            PreparedStatement preparedstm = conn.prepareStatement("SELECT * FROM Librarian WHERE username=? AND password=?");

            preparedstm.setString(1, username);
            preparedstm.setString(2, password);

            ResultSet rs = preparedstm.executeQuery();
            status = rs.next();
            conn.close();
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        return status;
    }

    public static int saveIssuedBook(String bookcallno, int userID, String username) {
         int status = 0;

         try {
             Connection conn = SqlConnection.dbConnect();
             status = updateBook(bookcallno); // Updating quantity and issue

             if (status > 0) {
                 PreparedStatement preparedstm = conn.prepareStatement("INSERT INTO Issue_Books(bookcallno, userID, username) VALUES(?, ?, ?)");

                 preparedstm.setString(1, bookcallno);
                 preparedstm.setInt(2,userID);
                 preparedstm.setString(3, username);

                 status = preparedstm.executeUpdate();
             }

             conn.close();
         }
         catch(Exception ex) {
             System.out.println(ex);
         }
         return status;
    }
}
