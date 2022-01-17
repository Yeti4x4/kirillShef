package jm.task.core.jdbc.util;

import java.sql.*;

import static org.hibernate.cfg.AvailableSettings.DRIVER;


public class Util {
    // реализуйте настройку соеденения с БД
    private static final String urlfixed =
            "jdbc:mysql://localhost:3306/mysql?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true" +
                    "&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String user = "roott";
    private static final String password = "root";

    public static Connection connection;

    public static Connection getConnection() {
        return connection;
    }
//Statement statement = null;
    //ResultSet rs = null;


    public Util() {
        try {
            connection = DriverManager.getConnection(urlfixed, user, password);
            Statement statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // public static void rollbackQuietly(Connection connection){
        //  if(connection != null){
        //  try {
        // connection.rollback();
        //  } catch (SQLException e) {
        //  }
        // public static void closeQuietly(ResultSet rs){
        //    if(connection != null){
        //       try {
        //           connection.rollback();
        //   } catch (SQLException e) {
    }

}
        //}
   // }













