package ru.vadim.courswork.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.vadim.courswork.dao.SpecialityDAO;
import ru.vadim.courswork.dao.StudentDAO;
import ru.vadim.courswork.entities.Speciality;
import ru.vadim.courswork.entities.Student;

import java.sql.SQLException;
import java.util.List;

public class StudentService {
    private static final Logger LOG = LogManager.getLogger(StudentService.class);
    StudentDAO studentDAO;
    SpecialityDAO specialityDAO;

    public StudentService() {
        studentDAO = new StudentDAO();
        specialityDAO = new SpecialityDAO();
    }

    public List<Student> getAllStudents() {
        List<Student> students;
        try {
            students = studentDAO.getAll();
            return students;
        } catch (SQLException e) {
            LOG.error(e);
        }
        return null;
    }

    public boolean addStudent(Student student) {
        try {
            return studentDAO.create(student);
        } catch (SQLException e) {
            LOG.error(e);
        }

        return false;
    }

    public List<Student> getBySpecName(String specialityName) {
        try {
            Speciality speciality = specialityDAO.getEntityByName(specialityName);
            return studentDAO.getBySpec(speciality.getId());

        } catch (SQLException e) {
            LOG.error(e);
        }
        return null;
    }

    public float getAvgScoreBySpecName(String specialityName) {
        try {
            Speciality speciality = specialityDAO.getEntityByName(specialityName);
            return studentDAO.getAvgScore(speciality.getId());

        } catch (SQLException e) {
            LOG.error(e);
        }
        return -1;
    }
}
