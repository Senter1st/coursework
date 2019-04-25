package ru.vadim.courswork.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.vadim.courswork.entities.Speciality;
import ru.vadim.courswork.entities.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class StudentDAO extends AbstractDAO<Student, Integer> {
    private static final Logger LOG = LogManager.getLogger(StudentDAO.class);

    @Override
    public List<Student> getAll() throws SQLException {
        List<Student> lst = new LinkedList<>();
        PreparedStatement statement = null;
        try {
            statement = getPrepareStatement("select * from Student");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                lst.add(createStudent(rs));
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            closePrepareStatement(statement);
        }

        return lst;
    }

    @Override
    public Student getEntityById(Integer id) throws SQLException {
        Student student = null;
        Speciality speciality = new Speciality();
        PreparedStatement statement = null;
        try {
            statement = getPrepareStatement("select s.idStudent, s.name, s.score, s.idSpeciality from Student where idStudent = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                student = createStudent(rs);
            }
        } catch (SQLException e) {
            LOG.error(e);
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
            statement.setInt(3, entity.getSpeciality().getId());
            statement.setInt(3, entity.getId());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(e);
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
            statement.setInt(3, entity.getSpeciality().getId());

            return statement.execute();
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            closePrepareStatement(statement);
        }
        return false;
    }

    public int[] createAll(List<Student> students) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = getPrepareStatement("insert into Student(name, score, idSpeciality) values(?, ?, ?)");
            for (Student student : students) {
                statement.setString(1, student.getName());
                statement.setFloat(2, student.getScore());
                statement.setInt(3, student.getSpeciality().getId());
                statement.addBatch();
            }
            return statement.executeBatch();
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            closePrepareStatement(statement);
        }
        return null;
    }

    private Student createStudent(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        Speciality speciality = new Speciality();
        student.setId(resultSet.getInt(1));
        student.setName(resultSet.getString(2));
        student.setScore(resultSet.getFloat(3));

        speciality.setId(resultSet.getInt(4));
        student.setSpeciality(speciality);
        return student;
    }

    @Override
    public Student getEntityByName(String name) throws SQLException {
        Student student = null;
        Speciality speciality = new Speciality();
        PreparedStatement statement = null;
        try {
            statement = getPrepareStatement("select s.idStudent, s.name, s.score, s.idSpeciality from Student s where name = ?");
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                student = createStudent(rs);
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            closePrepareStatement(statement);
        }
        return student;
    }

    public List<Student> getBySpec(int idSpeciality) throws SQLException {
        List<Student> students = new LinkedList<>();
        PreparedStatement statement = null;
        try {
            statement = getPrepareStatement("select s.idStudent, s.name, s.score, s.idSpeciality from Student s where s.idSpeciality = ?");
            statement.setInt(1, idSpeciality);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                students.add(createStudent(rs));
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            closePrepareStatement(statement);
        }

        return students;
    }

    public float getAvgScore(int idSpeciality) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = getPrepareStatement("select avg(s.score) from Student s where s.idSpeciality = ?");
            statement.setInt(1, idSpeciality);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getFloat(1);
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            closePrepareStatement(statement);
        }

        return -1;
    }
}
