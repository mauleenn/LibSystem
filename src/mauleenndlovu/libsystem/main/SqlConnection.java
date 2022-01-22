package mauleenndlovu.libsystem.main;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.*;

public class SqlConnection {

    public static Connection dbConnect() {

        // After loading the driver, establish connections
        Connection conn = null;

        // The try statement allows you to define a block
        // of code to be tested for errors while it is being executed.
        try {
            // Here we load the driver’s class file into memory at the runtime.
            Class.forName("org.sqlite.JDBC");

            // The DB is located in this file path
            conn = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
            return conn;
        }

        // The catch statement allows you to define a block
        // of code to be executed, if an error occurs in the try block.
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}