package com.i2i.sms.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.i2i.sms.dto.StudentAssignClubDto;
import com.i2i.sms.dto.StudentRequestDto;
import com.i2i.sms.dto.StudentResponseDto;
import com.i2i.sms.service.StudentService;
import com.i2i.sms.utils.DateUtil;
/**
* Implementation to handle student, grade and their address.
*/
@RestController
@RequestMapping("/sms/api/v1/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    private static final Logger logger = LogManager.getLogger(StudentController.class);

   /**
    * <p>
    * Get all the student detail such as name, mark, dob, standard, section and their address.
    * </p>
    */
    @PostMapping("/addStudent")
    public ResponseEntity<HttpStatus> addStudentDetail(@RequestBody StudentRequestDto studentRequestDto) {
        try {
            studentService.createStudentDetail(studentRequestDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
   
   /**
    * <p>
    * Get the rollNo of student to display their detail
    * It displays the grade, section, name, mark, dob, age of the student.
    * </p>
    *
    */
    @GetMapping("/getStudent/{rollNo}")
    public ResponseEntity<StudentResponseDto> getStudentDetailByRollNo(@PathVariable("rollNo") int rollNo) {
         try {
             StudentResponseDto studentResponseDto = studentService.getStudentDetailByRollNo(rollNo);
             int age = DateUtil.getDifferenceBetweenDateByYears(studentResponseDto.getDob(),null);
             studentResponseDto.setAge(age);
             return new ResponseEntity<>(studentResponseDto, HttpStatus.OK);
         }catch (Exception e){
             logger.error(e.getMessage(), e);
         }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

   /**
    * <p>
    * Get the rollNo of student to remove them.
    * </p>
    */
    @DeleteMapping("/removeStudent/{rollNo}")
    public ResponseEntity<HttpStatus> getRollNoToRemove(@PathVariable("rollNo") int rollNo) {
        try {
            studentService.removeStudentByRollNo(rollNo);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

   /**
    * <p>
    * Display the students detail of all grade.
    * </p>
    */
    @GetMapping("/getAllStudent")
    public ResponseEntity<List<StudentResponseDto>> printAllStudentsInformation() {
        List<StudentResponseDto> studentResponseDtos = studentService.showAllStudentDetails();
        if(studentResponseDtos != null){
            studentResponseDtos.forEach(studentResponse -> {
                        int age = DateUtil.getDifferenceBetweenDateByYears(studentResponse.getDob(), null);
                        logger.debug("Calculates the student age " + age + " by their DOB ");
                        studentResponse.setAge(age);
                    });
            logger.info("Student detail retrieved successfully");
            return new ResponseEntity<>(studentResponseDtos, HttpStatus.OK);
        }else{
            logger.info("No Students enrolled yet");
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

   /**
    * <p>
    * It adds student to the clubs
    * </p>
    */
   @PostMapping("/addStudentToClub")
    public ResponseEntity<HttpStatus> addStudentToClub(@RequestBody StudentAssignClubDto studentAssignClubDto) {
       try {
           studentService.assignStudentToClub(studentAssignClubDto);
           return new ResponseEntity<>(HttpStatus.CREATED);
       }catch(Exception e){
           logger.error(e.getMessage(), e);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}