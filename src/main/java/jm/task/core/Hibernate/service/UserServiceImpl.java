package jm.task.core.Hibernate.service;
import jm.task.core.Hibernate.dao.UserDao;
import jm.task.core.Hibernate.dao.UserDaoHibernateImpl;
import jm.task.core.Hibernate.dao.UserDaoJDBCImpl;
import jm.task.core.Hibernate.model.User;
import jm.task.core.Hibernate.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;


public class UserServiceImpl implements UserService {


    SessionFactory session = Util.getSessionFactory();
    UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl(session);

    public void createUsersTable() {
        userDaoHibernate.createUsersTable();
    }

    public void dropUsersTable() {
        userDaoHibernate.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        userDaoHibernate.saveUser(name,lastName,age);
    }

    public void removeUserById(long id) {
        userDaoHibernate.removeUserById(id);
    }

    public List<User> getAllUsers() {
        List<User> users = userDaoHibernate.getAllUsers();
        return users;
    }

    public void cleanUsersTable() {
        userDaoHibernate.cleanUsersTable();
    }
}
