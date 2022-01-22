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
        } catch (Exception e) {
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
}
