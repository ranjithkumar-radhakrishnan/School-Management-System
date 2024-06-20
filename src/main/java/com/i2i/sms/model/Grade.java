package com.i2i.sms.model;

import java.util.Set;
import java.util.HashSet;

/**
*This class represents a Grade with basic information such as standard, section, List of student detail, sectionCount and unique grade Id.
*/
public class Grade { 

    private int gradeId;
    private int standard;
    private char section;
    private Set<Student> students;
    private int sectionCount = 3;

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }
    public int getGradeId() {
        return gradeId;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }
    public int getStandard() {
        return standard;
    }

    public void setSection(char section) {
        this.section = section;
    }
    public char getSection() {
        return section;
    }
  
    public void setStudents(Set<Student> students) {
        this.students = students;
    }
    public Set<Student> getStudents() {
        return students;
    }

    public void setSectionCount(int sectionCount) {
        this.sectionCount = sectionCount;
    }
    public int getSectionCount() {
        return sectionCount;
    }
    
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\t\tstandard: ").append(standard)
                     .append("\n\t\tsection: ").append(section);
        return stringBuilder.toString();
    }
}