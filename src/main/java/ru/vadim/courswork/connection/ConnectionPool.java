package ru.vadim.courswork.connection;

import java.sql.Connection;

public interface ConnectionPool {
    Connection getConnection();

    boolean releaseConnection(Connection connection);

    String getConnectionString();

}
