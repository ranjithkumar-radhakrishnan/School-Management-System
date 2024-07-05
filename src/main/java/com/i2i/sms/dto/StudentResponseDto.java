package com.i2i.sms.dto;

import com.i2i.sms.model.Address;

import java.util.Date;

public class StudentResponseDto {
    private int rollNo;
    private String name;
    private int mark;
    private int age;
    private Date dob;
    private AddressResponseDto address;
    private GradeResponseDto grade;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getDob() {
        return dob;
    }
    public void setDob(Date dob) {
        this.dob = dob;
    }

    public AddressResponseDto getAddress() {
        return address;
    }

    public void setAddress(AddressResponseDto address) {
        this.address = address;
    }

    public GradeResponseDto getGrade() {
        return grade;
    }

    public void setGrade(GradeResponseDto grade) {
        this.grade = grade;
    }
}
