package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import static jm.task.core.jdbc.util.Util.getConnection;
import static jm.task.core.jdbc.util.Util.user;
import static org.hibernate.cache.spi.support.SimpleTimestamper.next;

public class UserDaoJDBCImpl implements UserDao {
  Connection connection;



    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastname VARCHAR(255), age INT)");
            System.out.println("Table create");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
            }
        }
    }
    public void dropUsersTable() {

        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            System.out.println("Table delete");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection =  getConnection()){
            PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO users (name, lastname, age) VALUES (?, ?, ?)");
            prepareStatement.setString(1, name);
            prepareStatement.setString(2, lastName);
            prepareStatement.setByte(3, age);
            prepareStatement.executeUpdate();
            System.out.println("User name – " + name + " add to database");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        }
    }



        public void removeUserById ( long id){
            try (Connection connection = getConnection()){
                PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
                prepareStatement.setLong(1, id);
                prepareStatement.executeUpdate();
                System.out.println("User delete");
                connection.commit();
            } catch (SQLException e) {
                try {
                    getConnection().rollback();
                } catch (SQLException ex) {
                    e.printStackTrace();
                }

            }
    }
        public List<User> getAllUsers () {
            List<User> allUser = new ArrayList<>();
            String sql = "SELECT id, name, lastName, age FROM users";
            try (Connection connection = getConnection()){
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql);
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong("id"));
                    user.setName(rs.getString("name"));
                    user.setLastName(rs.getString("lastName"));
                    user.setAge(rs.getByte("age"));
                    allUser.add(user);
                }
                connection.commit();
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                }

            }
            return allUser;
        }
        public void cleanUsersTable() {

            try (Connection connection = getConnection()){
                Statement statement = connection.createStatement();
                statement.executeUpdate("TRUNCATE TABLE users");
                System.out.println("Table clear");
                connection.commit();
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                }
            }
        }
}


