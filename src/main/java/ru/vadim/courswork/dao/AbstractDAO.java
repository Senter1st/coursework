package ru.vadim.courswork.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public abstract class AbstractDAO<E, K> {
    static final Logger LOGGER = LogManager.getLogger(AbstractDAO.class.getName());
    private Connection connection;

    public AbstractDAO(String connectionString) throws SQLException {
        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException exception) {
            LOGGER.error(exception.getMessage());
            throw exception;
        }
    }

    PreparedStatement getPrepareStatement(String sql) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
        } catch (SQLException exception) {
            LOGGER.error(exception);
            throw exception;
        }

        return statement;
    }

    void closePrepareStatement(PreparedStatement ps) throws SQLException {
        System.out.println(1111);
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException exception) {
                LOGGER.error(exception);
                throw exception;
            }
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException exception) {
            LOGGER.error(exception);
        }
    }

    public abstract List<E> getAll() throws SQLException;

    public abstract E getEntityById(K id) throws SQLException;

    public abstract E update(E entity) throws SQLException;

    public abstract boolean delete(K id) throws SQLException;

    public abstract boolean create(E entity) throws SQLException;
}
