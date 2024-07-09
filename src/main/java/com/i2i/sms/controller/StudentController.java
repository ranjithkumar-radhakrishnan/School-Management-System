package com.i2i.sms.controller;

import java.util.List;

import com.i2i.sms.exception.StudentException;
import com.i2i.sms.helper.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.i2i.sms.dto.AddStudentToClubResponseDto;
import com.i2i.sms.dto.StudentAssignClubDto;
import com.i2i.sms.dto.StudentRequestDto;
import com.i2i.sms.dto.StudentResponseDto;
import com.i2i.sms.dto.UpdateStudentDto;
import com.i2i.sms.service.StudentService;
import com.i2i.sms.utils.DateUtil;

/**
* Implementation to handle student, grade and their address.
*/
@RestController
@RequestMapping("/v1/students")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private Validator validator;
    private static final Logger logger = LogManager.getLogger(StudentController.class);

   /**
    * <p>
    * Create the new student detail
    * Student information are gathered by in the form of StudentRequestDto
    * </p>
    * @param studentRequestDto {@link StudentRequestDto}
    * @throws StudentException if not able to add student detail
    * @return ResponseEntity of StudentResponseDto if created or else http status not found
    */
    @PostMapping
    public ResponseEntity<?> addStudentDetail(@RequestBody StudentRequestDto studentRequestDto) {
        try {
            //To validate the request api
            ResponseEntity<?> responseEntity = validator.validRequest(studentRequestDto);
            if (null == responseEntity) {
                StudentResponseDto studentResponseDto = studentService.createStudentDetail(studentRequestDto);
                if (studentResponseDto != null) {
                    return new ResponseEntity<>(studentResponseDto, HttpStatus.CREATED);
                } else {
                    //If the grade count gets fulled
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                }
            }else{
                return responseEntity;
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        //return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    /**
    * <p>
    * Get the Id of student to display their detail
    * It displays the name, mark, dob, age, grade, section, address of the student.
    * </p>
    * @param id
    *        Id of the student as String.
    * @throws StudentException if unable to get student detail of particular rollNo.
    * @return ResponseEntity of StudentResponseDto if the rollNo of student exist or http status not found
    */
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> getStudentDetailByRollNo(@PathVariable("id") String id) {
         try {
             StudentResponseDto studentResponseDto = studentService.getStudentDetailByRollNo(id);
             return new ResponseEntity<>(studentResponseDto, HttpStatus.OK);
         }catch (Exception e){
             logger.error(e.getMessage(), e);
         }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

   /**
    * <p>
    * Get the id of student to remove them.
    * </p>
    * @param id
    *        Id of the student as String.
    * @throws StudentException if rollNo of student not exist.
    * @return ResponseEntity of http status No_Content if deleted or else http status NOT_FOUND
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> getRollNoToRemove(@PathVariable("id") String id) {
        try {
            studentService.removeStudentByRollNo(id);
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
    * @return ResponseEntity of List of StudentResponseDto if present or else http status NOT_FOUND
    */
    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> printAllStudentsInformation() {
        try {
            List<StudentResponseDto> studentResponseDtos = studentService.showAllStudentDetails();
            if (studentResponseDtos != null) {
                logger.info("Student detail retrieved successfully");
                return new ResponseEntity<>(studentResponseDtos, HttpStatus.OK);
            } else {
                logger.info("No Students enrolled yet");
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

   /**
    * <p>
    * It adds student to the clubs
    * </p>
    * @param id
    *        Id of the student as String
    * @param studentAssignClubDto {@link StudentAssignClubDto}
    * @throws StudentException if rollNo of student not exist.
    * @return ResponseEntity of AddStudentToClubResponseDto if student added to club or else http status NOT_FOUND
    */
   @PostMapping("/{id}/clubs")
   public ResponseEntity<AddStudentToClubResponseDto> addStudentToClub(@PathVariable("id") String id, @RequestBody StudentAssignClubDto studentAssignClubDto) {
       try {
           AddStudentToClubResponseDto addStudentToClubResponseDto = studentService.assignStudentToClub(id, studentAssignClubDto);
           //If request api is empty list
           if (addStudentToClubResponseDto == null) {
               return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
               //If request api contain club Id which does not exist
           } else if (!addStudentToClubResponseDto.getNoClubsExist().isEmpty() &&
                   addStudentToClubResponseDto.getAddedClubs() == null &&
                   addStudentToClubResponseDto.getAlreadyAddedClubs().isEmpty()) {
               return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
           } else {
               return new ResponseEntity<>(addStudentToClubResponseDto, HttpStatus.OK);
           }
       } catch (Exception e) {
           logger.error(e.getMessage(), e);
       }
       return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
   }

    /**
     * <p>
     * It updates the student
     * </p>
     * @param id
     *        Id of the student as String
     * @param updateStudentDto {@link UpdateStudentDto}
     * @throws StudentException if rollNo of student not exist.
     * @return ResponseEntity of StudentResponseDto if student updated or else http status NOT_FOUND
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable("id") String id, @RequestBody UpdateStudentDto updateStudentDto) {
        try {
            ResponseEntity<?> responseEntity = validator.validUpdateRequest(updateStudentDto);
            if (null == responseEntity) {
                StudentResponseDto studentResponseDto = studentService.updateStudent(id, updateStudentDto);
                int age = DateUtil.getDifferenceBetweenDateByYears(studentResponseDto.getDob(), null);
                studentResponseDto.setAge(age);
                return new ResponseEntity<>(studentResponseDto, HttpStatus.OK);
            } else {
                return responseEntity;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return new ResponseEntity<>("No student enrolled with this id " + id, HttpStatus.NOT_FOUND);
    }
}