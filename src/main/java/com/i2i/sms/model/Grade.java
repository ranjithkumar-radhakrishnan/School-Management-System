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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "grade_id")
    private String gradeId;
    private int standard;
    private Character section;

    @Column(name = "section_count")
    private int sectionCount;

    @OneToMany(mappedBy = "grade", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Student> students;


    public void setStandard(int standard) {
        this.standard = standard;
    }
    public int getStandard() {
        return standard;
    }

    public String getGradeId() {
        return gradeId;
    }
    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public Character getSection() {
        return section;
    }

    public void setSection(Character section) {
        this.section = section;
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

}