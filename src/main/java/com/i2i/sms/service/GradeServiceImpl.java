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
     * It displays the students of grade
     * </p>
     * @param gradeId
     *        Id of the grade as String
     * @throws GradeException if unable to get the students of the grade
     * @return Set of StudentGradeResponseDto if present
     */
    public Set<StudentGradeResponseDto> getStudentsOfGrade(String gradeId){
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

    /**
     * <p>
     * It updates the grade count
     * </p>
     * @param grade
     *        grade which contains the standard and section
     */
    public void updateGradeCount(Grade grade) {
        int sectionCount = grade.getSectionCount();
        sectionCount = sectionCount + 1;
        grade.setSectionCount(sectionCount);
        gradeRepo.save(grade);
    }

}
