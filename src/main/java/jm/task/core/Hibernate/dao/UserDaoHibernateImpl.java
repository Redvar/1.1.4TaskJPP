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

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory sessionFactory;

    public UserDaoHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void createUsersTable() {
        try {
            Session session = Util.getSessionFactory().openSession();
            Transaction transaction = null;
            transaction = session.beginTransaction();
            NativeQuery sqlQuery = session.createSQLQuery(
                    "create table IF NOT EXISTS users (" + " id bigint not null  AUTO_INCREMENT," +
                            " user_name varchar(25)," + " last_name varchar(25)," +
                            " age tinyint," + " PRIMARY KEY (id)" + ")");
            sqlQuery.executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            throw new UserDaoException("Ошибка создания БД", e);
        }

    }

    @Override
    public void dropUsersTable() throws HibernateException {
        try {
            Session session = Util.getSessionFactory().openSession();
            Transaction transaction = null;
            transaction = session.beginTransaction();
            Query querySql = session.createSQLQuery("DROP TABLE user ");
            querySql.executeUpdate();
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
            session.save(new User(name, lastName, age));
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
            session.createQuery("DELETE User WHERE id = " + id);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new UserDaoException("Ошибка удаления пользователя по ID", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List list = null;

        try {
            Session session = Util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Query querySql = session.createNativeQuery("SELECT * FROM user", User.class);
            list = querySql.getResultList();
            transaction.commit();
            session.close();
            return list;
        } catch (HibernateException e) {
            throw new UserDaoException("При получение всех Users произошла ошибка.", e);
        }
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("DELETE FROM User");
        query.executeUpdate();
        transaction.commit();
        session.close();
    }
}
