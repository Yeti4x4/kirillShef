package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable()  {
        try {
            Statement statement = connection.createStatement();
             statement.executeUpdate("CREATE TABLE IF NOT EXISTS users" +
                    "(id bigint not null auto_increment," +
                    "name VARCHAR(50)," +
                    "lastname VARCHAR(50)," +
                    "age tinyint," +
                    "PRIMARY KEY (id))");
            System.out.println("Table create");
            connection.commit();
        } catch (SQLException e) {
        Util.rollbackQuietly(connection);

        }

    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            System.out.println("Table delete");
            connection.commit();
        } catch (SQLException e) {
            Util.rollbackQuietly(connection);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users(name, lastname, age) VALUES(?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User name – " + name + " add to database");
            connection.commit();
        } catch (SQLException e) {
            Util.rollbackQuietly(connection);
        }

    }

    public void removeUserById(long id) {
        try (Statement statement = connection.createStatement()) {
            String sql = "DELETE FROM users WHERE id";
            statement.executeUpdate(sql);
            System.out.println("User delete");
            connection.commit();
        } catch (SQLException e) {
            Util.rollbackQuietly(connection);
        }

    }

    public List<User> getAllUsers() {
        List<User> allUser = new ArrayList<>();
        String sql = "SELECT id, name, lastName, age FROM users";

        try (Statement statement = connection.createStatement()) {
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
            Util.rollbackQuietly(connection);

        }
        return allUser;
    }
    public void cleanUsersTable() {
        String sql = "TRUNCATE users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Table clear");
            connection.commit();
        } catch (SQLException e) {
            Util.rollbackQuietly(connection);
            System.out.println("Table NOT clear");


        }


    }

}
