package com.i2i.sms.dto;

public class GradeResponseDto {
    private String gradeId;
    private int standard;
    private Character section;
    private int sectionCount;

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }
    public int getStandard() {
        return standard;
    }
    public Character getSection() {
        return section;
    }

    public void setSection(Character section) {
        this.section = section;
    }

    public int getSectionCount() {
        return sectionCount;
    }

    public void setSectionCount(int sectionCount) {
        this.sectionCount = sectionCount;
    }
}
