package ru.vadim.courswork;

import ru.vadim.courswork.connection.DBConnectionSingleton;
import ru.vadim.courswork.entities.Student;
import ru.vadim.courswork.service.StudentService;
import ru.vadim.courswork.util.CsvStudentReader;

import java.io.IOException;
import java.util.List;

public class MainApplication {
    public static void main(String[] args) {
        try {
            List<Student> students = CsvStudentReader.read("input.csv");
            System.out.println(students);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StudentService service = new StudentService();
        DBConnectionSingleton.close();
    }
}
