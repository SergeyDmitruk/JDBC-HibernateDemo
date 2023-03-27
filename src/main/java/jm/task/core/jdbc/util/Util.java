package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static SessionFactory sessionFactory;
private static Util instance;

public Connection getConnectionSql() throws SQLException {
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
    return DriverManager.getConnection(url, prop);
}

public static Util getInstance() throws SQLException {
    if (instance == null) {
        instance = new Util();
    } else if (instance.getConnectionSql().isClosed()) {
        instance = new Util();
    }

    return instance;
}

public static SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        properties.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
        properties.put(Environment.URL, "jdbc:mysql://localhost:3306/mysql");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.SHOW_SQL, "true");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "password");
        configuration.setProperties(properties);
        configuration.addAnnotatedClass(User.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }
    return sessionFactory;
}
}
