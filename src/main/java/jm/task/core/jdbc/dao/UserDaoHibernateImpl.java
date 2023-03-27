package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {


public UserDaoHibernateImpl() {

}


@Override
public void createUsersTable() {
    Transaction tx = null;
    try (Session session = Util.getSessionFactory().openSession()) {
        tx = session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS users\n" +
                "(id MEDIUMINT NOT NULL AUTO_INCREMENT,\n" +
                " name VARCHAR(30) NOT NULL,\n" +
                "  lastName VARCHAR(30) NOT NULL,\n" +
                "   age TINYINT NOT NULL,\n" +
                "   PRIMARY KEY (id));").executeUpdate();
        tx.commit();
    } catch (Exception e) {
        if (tx != null) {
            tx.rollback();
        }
        System.err.println("Error create table: " + e.getMessage());
    }
}

@Override
public void dropUsersTable() {
    Transaction tx = null;
    try (Session session = Util.getSessionFactory().openSession()) {
        tx = session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS users;").executeUpdate();
        tx.commit();
    } catch (Exception e) {
        if (tx != null) {
            tx.rollback();
        }
        System.err.println("Error create table: " + e.getMessage());
    }

}

@Override
public void saveUser(String name, String lastName, byte age) {
    try (Session session = Util.getSessionFactory().openSession()) {
        session.beginTransaction();
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        session.save(user);
        session.getTransaction().commit();
    } catch (Exception e) {
        System.err.println("Error save user:" + e);

    }

}

@Override
public void removeUserById(long id) {
    try (Session session = Util.getSessionFactory().openSession()) {
        session.beginTransaction();
        User user = session.get(User.class, id);
        session.delete(user);
        session.getTransaction().commit();
    } catch (Exception e) {
        System.err.println("Error remove user:" + e.getMessage());
    }

}

@Override
public List<User> getAllUsers() {
    List<User> users = null;
    try (Session session = Util.getSessionFactory().openSession()) {
        session.beginTransaction();
        users = session.createQuery("FROM User").getResultList();
        session.getTransaction().commit();

    } catch (Exception e) {
        System.err.println("Error get all users:" + e.getMessage());
    }
    return users;
}

@Override
public void cleanUsersTable() {
    try (Session session = Util.getSessionFactory().openSession()) {
        session.beginTransaction();
        session.createQuery("delete User").executeUpdate();
        session.getTransaction().commit();
    } catch (Exception e) {
        System.err.println("Error clean users table: " + e.getMessage());
    }
}
}
