package com.i2i.sms.model;

import jakarta.persistence.*;

import java.util.Set;
import java.util.Date;

/**
*This class represents a student which contains their information such as Id, name, date of birth, age and their grade Id.
*/
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private int rollNo;

    @Column(name = "student_name")
    private String name;

    @Column(name = "student_mark")
    private int mark;

    @Column(name = "student_dob")
    private Date dob;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_club",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "club_id")
    )
    private Set<Club> clubs;

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }
    public int getRollNo() {
        return rollNo;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
    public int getMark() {
        return mark;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
    public Date getDob() {
        return dob;
    }
  
    public void setAddress(Address address) {
        this.address = address;
    }
    public Address getAddress() {
        return address;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
    public Grade getGrade() {
        return grade;
    }

    public void setClubs(Set<Club> clubs) {
        this.clubs = clubs;
    }
    public Set<Club> getClubs() {
        return clubs;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\t\tStudent RollNo: ").append(rollNo)
                     .append("\n\t\tStudent Name: ").append(name)
                     .append("\n\t\tStudent mark: ").append(mark)
                     .append("\n\t\tStudent DOB: ").append(dob)
                     .append("\n\t\tAddress: ").append(address);
        return stringBuilder.toString();
    }

}



