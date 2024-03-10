package org.example;

import java.util.Comparator;

class ComparatorMedie implements Comparator<Student> {
    public int compare(Student s1, Student s2) {
        if (s1.getMedie() > s2.getMedie())
            return -1;
        else if (s1.getMedie() < s2.getMedie())
            return 1;
        else
            return s1.getNume().compareTo(s2.getNume());
    }
}
