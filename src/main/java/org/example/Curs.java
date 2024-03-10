package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

class Curs<T extends Student> {
    private String nume;
    private int capacitate;
    private TreeSet<T> studenti = new TreeSet<>(new ComparatorAlfabetic());
    private double ultimaMedie;

    public Curs(String nume, int capacitate) {
        this.nume = nume;
        this.capacitate = capacitate;
    }
    public void adaugaStudent(T student) {
        studenti.add(student);
        ultimaMedie = student.getMedie();
    }

    public double getUltimaMedie() {
        return ultimaMedie;
    }

    public int getCapacitate() {
        return capacitate;
    }

    public TreeSet<T> getStudenti() {
        return studenti;
    }

    public String getNume() {
        return nume;
    }
}
