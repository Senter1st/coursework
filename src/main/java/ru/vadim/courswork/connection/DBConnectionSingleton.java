package ru.vadim.courswork.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionSingleton {
    private static final Logger LOG = LogManager.getLogger(DBConnectionSingleton.class);
    private static DBConnectionSingleton instance;
    private Connection connection;

    private DBConnectionSingleton() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try (InputStream input = new FileInputStream("./src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            connection = DriverManager.getConnection(prop.getProperty("connection.url"));
        } catch (IOException ex) {
            LOG.error(ex);
            ex.printStackTrace();
        }
    }

    public static synchronized DBConnectionSingleton getInstance() {
        if (instance == null) {
            try {
                instance = new DBConnectionSingleton();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public static void close(){
        if (instance != null) {
            try {
                instance.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized Connection getConnection() {
        return connection;
    }
}
