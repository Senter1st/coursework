package ru.vadim.courswork;

import ru.vadim.courswork.connection.DBConnectionSingleton;
import ru.vadim.courswork.service.StudentService;

public class MainApplication {
    public static void main(String[] args) {
        StudentService service = new StudentService();
        System.out.println(service.getAllStudents());
        DBConnectionSingleton.close();
    }
}
