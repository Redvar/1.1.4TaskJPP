package jm.task.core.Hibernate.dao;

import jm.task.core.Hibernate.model.User;
import jm.task.core.Hibernate.util.Util;
import org.hibernate.*;
import org.hibernate.query.NativeQuery;

import java.util.ArrayList;
import java.util.List;

import static jm.task.core.Hibernate.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction1;
        try (Session session = getSessionFactory().openSession()) {
            transaction1 = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS USER " +
                    "(id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(45) NOT NULL ," +
                    "lastName VARCHAR(45) NOT NULL ,age INT NOT NULL )")
                    .executeUpdate();
            transaction1.commit();
            System.out.println("Таблица успешно создана.");
        } catch (HibernateException e) {
            throw new UserDaoException("При создание БД произошла ошибка", e);
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction2;
        try (Session session = getSessionFactory().openSession()) {
            transaction2 = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS USER")
                    .executeUpdate();
            System.out.println("Таблицы успешно удалена.");
        } catch (HibernateException e) {
            throw new UserDaoException("При удаление ошибки произошла ошибка", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction3;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction3 = session.beginTransaction();
            session.save(new User(name, lastName, age));
            System.out.println("Пользователь успешно добавлен.");
            transaction3.commit();
        } catch (HibernateException e) {
            throw new UserDaoException("При добавление пользователя произошла ошибка", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction4;
        try (Session session = getSessionFactory().openSession()) {
            transaction4 = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction4.commit();
            System.out.println("Пользователь успешно удалён.");
        } catch (HibernateException e) {
            throw new UserDaoException("При удаления пользователя по id произошла ошибка", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Transaction transaction5;
        try (Session session = getSessionFactory().openSession()) {
            transaction5 = session.beginTransaction();
            userList = session.createQuery("FROM User").list();
            transaction5.commit();
            System.out.println("Список пользователей успешно получен.");
            return userList;
        } catch (HibernateException e) {
            throw new UserDaoException("При получение списка пользователей произошла ошибка", e);
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction6 = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction6 = session.beginTransaction();
            session.createSQLQuery("DELETE FROM user")
                    .executeUpdate();
            System.out.println("таблица успешно очищена.");
            transaction6.commit();
        } catch (HibernateException e) {
            throw new UserDaoException("При очистке таблицы произошла ошибка", e);
        }
    }
}