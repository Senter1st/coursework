package ru.vadim.courswork.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.vadim.courswork.entities.Speciality;
import ru.vadim.courswork.entities.Student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvStudentReader {
    private static final String DELIMITER = ";";
    private static final Logger LOG = LogManager.getLogger(CsvStudentReader.class);

    public static List<Student> read(String filepath) {
        List<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(DELIMITER);
                try {
                    students.add(createStudent(values));
                } catch (NumberFormatException e) {
                    LOG.error(e);
                }
            }
        } catch (IOException e) {
            LOG.error(e);
        }
        return students;
    }

    private static Student createStudent(String[] values) throws NumberFormatException {
        Student student = new Student();
        Speciality speciality = new Speciality();
        for (int i = 0; i < values.length; i++) {
            student.setId(Integer.valueOf(values[0]));
            student.setName(values[1]);
            student.setScore(Float.valueOf(values[2]));
            speciality.setId(Integer.valueOf(values[3]));
            student.setSpeciality(speciality);
        }
        return student;
    }
}
