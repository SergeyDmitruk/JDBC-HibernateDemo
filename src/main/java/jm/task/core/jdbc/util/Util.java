package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.PrimitiveIterator;
import java.util.Properties;

public class Util {
    String url = "jdbc:mysql://localhost:3306/";
    public Connection getConnectionSql()throws SQLException{
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3306/mysql";
        Properties prop = new Properties();
        prop.put("user", "root");
        prop.put("password", "password");
        prop.put("autoReconnect", "true");
        prop.put("characterEncoding", "UTF-8");
        return DriverManager.getConnection(url,prop);


    }
}
