package ru.vadim.courswork.dao;

import ru.vadim.courswork.entities.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class StudentDAO extends AbstractDAO<Student, Integer> {
    @Override
    public List<Student> getAll() throws SQLException {
        List<Student> lst = new LinkedList<>();
        PreparedStatement statement = null;
        try {
            statement = getPrepareStatement("select * from Student");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt(1));
                student.setName(rs.getString(2));
                student.setScore(rs.getFloat(3));
                student.setIdSpecialty(rs.getInt(4));
                lst.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(statement);
        }

        return lst;
    }

    @Override
    public Student getEntityById(Integer id) throws SQLException {
        Student student = null;
        PreparedStatement statement = null;
        try {
            statement = getPrepareStatement("select * from Student where idStudent = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                student.setId(rs.getInt(1));
                student.setName(rs.getString(2));
                student.setScore(rs.getFloat(3));
                student.setIdSpecialty(rs.getInt(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(statement);
        }
        return student;
    }

    @Override
    public Student update(Student entity) throws SQLException {
        Student student = null;
        PreparedStatement statement = null;
        try {
            statement = getPrepareStatement("update Student set name = ?, score = ?, idSpesiality = ? where id = ?");
            statement.setString(1, entity.getName());
            statement.setFloat(2, entity.getScore());
            statement.setInt(3, entity.getIdSpecialty());
            statement.setInt(3, entity.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(statement);
        }
        return student;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = getPrepareStatement("delete from Student where idStudent = ?");
            statement.setInt(1, id);
            return statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            closePrepareStatement(statement);
        }
        return false;
    }

    @Override
    public boolean create(Student entity) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = getPrepareStatement("insert into Student(name, score, idSpeciality) values(?, ?, ?)");
            statement.setString(1, entity.getName());
            statement.setFloat(2, entity.getScore());
            statement.setInt(3, entity.getIdSpecialty());

            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(statement);
        }
        return false;
    }
}
