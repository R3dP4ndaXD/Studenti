package org.example;

import java.io.*;
import java.util.*;

public class Secretariat {
    HashMap<String, Student> studenti = new HashMap<>();
    TreeMap<Student, List<String>> studenti_ierarhie = new TreeMap<>(new ComparatorMedie());
    HashMap<String, Curs<StudentLicenta>> cursuriLicenta = new HashMap<>();
    HashMap<String, Curs<StudentMaster>> cursuriMaster = new HashMap<>();

    public void adauga_student(String program_de_studii, String nume_student) throws ExceptieDuplicat {
        if (studenti.containsKey(nume_student)) {
            throw new ExceptieDuplicat("Student duplicat: " + nume_student);
        }
        Student student;
        if (program_de_studii.equals("licenta")) {
            student = new StudentLicenta(nume_student);
        } else {
            student = new StudentMaster(nume_student);
        }
        studenti.put(nume_student, student);
    }

    public void adauga_curs(String program_de_studii, String nume_curs, int capacitatea_maxima) {
        if (program_de_studii.equals("licenta")) {
            Curs<StudentLicenta> curs = new Curs<>(nume_curs, capacitatea_maxima);
            cursuriLicenta.put(nume_curs, curs);
        } else {
            Curs<StudentMaster> curs = new Curs<>(nume_curs, capacitatea_maxima);
            cursuriMaster.put(nume_curs, curs);
        }
    }

    public void contestatie(String nume_student, double noua_medie) {
        Student student= studenti.get(nume_student);
        if (student != null) {
            List<String> optiuni = studenti_ierarhie.remove(student);
            student.setMedie(noua_medie);
            studenti_ierarhie.put(student, optiuni);
        }
    }

    public void citeste_mediile(File directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isFile() && file.getName().startsWith("note")) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line = br.readLine();
                    while (line != null) {
                        String[] pair = line.split(" - ");
                        Student s = studenti.get(pair[0]);
                        s.setMedie(Double.parseDouble(pair[1]));
                        studenti_ierarhie.put(s, null);
                        line = br.readLine();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void posteaza_mediile(File file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write("***\n");
            for (Map.Entry<Student, List<String>> entry : studenti_ierarhie.entrySet()) {
                bw.write(entry.getKey().getNume() + " - " + entry.getKey().getMedie() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void adauga_preferinte(String nume, List<String> preferinte) {
        Student student = studenti.get(nume);
        if (student != null) {
            studenti_ierarhie.put(student, preferinte);
        }
    }

    public void repartizeaza() {
        for (Map.Entry<Student, List<String>> entry : studenti_ierarhie.entrySet()) {
            if (entry.getKey() instanceof StudentLicenta) {
                StudentLicenta student = (StudentLicenta) entry.getKey();
                for (String optiune: entry.getValue()) {
                    Curs<StudentLicenta> curs = cursuriLicenta.get(optiune);
                    if (curs.getStudenti().size() < curs.getCapacitate() || curs.getUltimaMedie() == student.getMedie()) {
                        curs.adaugaStudent(student);
                        student.setCurs(optiune);
                        break;
                    }
                }
            } else if (entry.getKey() instanceof StudentMaster) {       //HashMap can accommodate one null key and multiple null values.
                StudentMaster student = (StudentMaster) entry.getKey();
                for (String optiune: entry.getValue()) {
                    Curs<StudentMaster> curs = cursuriMaster.get(optiune);
                    if(curs.getStudenti().size() < curs.getCapacitate() || curs.getUltimaMedie() == student.getMedie()) {
                        curs.adaugaStudent(student);
                        student.setCurs(optiune);
                        break;
                    }
                }
            }
        }
    }
    public void posteaza_curs(String nume, File file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write("***\n");
            if (cursuriLicenta.containsKey(nume)) {
                Curs<StudentLicenta> curs = cursuriLicenta.get(nume);
                bw.write(curs.getNume() + " (" + curs.getCapacitate() + ")\n");
                for (Student s: curs.getStudenti()) {
                    bw.write(s.getNume() + " - " + s.getMedie() + "\n");
                }
            } else if (cursuriMaster.containsKey(nume)){
                Curs<StudentMaster> curs = cursuriMaster.get(nume);
                bw.write(curs.getNume() + " (" + curs.getCapacitate() + ")\n");
                for (Student s: curs.getStudenti()) {
                    bw.write(s.getNume() + " - " + s.getMedie() + "\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void posteaza_student(String nume, File file) {
        Student student = studenti.get(nume);
        if (student == null) {
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write("***\n");
            if (student instanceof StudentLicenta) {
                bw.write("Student Licenta: ");
            } else {
                bw.write("Student Master: ");
            }
            bw.write(student.getNume() + " - " + student.getMedie() + " - " + student.getCurs() + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
