package ru.vadim.courswork.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.vadim.courswork.connection.DBConnectionSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public abstract class AbstractDAO<E, K> {
    static final Logger LOGGER = LogManager.getLogger(AbstractDAO.class.getName());
    private Connection connection;

    AbstractDAO() {
        connection = DBConnectionSingleton.getInstance().getConnection();
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
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException exception) {
                LOGGER.error(exception);
                throw exception;
            }
        }
    }

    public abstract List<E> getAll() throws SQLException;

    public abstract E getEntityById(K id) throws SQLException;

    public abstract E getEntityByName(String name) throws SQLException;

    public abstract E update(E entity) throws SQLException;

    public abstract boolean delete(K id) throws SQLException;

    public abstract boolean create(E entity) throws SQLException;
}
