package ru.vadim.courswork;

import ru.vadim.courswork.connection.DBConnectionSingleton;
import ru.vadim.courswork.entities.Student;
import ru.vadim.courswork.service.StudentService;
import ru.vadim.courswork.util.CsvStudentReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.util.List;

public class MainApplication {
    public static void main(String[] args) {
        try {
            List<Student> students = CsvStudentReader.read("input.csv");
            StudentsXml sdds = new StudentsXml(students);
            JAXBContext jaxbContext = JAXBContext.newInstance(StudentsXml.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.marshal(sdds, System.out);
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
        StudentService service = new StudentService();
        DBConnectionSingleton.close();
    }
}
