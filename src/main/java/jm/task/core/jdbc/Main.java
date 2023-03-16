package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
public static void main(String[] args) {
    UserService service = new UserServiceImpl();
    service.createUsersTable();
    service.saveUser("Albert", "Einstein", (byte) 20);
    service.saveUser("Linus", "Torvalds", (byte) 19);
    service.saveUser("Elon", "Musk", (byte) 33);
    service.saveUser("Diana", "Spencer", (byte) 28);
    List<User> users = service.getAllUsers();
    for (User user : users) {
        System.out.println(user.toString());
    }
    service.cleanUsersTable();
    service.dropUsersTable();

}
}
