package ru.vadim.courswork.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "student")
public class Student {
    private int id;
    private String name;
    private Speciality speciality;
    private float score;

    public int getId() {
        return id;
    }

    @XmlElement(name = "id", required = true)
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @XmlElement(name = "name", required = true)
    public void setName(String name) {
        this.name = name;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    @XmlElement(name = "speciality", required = true)
    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public float getScore() {
        return score;
    }

    @XmlElement(name = "score", required = true)
    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return String.format("%d\t%s\t%s\t%d", id, name, score, speciality.getId());
    }
}
