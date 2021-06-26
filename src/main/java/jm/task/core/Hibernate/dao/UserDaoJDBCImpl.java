package jm.task.core.Hibernate.dao;
import jm.task.core.Hibernate.model.User;
import jm.task.core.Hibernate.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    public void createUsersTable() {
        try(Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(
                        "create table IF NOT EXISTS users (" + " id bigint not null  AUTO_INCREMENT," +
                            " user_name varchar(25)," + " last_name varchar(25)," +
                            " age tinyint," + " PRIMARY KEY (id)" + ");");
            System.out.println("Таблица создана успешно.");
        } catch (SQLException e) {
            throw new UserDaoException("Ошибка создания таблицы", e);
        }
    }

    public void dropUsersTable() {
        try(Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute("drop table IF EXISTS users;");
            System.out.println("Таблица успешно удалена.");
        } catch (SQLException e) {
            throw new UserDaoException("Ошибка удаления таблицы.", e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection()){
            PreparedStatement statement = connection.prepareStatement(
                    "insert into users (user_name, last_name, age) " + " values (?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
            System.out.println("User с именем - " + name +
                    " добавлен в базу данных");
        } catch (SQLException e) {
            throw new UserDaoException("Ошибка сохранения пользователя.", e);
        }
    }

    public void removeUserById(long id) {
        try(Connection connection = Util.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("delete from users where id = ?");
            statement.setLong(1, id);
            statement.execute();
            System.out.println("User с id - " + id +
                    " удалён из базы данных");
        } catch (SQLException e) {
            throw new UserDaoException("Ошибка удаления пользователя: " + id, e);
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();

        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("select id, user_name, last_name, age FROM users;");

            while (set.next()){
                User user = new User();
                user.setId(set.getLong(1));
                user.setName(set.getString(2));
                user.setLastName(set.getString(3));
                user.setAge(set.getByte(4));
                result.add(user);
            }
            System.out.println("Список пользователей успешно получен.");

        } catch (SQLException e) {
            throw new UserDaoException("Ошибка получения всех пользователей.", e);
        }
        return result;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()){
            PreparedStatement statement = connection.prepareStatement("delete FROM users;");
            statement.execute();
            System.out.println("Таблица успешно очищена.");

        } catch (SQLException e) {
            throw new UserDaoException("Ошибка очистки таблицы пользователей.", e);
        }
    }
}
