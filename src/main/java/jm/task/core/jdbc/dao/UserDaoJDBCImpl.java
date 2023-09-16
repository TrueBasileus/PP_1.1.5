package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()) {
            statement.executeUpdate("create Table if not exists Users (id BIGINT Primary Key Auto_increment not null, name VARCHAR(64), lastname VARCHAR(64), age TINYINT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
             statement.executeUpdate("Drop Table if exists Users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement state = connection.prepareStatement("insert into Users(name, lastname, age) values (?, ?, ?)")) {
             state.setString(1, name);
             state.setString(2, lastName);
             state.setByte(3, age);
             state.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
             statement.executeUpdate("delete from Users where id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("Select * FROM Users")) {
             while (resultSet.next()) {
                 list.add(new User(resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4) ));
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("delete from Users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
