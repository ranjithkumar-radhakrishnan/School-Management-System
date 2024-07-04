package com.i2i.sms.dto;

import java.util.Date;
import java.util.Set;

public class StudentAssignClubDto {
    private int rollNo;
    private String name;
    private int mark;
    private Date dob;
    private AddressAssignDto address;
    private GradeAssignDto grade;
    private Set<ClubAssignStudentDto> clubs;

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Set<ClubAssignStudentDto> getClubs() {
        return clubs;
    }

    public void setClubs(Set<ClubAssignStudentDto> clubs) {
        this.clubs = clubs;
    }

    public AddressAssignDto getAddress() {
        return address;
    }

    public void setAddress(AddressAssignDto address) {
        this.address = address;
    }

    public GradeAssignDto getGrade() {
        return grade;
    }

    public void setGrade(GradeAssignDto grade) {
        this.grade = grade;
    }
}
