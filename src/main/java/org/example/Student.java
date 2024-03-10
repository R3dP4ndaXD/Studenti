package org.example;

public class Student {
    private final String nume;
    private double medie;
    private String curs;        //ar merge clasa

    public Student(String nume) {
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public void setMedie(double medie) {
        this.medie = medie;
    }

    public double getMedie() {
        return medie;
    }

    public void setCurs(String curs) {
        this.curs = curs;
    }

    public String getCurs() {
        return curs;
    }
}
