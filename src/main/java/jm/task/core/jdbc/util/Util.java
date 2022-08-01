package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.cfg.AvailableSettings.DRIVER;


public class Util {

    public static final String user = "roott";
    public static final String passvord = "root";
    public static final String url = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimeCode=false&serverTimezone=UTC";


    private static Connection connection = null;


    static {
        try {
            connection = DriverManager.getConnection(url, user, passvord);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static Connection getConnection() {


         try {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException();
        }


        return connection;
    }
}
