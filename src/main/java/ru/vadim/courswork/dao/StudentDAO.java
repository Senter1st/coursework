package ru.vadim.courswork.dao;

import ru.vadim.courswork.entities.Student;

import java.sql.SQLException;
import java.util.List;

public class StudentDAO extends AbstractDAO<Student, Integer> {
    public StudentDAO(String connectionString) throws SQLException {
        super(connectionString);
    }

    @Override
    public List<Student> getAll() {
        return null;
    }

    @Override
    public Student getEntityById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public Student update(Student entity) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean create(Student entity) {
        return false;
    }

}
