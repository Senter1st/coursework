package ru.vadim.courswork;

import ru.vadim.courswork.connection.ConnectionPool;

import java.sql.SQLException;

public class MainApplication {
    public static void main(String[] args) {
        try {
            ConnectionPool connectionPool = ConnectionPool.create("jdbc:sqlite:students.db");
            connectionPool.clearPool();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
