package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static Connection getConnection () throws SQLException{
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/katapp", "root", "APGVn4i7");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
