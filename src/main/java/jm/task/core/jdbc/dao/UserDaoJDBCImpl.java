package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection;

    {
        try {
            connection = Util.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserDaoJDBCImpl() {

    }
    @Override
    public void createUsersTable() {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("create Table if not exists users (id BIGINT Primary Key Auto_increment not null, name VARCHAR(64), lastname VARCHAR(64), age TINYINT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
             statement.executeUpdate("Drop Table if exists users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement state = connection.prepareStatement("insert into users(name, lastname, age) values (?, ?, ?)")) {
             state.setString(1, name);
             state.setString(2, lastName);
             state.setByte(3, age);
             state.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void removeUserById(long id) {
        try (PreparedStatement statement = connection.prepareStatement("delete from users where id = ?")) {
             statement.setLong(1, id);
             statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("Select * FROM users")) {
             while (resultSet.next()) {
                 list.add(new User(resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4) ));
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    @Override
    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("delete from users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
