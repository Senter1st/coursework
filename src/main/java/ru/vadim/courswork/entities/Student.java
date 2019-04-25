package ru.vadim.courswork.entities;

public class Student {
    int id;
    String name;
    int idSpecialty;
    float score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdSpecialty() {
        return idSpecialty;
    }

    public void setIdSpecialty(int idSpecialty) {
        this.idSpecialty = idSpecialty;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return String.format("Student{id=%d, name='%s', idSpecialty=%d, score=%s}", id, name, idSpecialty, score);
    }
}
