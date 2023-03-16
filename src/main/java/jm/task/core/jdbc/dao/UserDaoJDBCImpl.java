package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    String sqlCreateTable = "CREATE TABLE IF NOT EXISTS users\n" +
            "(id MEDIUMINT NOT NULL AUTO_INCREMENT,\n" +
            " name VARCHAR(30) NOT NULL,\n" +
            "  lastName VARCHAR(30) NOT NULL,\n" +
            "   age TINYINT NOT NULL,\n" +
            "   PRIMARY KEY (id));";
    String sqlDeleteTable = "DROP TABLE IF EXISTS users;";
    String sqlDeleteUser = "DELETE FROM users WHERE id =";
    String sqlGetAllUsers = "SELECT * FROM users;";
    String sqlCleanTableUsers = "TRUNCATE TABLE users;";

    Util createConnection = new Util();
   private Connection connection;


    public UserDaoJDBCImpl() {

    }


    public void createUsersTable() {
try(Connection connection = createConnection.getConnectionSql();
    Statement statement = connection.createStatement()){
    statement.executeUpdate(sqlCreateTable);
}catch (SQLException e){
    e.printStackTrace();
}
    }

    public void dropUsersTable() {
        try(Connection connection = createConnection.getConnectionSql();
            Statement statement = connection.createStatement()){
            statement.executeUpdate(sqlDeleteTable);
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection connection = createConnection.getConnectionSql();
            PreparedStatement statement =
                    connection.prepareStatement("INSERT INTO users(name, lastName, age) Values(?,?,?) ")){
                statement.setString(1,name);
                statement.setString(2,lastName);
                statement.setByte(3,age);
                statement.executeUpdate();
            System.out.println("User with name " + name + " was added in DB");

        }catch (SQLException e){
            System.err.println("Error saving user  " + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        try(Connection connection = createConnection.getConnectionSql();
            Statement statement = connection.createStatement()){
            statement.executeUpdate(sqlDeleteUser  + id + ";");

        }catch (SQLException e){
            System.err.println("Error deleting user  " + e.getMessage());
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Connection connection = createConnection.getConnectionSql();
            Statement statement = connection.createStatement()){

            ResultSet resultSet = statement.executeQuery(sqlGetAllUsers);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                users.add(user);
            }

        }catch (SQLException e){
            System.err.println("Error getting all users  " + e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        try(Connection connection = createConnection.getConnectionSql();
            Statement statement = connection.createStatement()){
            statement.executeUpdate(sqlCleanTableUsers);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
