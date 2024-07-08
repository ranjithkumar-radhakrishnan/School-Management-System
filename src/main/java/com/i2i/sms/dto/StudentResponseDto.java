package com.i2i.sms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Date;

public class StudentResponseDto {
    private String id;
    private String name;
    private int mark;
    private int age;
    private LocalDate dob;
    private AddressResponseDto address;
    private GradeResponseDto grade;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
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
