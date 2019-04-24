package ru.vadim.courswork.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    private static int INITIAL_POOL_SIZE = 1;
    private static int MAX_POOL_SIZE = 5;
    private static ConnectionPool instance;
    private String connectionString;
    private List<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();

    private ConnectionPool(String connectionString, List<Connection> pool) {
        this.connectionString = connectionString;
        this.connectionPool = pool;
    }

    public static synchronized ConnectionPool create(String connectionString) throws SQLException {
        if (instance == null) {
            List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
            for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                pool.add(createConnection(connectionString));
            }
            instance = new ConnectionPool(connectionString, pool);
        }
        return instance;
    }

    private static Connection createConnection(String connectionString) throws SQLException {
        return DriverManager.getConnection(connectionString);
    }

    public Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                connectionPool.add(createConnection(connectionString));
            } else {
                throw new RuntimeException("Over maximum pool size");
            }
        }

        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    public void clearPool() throws SQLException {
        connectionPool.addAll(usedConnections);
        usedConnections.clear();

        for (Connection connection : connectionPool) {
            connection.close();
        }
        connectionPool.clear();
    }
}
