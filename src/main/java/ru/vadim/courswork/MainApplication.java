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
                        if (command[1].equals("/help")) {
                            help(command[0]);
                            break;
                        }
                        List<Student> allStudents = service.getAllStudents();
                        service.exportToXml(allStudents, command[1]);
                    } else help(command[0]);
                    break;
                case "import":
                    if (command.length == 2) {
                        if (command[1].equals("/help")) {
                            help(command[0]);
                            break;
                        }
                        List<Student> students = CsvStudentReader.read(command[1]);
                        if (students != null) {
                            int created = service.createAll(students);
                            System.out.printf("%d created\n", created);
                        }
                    }
                    break;
                case "getStudents":
                    if (command.length > 1)
                        if (command[1].equals("/help")) {
                            help(command[0]);
                            break;
                        }
                    List<Student> studentList = service.getAllStudents();
                    if (studentList != null) {
                        studentList.forEach(spec -> System.out.println(spec.toString()));
                    }
                    break;
                case "addStudent":
                    if (command.length > 1) {
                        if (command[1].equals("/help")) {
                            help(command[0]);
                            break;
                        }
                    }
                    if (command.length == 4) {
                        Student student = new Student();
                        Speciality speciality = new Speciality();
                        student.setName(command[1]);
                        student.setScore(Float.valueOf(command[2]));
                        speciality.setId(Integer.valueOf(command[3]));
                        student.setSpeciality(speciality);
                        service.addStudent(student);
                        System.out.println("Student has been created");
                    } else help(command[0]);
                    break;
                case "getSpecialities":
                    if (command.length > 1)
                        if (command[1].equals("/help")) {
                            help(command[0]);
                            break;
                        }
                    List<Speciality> specialities = service.getSpecialities();
                    if (specialities != null) {
                        specialities.forEach(spec -> System.out.println(spec.toString()));
                    }
                    break;
                case "addSpeciality":
                    if (command.length > 1)
                        if (command[1].equals("/help")) {
                            help(command[0]);
                            break;
                        }
                    if (command.length == 3) {
                        Speciality speciality = new Speciality();
                        speciality.setName(command[1]);
                        speciality.setDescription(command[2]);
                        System.out.println("Speciality has been created");
                    } else help(command[0]);
                    break;

                case "getBySpec":
                    if (command.length > 1)
                        if (command[1].equals("/help")) {
                            help(command[0]);
                            break;
                        }
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
                    } else help(command[0]);
                    break;

                case "getAvgScore":
                    if (command.length > 1)
                        if (command[1].equals("/help")) {
                            help(command[0]);
                            break;
                        }
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
                    } else help(command[0]);
                    break;
            }
        }
        DBConnectionSingleton.close();
    }

    public static void help(String command) {
        switch (command) {
            case "exportall":
                System.out.println("---");
                System.out.println("export all data from database to XML");
                System.out.println("Usage: exportall <output filepath>");
                System.out.println("---");
                break;
            case "import":
                System.out.println("---");
                System.out.println("Imports all data from a CSV file");
                System.out.println("Usage: import <import filepath>");
                System.out.println("---");
                break;
            case "getStudents":
                System.out.println("---");
                System.out.println("Print list of students");
                System.out.println("Usage: getStudents");
                System.out.println("---");
                break;
            case "addStudent":
                System.out.println("---");
                System.out.println("Add  new student");
                System.out.println("Usage: addStudent <name> <score> <speciality id>");
                System.out.println("---");
                break;
            case "getSpecialities":
                System.out.println("---");
                System.out.println("Print list of specialities");
                System.out.println("Usage: getSpecialities");
                System.out.println("---");
                break;
            case "addSpeciality":
                System.out.println("---");
                System.out.println("Add new speciality");
                System.out.println("Usage: addSpeciality <name> <description>");
                System.out.println("---");
                break;
            case "getBySpec":
                System.out.println("---");
                System.out.println("Print list of students by speciality name");
                System.out.println("Usage: getBySpec <name>");
                System.out.println("---");
                break;
            case "getAvgScore":
                System.out.println("---");
                System.out.println("Print avg score by speciality name");
                System.out.println("Usage: getAvgScore <name>");
                System.out.println("---");
                break;
        }
    }
}
