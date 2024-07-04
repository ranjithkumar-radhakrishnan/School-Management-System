package com.i2i.sms.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import com.i2i.sms.dto.StudentGradeResponseDto;
import com.i2i.sms.exception.GradeException;
import com.i2i.sms.helper.Mapper;
import com.i2i.sms.model.Grade;
import com.i2i.sms.model.Student;
import com.i2i.sms.repositry.GradeRepo;

/**
 *This class implemented to collect, search the grade details.
 */
@Service
public class GradeServiceImpl implements GradeService{
    @Autowired
    private GradeRepo gradeRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Mapper mapper;

    /**
     * <p>
     * Get the students of given grade id as StudentGradeResponseDto
     * </p>
     * @param gradeId
     *        grade Id as int
     * @return Set of StudentGradeResponseDto if given grade present
     */
    public Set<StudentGradeResponseDto> getStudentsOfGrade(int gradeId){
        Grade grade = gradeRepo.findById(gradeId)
                .orElseThrow(() -> new GradeException("No grade found with this Id " + gradeId));
        Set<Student> students = grade.getStudents();
        return mapper.covertStudentOfGradeEntityToDto(students);
    }

    /**
     * <p>
     * Get the grade detail
     * </p>
     * @param grade
     *        grade which contains the standard and section
     * @return Grade detail if present or else null
     */
    public Grade getGradeOfStandardAndSection(Grade grade){
        return gradeRepo.findByStandardAndSection(grade.getStandard(), grade.getSection());
    }
}
