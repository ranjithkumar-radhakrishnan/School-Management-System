package com.i2i.sms.model;

import java.util.Set;
import java.util.HashSet;
import java.util.Date;

/**
*This class represents a student which contains their information such as Id, name, date of birth, age and their grade Id.
*/
public class Student {
 
    private int rollNo;
    private String name;
    private int mark;
    private Date dob;
    private Address address;
    private Grade grade;
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



