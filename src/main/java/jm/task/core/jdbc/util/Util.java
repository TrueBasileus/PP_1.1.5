package jm.task.core.jdbc.util;

import java.util.Properties;

import java.sql.SQLException;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import jm.task.core.jdbc.model.User;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/katapp";
    private static final String USER = "root";
    private static final String PASSWORD = "APGVn4i7";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static SessionFactory sf;

    public static SessionFactory getConnection() {
        Configuration configuration = new Configuration();
        Properties settings = new Properties();
        settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        settings.put(Environment.DRIVER, DRIVER);
        settings.put(Environment.URL, URL);
        settings.put(Environment.USER, USER);
        settings.put(Environment.PASS, PASSWORD);
        settings.put(Environment.SHOW_SQL, true);
        configuration.setProperties(settings);
        configuration.addAnnotatedClass(User.class);
        ServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sf = configuration.buildSessionFactory(sr);
        return sf;
    }

    public static void closeConnection() {
        sf.close();
    }
}
