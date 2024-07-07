package com.i2i.sms.dto;

public class UpdateGradeDto {
    private int standard;
    private Character section;

    public int getStandard() {
        return standard;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }

    public Character getSection() {
        return section;
    }

    public void setSection(Character section) {
        this.section = section;
    }
}
