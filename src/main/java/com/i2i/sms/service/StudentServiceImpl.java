package com.i2i.sms.service;

import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.i2i.sms.dto.StudentAssignClubDto;
import com.i2i.sms.dto.StudentRequestDto;
import com.i2i.sms.dto.StudentResponseDto;
import com.i2i.sms.exception.StudentException;
import com.i2i.sms.helper.Mapper;
import com.i2i.sms.model.Club;
import com.i2i.sms.model.Grade;
import com.i2i.sms.model.Student;
import com.i2i.sms.repositry.StudentRepo;

/**
*This class implemented to store, collect, search and remove the student details.
*/
@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private ClubService clubService;
    @Autowired
    private Mapper mapper;

    private static final Logger logger = LogManager.getLogger(StudentServiceImpl.class);
   /**
    * <p>
    * Creates student detail by getting their information.
    * </p>
    *
    * @param studentRequestDto
    *        the DTO which contains the student, address, grade, club detail except their primary key
    * @throws StudentException if not able to add student detail
    */
    public void createStudentDetail(StudentRequestDto studentRequestDto) {
        Student student = mapper.convertDtoToEntity(studentRequestDto);
        try {
            Grade grade = gradeService.getGradeOfStandardAndSection(student.getGrade());
            if (grade != null) {
                logger.debug("Grade already present {}", grade);
                student.setGrade(grade);
                studentRepo.save(student);
                logger.info("Student successfully added whose rollNo {}", student.getRollNo());
            } else {
                logger.debug("No grade available {}", studentRequestDto.getGrade());
                studentRepo.save(student);
                logger.info("Student successfully added whose rollNo {}", student.getRollNo());
            }
        }catch(Exception e){
            throw new StudentException("Unable to add the student detail");
        }
    }

   /**
    * <p>
    * It displays the student detail of all grades.
    * </p>
    *
    * @throws StudentException if unable to get the student detail of all grades.
    * @return List of StudentResponseDto if present or else null
    */
    public List<StudentResponseDto> showAllStudentDetails() {
        if(!studentRepo.findAll().isEmpty()) {
            logger.debug("It has students, not empty");
            return mapper.covertStudentEntityToDto(studentRepo.findAll());
        }else{
            return null;
        }
    }

   /**
    * <p>
    * Get student detail by rollNo.
    * </p>
    *
    * @param rollNo
    *        RollNo of student passed as int.
    * @throws StudentException if unable to get student detail of particular rollNo.
    * @return StudentResponseDto if the rollNo of student exist or else throws Exception
    */
    public StudentResponseDto getStudentDetailByRollNo(int rollNo) {
        Student student = studentRepo.findById(rollNo).orElseThrow(() -> new StudentException("No Student enrolled with id "+rollNo));
        logger.debug("Student detail retrieved if enrolled or else throws exception {}", student);
        return  mapper.convertEntityToDto(student);
    }

   /**
    *
    * <p>
    * Remove student detail by rollNo.
    * </p>
    * @param rollNo
    *        RollNo of student as integer.
    * @throws StudentException if rollNo of student not exist.
    */
    public void removeStudentByRollNo(int rollNo) {
        studentRepo.findById(rollNo)
                .orElseThrow(() -> new StudentException("No Student enrolled with id "+rollNo));
        studentRepo.deleteById(rollNo);
        logger.info("Student detail successfully removed whose rollNo {}", rollNo);
    }

   /**
    * <p>
    * It adds student to the clubs
    * </p>
    *
    * @param studentAssignClubDto
    *        the DTO which contains the Student detail including rollNo
    * @throws StudentException if unable to add the student to clubs
    */
    public void assignStudentToClub(StudentAssignClubDto studentAssignClubDto) {
        Student student = studentRepo.findById(studentAssignClubDto.getRollNo()).orElse(new Student());
        logger.debug("Already enrolled student whose rollNo " + student.getRollNo());
        Set<Club> clubs =clubService.getClubs(studentAssignClubDto.getClubs());
        student.setClubs(clubs);

        studentRepo.save(student);
        logger.info("Student assigned to club successfully whose rollNo {}",student.getRollNo());
    }
}