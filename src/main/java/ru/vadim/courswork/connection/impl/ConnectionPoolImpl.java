package ru.vadim.courswork.connection.impl;

import ru.vadim.courswork.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPoolImpl implements ConnectionPool {
    private static int INITIAL_POOL_SIZE = 5;
    private String connectionString;
    private List<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();

    private ConnectionPoolImpl(String connectionString, List<Connection> pool) {
        this.connectionString = connectionString;
        this.connectionPool = pool;
    }

    public static ConnectionPoolImpl create(String connectionString) throws SQLException {
        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection(connectionString));
        }
        return new ConnectionPoolImpl(connectionString, pool);
    }

    private static Connection createConnection(String connectionString) throws SQLException {
        return DriverManager.getConnection(connectionString);
    }

    @Override
    public Connection getConnection() {
        Connection connection = connectionPool
                .remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    @Override
    public String getConnectionString() {
        return this.connectionString;
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }
}
