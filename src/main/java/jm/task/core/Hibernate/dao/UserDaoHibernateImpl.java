package jm.task.core.Hibernate.dao;

import jm.task.core.Hibernate.model.User;
import jm.task.core.Hibernate.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;
import java.util.zip.DataFormatException;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory sessionFactory;

    public UserDaoHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void createUsersTable() {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = null;
            transaction = session.beginTransaction();
            NativeQuery sqlQuery = session.createSQLQuery(
                    "create table IF NOT EXISTS users ( id bigint not null  AUTO_INCREMENT, user_name varchar(25), last_name varchar(25), age tinyint, PRIMARY KEY (id))");
            sqlQuery.executeUpdate();
            transaction.commit();
            System.out.println("Таблица успешно создана.");
            session.close();
        } catch (Exception e) {
            throw new UserDaoException("Ошибка создания БД", e);
        }

    }

    @Override
    public void dropUsersTable() throws HibernateException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            NativeQuery nativeQuery = session.createSQLQuery("drop table IF EXISTS users;");
            nativeQuery.executeUpdate();
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new UserDaoException("Ошибка удаления БД.", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            NativeQuery nativeQuery = session.createSQLQuery("(user_name, last_name, age) " + " values (?, ?, ?)");
            nativeQuery.setParameter(1, name);
            nativeQuery.setParameter(2, lastName);
            nativeQuery.setParameter(3, age);
            nativeQuery.executeUpdate();
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new UserDaoException("При сохранение пользвателя произошла ошибка", e);
        }

    }

    @Override
    public void removeUserById(long id) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            NativeQuery nativeQuery = session.createSQLQuery("delete from users where id = ?");
            nativeQuery.setParameter(1, id);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new UserDaoException("Ошибка удаления пользователя по ID", e);
        }
    }

    @Override
    public List<User> getAllUsers() {

        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            NativeQuery nativeQuery = session.createSQLQuery("select id, user_name, last_name, age FROM users");
            List list = nativeQuery.getResultList();
            transaction.commit();
            System.out.println("Получение всех Юзеров прошло успешно.");
            session.close();
            return list;
        } catch (HibernateException e) {
            throw new UserDaoException("При получение всех Users произошла ошибка.", e);
        }
    }

    @Override
    public void cleanUsersTable() {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            NativeQuery nativeQuery = session.createSQLQuery("delete FROM users;");//Native
            nativeQuery.executeUpdate();
            transaction.commit();
            System.out.println("Удаления таблицы User-ов прошла успешно.");
            session.close();
        } catch (HibernateException e) {
            throw new UserDaoException("Ошибка удаления таблицы User-ов", e);
        }
    }
}
