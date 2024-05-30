package sexy.criss.task.core.jdbc.util;

import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import sexy.criss.task.core.jdbc.model.User;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Util {
    private static Connection connection;
    @Getter
    private static SessionFactory sessionFactory;

    static {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();

        properties.put(AvailableSettings.DRIVER, "com.mysql.cj.jdbc.Driver");
        properties.put(AvailableSettings.URL, "jdbc:mysql://localhost:3306/dataBase");
        properties.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(AvailableSettings.USER, "root");
        properties.put(AvailableSettings.PASS, "password");

        configuration.addAnnotatedClass(User.class);
        configuration.setProperties(properties);

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        try {
            sessionFactory = configuration.buildSessionFactory(registry);
        } catch (Exception ex) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static void commit(Session session, Action action) {
        try (session) {
            session.beginTransaction();
            action.handle();
            session.getTransaction().commit();
        }
    }

    public static Connection getConnection() {
        if(connection != null) return connection;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dataBase", "root", "password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static boolean execute(String sql, Object... args) {
        boolean result = false;
        if(sql == null || sql.isEmpty()) return false;
        if(args.length > 0) {
            sql = format(sql, args);
        }
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            result = ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static PreparedStatement prepared(String sql, Object... args) throws SQLException {
        if(sql == null || sql.isEmpty()) return null;
        sql = format(sql, args);
        return getConnection().prepareStatement(sql);
    }

    public static String format(String s, Object... args) {
        return args.length > 0 ? String.format(s, args) : s;
    }

}