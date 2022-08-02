package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.cfg.AvailableSettings.DRIVER;


public class Util {

    public static final String user = "roott";
    public static final String passvord = "root";
    public static final String url = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";


    private static SessionFactory sessionFactory = null;



    public static SessionFactory getConnection() {

        try {
            Configuration configuration = new Configuration()
                    .setProperty("hibernate.connection.driver_class", DRIVER)
                    .setProperty("hibernate.connection.url", url)
                    .setProperty("hibernate.connection.username", user)
                    .setProperty("hibernate.connection.password", passvord)
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                    .addAnnotatedClass(User.class);
//                    .setProperty("hibernate.c3p0.min_size","5")
//                    .setProperty("hibernate.c3p0.max_size","255")
//                    .setProperty("hibernate.c3p0.max_statements","255");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }
}