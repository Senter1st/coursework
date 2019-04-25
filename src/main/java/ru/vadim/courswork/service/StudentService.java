package ru.vadim.courswork.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.vadim.courswork.StudentsXml;
import ru.vadim.courswork.dao.SpecialityDAO;
import ru.vadim.courswork.dao.StudentDAO;
import ru.vadim.courswork.entities.Speciality;
import ru.vadim.courswork.entities.Student;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.IntStream;

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
            if (speciality != null) {
                return studentDAO.getBySpec(speciality.getId());
            }
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

    public int createAll(List<Student> students) {
        try {
            int[] created = studentDAO.createAll(students);
            return IntStream.of(created).sum();
        } catch (SQLException e) {
            LOG.error(e);
        }
        return -1;
    }

    public List<Speciality> getSpecialities() {
        try {
            return specialityDAO.getAll();
        } catch (SQLException e) {
            LOG.error(e);
        }
        return null;
    }

    public boolean addSpecialities(Speciality speciality) {
        try {
            return specialityDAO.create(speciality);
        } catch (SQLException e) {
            LOG.error(e);
        }
        return false;
    }

    public void exportToXml(List<Student> list, String output) {
        try {
            File file = new File(output);
            StudentsXml studentsXml = new StudentsXml(list);
            JAXBContext jaxbContext = JAXBContext.newInstance(StudentsXml.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(studentsXml, file);
        } catch (JAXBException e) {
            LOG.error(e);
        }
    }
}
