package com.i2i.sms.dto;

import com.i2i.sms.model.Club;

import java.util.Date;
import java.util.Set;

public class StudentRequestDto {
    private String name;
    private int mark;
    private Date dob;
    private AddressRequestDto address;
    private GradeRequestDto grade;

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

    public AddressRequestDto getAddress() {
        return address;
    }

    public void setAddress(AddressRequestDto address) {
        this.address = address;
    }

    public GradeRequestDto getGrade() {
        return grade;
    }

    public void setGrade(GradeRequestDto grade) {
        this.grade = grade;
    }
}
