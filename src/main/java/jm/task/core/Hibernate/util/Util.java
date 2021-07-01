
package jm.task.core.Hibernate.util;
import jm.task.core.Hibernate.model.User;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.imageio.spi.ServiceRegistry;
import java.sql.Connection;

public class Util {
    private final String URL = "jdbc:mysql://localhost:3306/baida?useUnicode=true&serverTimezone=UTC";
    private final String USER = "root";
    private final String PASSWORD = "glhf1941WAW";
    private Connection connection;
    public static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null){
            sessionFactory = createSessionFactory();
        }
        return sessionFactory;
    }

    public static Configuration getMySqlConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/mydb");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "glhf1941WAW");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        return configuration;
    }

    public static SessionFactory createSessionFactory(){
        Configuration configuration = getMySqlConfiguration();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = (ServiceRegistry) builder.build();
        return configuration.buildSessionFactory((org.hibernate.service.ServiceRegistry) serviceRegistry);
    }

    public Connection getConnection() {
        return connection;
    }

}
