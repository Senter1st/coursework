package ru.vadim.courswork;

import ru.vadim.courswork.entities.Student;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "students")
public class StudentsXml extends ArrayList<Student> {
    List<Student> list;

    public StudentsXml(List<Student> list) {
        this.list = list;
    }

    public StudentsXml() {
    }

    @XmlElement(name = "student", required = true)
    public List<Student> getStudents() {
        return list;
    }
}
