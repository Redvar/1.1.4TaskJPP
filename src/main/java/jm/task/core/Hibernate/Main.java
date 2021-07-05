package jm.task.core.Hibernate;

import jm.task.core.Hibernate.model.User;
import jm.task.core.Hibernate.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Павел", "Коноплин", (byte) 22);
        userService.saveUser("Сергей", "Иванов", (byte) 7);
        userService.saveUser("Максим", "Достоевский", (byte) 87);
        userService.saveUser("Александр", "Горчаков", (byte) 2);
        List<User> userList = userService.getAllUsers();
        userList.forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
