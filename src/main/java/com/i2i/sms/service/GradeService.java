package com.i2i.sms.service;

import java.util.Set;

import com.i2i.sms.dto.StudentGradeResponseDto;
import com.i2i.sms.model.Grade;

/**
 *This interface has declared methods which used to get, display the grade details
 */
public interface GradeService {
    Set<StudentGradeResponseDto> getStudentsOfGrade(int gradeId);
    Grade getGradeOfStandardAndSection(Grade grade);
}
