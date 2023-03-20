package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

private Connection connection;

{
    try {
        Util createConnection = Util.getInstance();
        connection = createConnection.getConnectionSql();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}


public UserDaoJDBCImpl() {

}


public void createUsersTable() {
    try (Statement statement = connection.createStatement()) {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS users\n" +
                "(id MEDIUMINT NOT NULL AUTO_INCREMENT,\n" +
                " name VARCHAR(30) NOT NULL,\n" +
                "  lastName VARCHAR(30) NOT NULL,\n" +
                "   age TINYINT NOT NULL,\n" +
                "   PRIMARY KEY (id));");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void dropUsersTable() {
    try (Statement statement = connection.createStatement()) {
        statement.executeUpdate("DROP TABLE IF EXISTS users;");
    } catch (SQLException e) {
        e.printStackTrace();
    }

}

public void saveUser(String name, String lastName, byte age) {
    try (PreparedStatement statement =
                 connection.prepareStatement("INSERT INTO users(name, lastName, age) Values(?,?,?) ")) {
        statement.setString(1, name);
        statement.setString(2, lastName);
        statement.setByte(3, age);
        statement.executeUpdate();
        System.out.println("User with name " + name + " was added in DB");

    } catch (SQLException e) {
        System.err.println("Error saving user  " + e.getMessage());
    }
}

public void removeUserById(long id) {
    try (Statement statement = connection.createStatement()) {
        statement.executeUpdate("DELETE FROM users WHERE id =" + id + ";");

    } catch (SQLException e) {
        System.err.println("Error deleting user  " + e.getMessage());
    }

}

public List<User> getAllUsers() {
    List<User> users = new ArrayList<>();
    try (Statement statement = connection.createStatement()) {

        ResultSet resultSet = statement.executeQuery("SELECT * FROM users;");
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getLong(1));
            user.setName(resultSet.getString(2));
            user.setLastName(resultSet.getString(3));
            user.setAge(resultSet.getByte(4));
            users.add(user);
        }

    } catch (SQLException e) {
        System.err.println("Error getting all users  " + e.getMessage());
    }
    return users;
}

public void cleanUsersTable() {
    try (Statement statement = connection.createStatement()) {
        statement.executeUpdate("TRUNCATE TABLE users;");

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}
