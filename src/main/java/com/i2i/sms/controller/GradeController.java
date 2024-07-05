package com.i2i.sms.controller;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.i2i.sms.dto.StudentGradeResponseDto;
import com.i2i.sms.service.GradeService;
import com.i2i.sms.utils.DateUtil;

@RestController
@RequestMapping("/sms/api/v1/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    private static final Logger logger = LogManager.getLogger(GradeController.class);

    /**
     * <p>
     * It displays the students of grade
     * </p>
     */
    @GetMapping("/{gradeId}")
    public ResponseEntity<Set<StudentGradeResponseDto>> getStudentsOfGrade(@PathVariable("gradeId") int gradeId){
        try {
            Set<StudentGradeResponseDto> studentGradeResponseDtos = gradeService.getStudentsOfGrade(gradeId);
            studentGradeResponseDtos.forEach(studentGradeResponse -> {
                int age = DateUtil.getDifferenceBetweenDateByYears(studentGradeResponse.getDob(), null);
                logger.debug("Calculates the student age " + age + " by their DOB ");
                studentGradeResponse.setAge(age);
            });
            logger.info("Students retrieved successfully of grade Id {} ", gradeId);
            return new ResponseEntity<>(studentGradeResponseDtos, HttpStatus.OK);
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
