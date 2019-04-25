package ru.vadim.courswork;

import ru.vadim.courswork.connection.DBConnectionSingleton;
import ru.vadim.courswork.entities.Speciality;
import ru.vadim.courswork.entities.Student;
import ru.vadim.courswork.service.StudentService;
import ru.vadim.courswork.util.CsvStudentReader;

import java.util.List;
import java.util.Scanner;

public class MainApplication {
    public static void main(String[] args) {
        StudentService service = new StudentService();
        Scanner in = new Scanner(System.in);
        while (true) {
            String userInput = in.nextLine();
            String[] command = userInput.split(" ");
            if (command[0].equals("exit")) break;


            switch (command[0]) {
                case "exportall":
                    if (command.length == 2) {
                        List<Student> allStudents = service.getAllStudents();
                        service.exportToXml(allStudents, command[1]);
                    }
                    break;
                case "import":
                    if (command.length == 2) {
                        List<Student> students = CsvStudentReader.read(command[1]);
                        if (students != null) {
                            int created = service.createAll(students);
                            System.out.printf("%d created\n", created);
                        }
                    }
                    break;
                case "getStudents":
                    List<Student> studentList = service.getAllStudents();
                    if (studentList != null) {
                        studentList.forEach(spec -> System.out.println(spec.toString()));
                    }
                    break;
                case "addStudent":
                    // <name> <score> <speciality id>
                    // getSpecialities
                    if (command.length == 4) {
                        Student student = new Student();
                        Speciality speciality = new Speciality();
                        student.setName(command[1]);
                        student.setScore(Float.valueOf(command[2]));
                        speciality.setId(Integer.valueOf(command[3]));
                        student.setSpeciality(speciality);
                        service.addStudent(student);
                    }
                    break;
                case "getSpecialities":
                    List<Speciality> specialities = service.getSpecialities();
                    if (specialities != null) {
                        specialities.forEach(spec -> System.out.println(spec.toString()));
                    }
                    break;
                case "addSpecialities":
                    // <name> <description>
                    if (command.length == 3) {
                        Speciality speciality = new Speciality();
                        speciality.setName(command[1]);
                        speciality.setDescription(command[2]);
                        if (service.addSpecialities(speciality)) {
                            System.out.println("Speciality has been created");
                        }
                    }
                    break;

                case "getBySpec":
                    // <name>
                    if (command.length >= 2) {
                        StringBuilder builder = new StringBuilder();
                        for (int i = 1; i < command.length; i++) {
                            builder.append(command[i]);
                            if (i != command.length - 1) builder.append(" ");
                        }
                        List<Student> students = service.getBySpecName(builder.toString());
                        if (students != null) {
                            students.forEach(spec -> System.out.println(spec.toString()));
                        }
                    }
                    break;

                case "getAvgScore":
                    // <name>
                    if (command.length >= 2) {
                        StringBuilder builder = new StringBuilder();
                        for (int i = 1; i < command.length; i++) {
                            builder.append(command[i]);
                            if (i != command.length - 1) builder.append(" ");
                        }
                        float score = service.getAvgScoreBySpecName(builder.toString());
                        if (score != -1) {
                            System.out.printf("Score is %f\n", score);
                        }
                    }
                    break;
            }
        }
        DBConnectionSingleton.close();
    }
}
