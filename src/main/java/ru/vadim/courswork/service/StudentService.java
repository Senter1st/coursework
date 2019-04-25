package ru.vadim.courswork.service;

import ru.vadim.courswork.dao.SpecialityDAO;
import ru.vadim.courswork.dao.StudentDAO;
import ru.vadim.courswork.entities.Speciality;
import ru.vadim.courswork.entities.Student;

import java.sql.SQLException;
import java.util.List;

public class StudentService {
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
            e.printStackTrace();
        }
        return null;
    }

    public boolean addStudent(Student student) {
        try {
            return studentDAO.create(student);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Student> getBySpecName(String specialityName) {
        try {
            Speciality speciality = specialityDAO.getEntityByName(specialityName);
            return studentDAO.getBySpec(speciality.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public float getAvgScoreBySpecName(String specialityName) {
        try {
            Speciality speciality = specialityDAO.getEntityByName(specialityName);
            return studentDAO.getAvgScore(speciality.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
