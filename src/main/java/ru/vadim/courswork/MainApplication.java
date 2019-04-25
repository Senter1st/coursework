package ru.vadim.courswork;

import ru.vadim.courswork.dao.StudentDAO;
import ru.vadim.courswork.entities.Student;

import java.sql.SQLException;
import java.util.List;

public class MainApplication {
    public static void main(String[] args) {
        try {
            StudentDAO studentDAO = new StudentDAO();
            List<Student> all = studentDAO.getAll();
            System.out.println(all);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
