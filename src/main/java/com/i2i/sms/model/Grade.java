package com.i2i.sms.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
*This class represents a Grade with basic information such as standard, section, List of student detail, sectionCount and unique grade Id.
*/
@Entity
@Table(name = "grade")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private int gradeId;
    private int standard;
    private Character section;

    @Column(name = "section_count")
    private int sectionCount;

    @OneToMany(mappedBy = "grade", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Student> students;

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