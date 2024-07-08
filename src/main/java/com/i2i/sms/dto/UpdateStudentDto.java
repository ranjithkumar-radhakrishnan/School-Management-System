package com.i2i.sms.dto;

import java.time.LocalDate;

public class UpdateStudentDto {
    private String name;
    private int mark;
    private LocalDate dob;
    private AddressRequestDto address;
    private UpdateGradeDto grade;

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

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public AddressRequestDto getAddress() {
        return address;
    }

    public void setAddress(AddressRequestDto address) {
        this.address = address;
    }

    public UpdateGradeDto getGrade() {
        return grade;
    }

    public void setGrade(UpdateGradeDto grade) {
        this.grade = grade;
    }
}
