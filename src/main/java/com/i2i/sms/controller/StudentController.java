package com.i2i.sms.controller;

import java.util.List;

import com.i2i.sms.exception.StudentException;
import com.i2i.sms.utils.CommonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.i2i.sms.dto.AddressRequestDto;
import com.i2i.sms.dto.AddStudentToClubResponseDto;
import com.i2i.sms.dto.GradeRequestDto;
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
@RequestMapping("/sms/api/v1/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

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
            ResponseEntity<?> responseEntity = validRequest(studentRequestDto);
            if (null == responseEntity) {
                StudentResponseDto studentResponseDto = studentService.createStudentDetail(studentRequestDto);
                if (studentResponseDto != null) {
                    int age = DateUtil.getDifferenceBetweenDateByYears(studentResponseDto.getDob(), null);
                    studentResponseDto.setAge(age);
                    return new ResponseEntity<>(studentResponseDto, HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                }
            }else{
                return responseEntity;
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    /**
     * <p>
     * Validates the Student information such as name, mark, dob
     * Student information are gathered by in the form of StudentRequestDto
     * </p>
     * @param studentRequestDto {@link StudentRequestDto}
     * @return ResponseEntity as null if validated or else send Http bad request
     */
    private ResponseEntity<?> validRequest(StudentRequestDto studentRequestDto) {
        if(CommonUtil.isValidString(studentRequestDto.getName())){
            if(CommonUtil.isValidRangeOfNumber(studentRequestDto.getMark(), 0, 100)){
                if(null != DateUtil.validateDateFormat(studentRequestDto.getDob())){
                    int age = DateUtil.getDifferenceBetweenDateByYears(studentRequestDto.getDob(), null);
                    if(CommonUtil.isValidRangeOfNumber(age, 6, 17)) {
                        return validateAddressRequest(studentRequestDto);
                    }else{
                        return new ResponseEntity<>("Student age not valid for the grade, Invalid age: " + age, HttpStatus.BAD_REQUEST);
                    }
                }else{
                    return new ResponseEntity<>("Invalid Date provided, it should be in format yyyy-MM-dd", HttpStatus.BAD_REQUEST);
                }
            }else{
                return new ResponseEntity<>("Invalid Mark range, it should be between 0 to 100", HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>("Invalid Student Name " + studentRequestDto.getName(), HttpStatus.BAD_REQUEST);
        }
    }
    /**
     * <p>
     * Validates the Address of the student
     * Student information are gathered by in the form of StudentRequestDto
     * </p>
     * @param studentRequestDto {@link StudentRequestDto}
     * @return ResponseEntity as null if validated or else send Http bad request
     */
    private ResponseEntity<?> validateAddressRequest(StudentRequestDto studentRequestDto) {
        AddressRequestDto addressRequestDto = studentRequestDto.getAddress();
        if(CommonUtil.isValidString(addressRequestDto.getStreet())){
            if(CommonUtil.isValidString(addressRequestDto.getCity())){
                if(CommonUtil.isValidString(addressRequestDto.getState())){
                    if(CommonUtil.isValidNumber(Integer.toString(addressRequestDto.getPincode()))){
                        return validateGradeRequest(studentRequestDto.getGrade());
                    }else{
                        return new ResponseEntity<>("Invalid Pincode " + addressRequestDto.getPincode(), HttpStatus.BAD_REQUEST);
                    }
                }else{
                    return new ResponseEntity<>("Invalid State Name " + addressRequestDto.getState(), HttpStatus.BAD_REQUEST);
                }
            }else{
                return new ResponseEntity<>("Invalid City name: " + addressRequestDto.getCity(), HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>("Invalid Street name: " + addressRequestDto.getStreet(), HttpStatus.BAD_REQUEST);
        }
    }
    /**
     * <p>
     * Validates the grade of the student
     * Grade information are gathered by in the form of GradeRequestDto
     * </p>
     * @param gradeRequestDto {@link GradeRequestDto}
     * @return ResponseEntity as null if validated or else send Http bad request
     */
    private ResponseEntity<?> validateGradeRequest(GradeRequestDto gradeRequestDto) {
        if(CommonUtil.isValidRangeOfNumber(gradeRequestDto.getStandard(), 1, 12)){
            if(CommonUtil.isUppercaseCharacter(gradeRequestDto.getSection())){
                if(CommonUtil.isValidNumber(Integer.toString(gradeRequestDto.getSectionCount()))){
                    return null;
                }else{
                    return new ResponseEntity<>("Invalid Section count, it should be integer: " + gradeRequestDto.getSectionCount(), HttpStatus.BAD_REQUEST);
                }
            }else{
                return new ResponseEntity<>("Invalid Section, it should be uppercase character: " + gradeRequestDto.getSection(), HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>("Invalid Standard, it should be range from 1 to 12: " + gradeRequestDto.getStandard(), HttpStatus.BAD_REQUEST);
        }
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
           if (addStudentToClubResponseDto != null) {
               return ResponseEntity.status(HttpStatus.CREATED).body(addStudentToClubResponseDto);
           } else {
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
           }
       }catch (Exception e){
               logger.error(e.getMessage(), e);
       }
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
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
    public ResponseEntity<?> updateStudent(@PathVariable("id") String id, @RequestBody UpdateStudentDto updateStudentDto){
        try {
            StudentResponseDto studentResponseDto = studentService.updateStudent(id, updateStudentDto);
            return new ResponseEntity<>(studentResponseDto, HttpStatus.OK);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}