package ru.vadim.courswork.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionSingleton {
    private static final Logger LOG = LogManager.getLogger(DBConnectionSingleton.class);
    private static DBConnectionSingleton instance;
    private final String CONNECTION_STRING = "jdbc:sqlite:students.db";
    private Connection connection;

    private DBConnectionSingleton() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(CONNECTION_STRING);
        } catch (ClassNotFoundException | SQLException e) {
            LOG.error(e);
            throw e;
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

    public synchronized Connection getConnection() {
        return connection;
    }
}
