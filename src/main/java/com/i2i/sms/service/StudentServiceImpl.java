package com.i2i.sms.service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.i2i.sms.utils.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.i2i.sms.dto.AddressRequestDto;
import com.i2i.sms.dto.AddStudentToClubResponseDto;
import com.i2i.sms.dto.ClubResponseDto;
import com.i2i.sms.dto.StudentAssignClubDto;
import com.i2i.sms.dto.StudentRequestDto;
import com.i2i.sms.dto.StudentResponseDto;
import com.i2i.sms.dto.UpdateStudentDto;
import com.i2i.sms.exception.StudentException;
import com.i2i.sms.helper.Mapper;
import com.i2i.sms.model.Address;
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
     * Create the new student detail
     * Student information are gathered by in the form of StudentRequestDto
     * </p>
     * @param studentRequestDto {@link StudentRequestDto}
     * @throws StudentException if not able to add student detail
     * @return StudentResponseDto if created
     */
    public StudentResponseDto createStudentDetail(StudentRequestDto studentRequestDto) {
        Student student = mapper.convertDtoToEntity(studentRequestDto);
        try {
            //To check if grade already exist
            Grade grade = gradeService.getGradeOfStandardAndSection(student.getGrade());
            StudentResponseDto studentResponseDto = null;
            int sectionCount = 0;
            if (grade != null) {
                logger.debug("Grade already present {}", grade);
                sectionCount = grade.getSectionCount();
                if(sectionCount != 0) {
                    sectionCount = sectionCount-1;
                    grade.setSectionCount(sectionCount);
                    student.setGrade(grade);
                    studentResponseDto = mapper.convertEntityToDto(studentRepo.save(student));
                    logger.info("Student successfully added whose rollNo {}", student.getId());
                    return studentResponseDto;
                }else {
                    return null;
                }
            } else {
                logger.debug("No grade available, grade will be created {}", studentRequestDto.getGrade());
                sectionCount = student.getGrade().getSectionCount();
                sectionCount = sectionCount-1;
                student.getGrade().setSectionCount(sectionCount);
                studentResponseDto = mapper.convertEntityToDto(studentRepo.save(student));
                logger.info("Student successfully added whose rollNo {}", student.getId());
                return studentResponseDto;
            }
        }catch(Exception e){
            throw new StudentException("Unable to add the student detail", e);
        }
    }

    /**
     * <p>
     * Display the students detail of all grade.
     * </p>
     * @return List of StudentResponseDto if present or else null
     */
    public List<StudentResponseDto> showAllStudentDetails() {
        try {
            if (!studentRepo.findAll().isEmpty()) {
                List<StudentResponseDto> studentResponseDtos = mapper.covertStudentEntityToDto(studentRepo.findAll());
                studentResponseDtos.forEach(studentResponse -> {
                    int age = DateUtil.getDifferenceBetweenDateByYears(studentResponse.getDob(), null);
                    logger.debug("Calculates the student age " + age + " by their DOB ");
                    studentResponse.setAge(age);
                });
                return studentResponseDtos;
            } else {
                return null;
            }
        }catch (Exception e){
            throw new StudentException("Unable to display the student details");
        }
    }

   /**
    * <p>
    * Get student detail by Id.
    * </p>
    *
    * @param id
    *        Id of student passed as String
    * @throws StudentException if unable to get student detail of particular rollNo.
    * @return StudentResponseDto if the rollNo of student exist or else throws Exception
    */
    public StudentResponseDto getStudentDetailByRollNo(String id) {
            Student student = studentRepo.findById(id).orElseThrow(() -> new StudentException("No Student enrolled with id " + id));
            logger.debug("Student detail retrieved successfully whose rollNo {}", id);
            StudentResponseDto studentResponseDto = mapper.convertEntityToDto(student);
            int age = DateUtil.getDifferenceBetweenDateByYears(studentResponseDto.getDob(), null);
            studentResponseDto.setAge(age);
            return studentResponseDto;
    }

   /**
    *
    * <p>
    * Remove student detail by rollNo.
    * </p>
    * @param id
    *        Id of student as String
    * @throws StudentException if rollNo of student not exist.
    */
    public void removeStudentByRollNo(String id) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new StudentException("No Student enrolled with id "+id));
        Grade grade = student.getGrade();
        //update the grade count
        gradeService.updateGradeCount(grade);
        studentRepo.deleteById(id);
        logger.info("Student detail successfully removed whose rollNo {}", id);
    }

   /**
    * <p>
    * It adds student to the clubs
    * </p>
    *
    * @param studentAssignClubDto {@link StudentAssignClubDto}
    * @throws StudentException if unable to add the student to clubs
    * @return AddStudentToClubResponseDto if student added to club or else null
    */
    public AddStudentToClubResponseDto assignStudentToClub(String  id, StudentAssignClubDto studentAssignClubDto) {
        Student student = studentRepo.findById(id)
                        .orElseThrow(() -> new StudentException("No student enrolled with rollNo " + id));
        logger.debug("Already enrolled student whose rollNo " + student);
        //It has the new Clubs added to student
        Set<Club> newClubs = new HashSet<>();
        //It has club Ids that do not exist as String
        List<String> noExistClubIds = new ArrayList<>();
        //It has club Ids that already added to student as String
        List<String> alreadyAddedClubs = new ArrayList<>();

        for(String clubId : studentAssignClubDto.getClubIds()){
            Club club = clubService.getClub(clubId);
            //check if given club exist
            if(clubService.isClubExist(clubId)){
                //check if student already added to the clubs
                if(!student.getClubs().contains(club)) {
                    newClubs.add(club);
                }else{
                    alreadyAddedClubs.add(clubId);
                }
            }else{
                noExistClubIds.add(clubId);
            }
        }
        List<Club> createdClubs = null;
        List<ClubResponseDto> clubResponseDtos = null;
        //Assign the student to clubs
        if(!newClubs.isEmpty()) {
            Set<Club> clubs = student.getClubs();
            newClubs.forEach(club -> {
                clubs.add(club);
            });
            student.setClubs(clubs);
            createdClubs = new ArrayList<>(studentRepo.save(student).getClubs());
            clubResponseDtos = mapper.convertClubEntityToDto(createdClubs);
            logger.info("Student assigned to club successfully whose rollNo {}", student.getId());
        }
        //If the api request is empty
        if(createdClubs == null && noExistClubIds.isEmpty() && alreadyAddedClubs.isEmpty()) {
            return null;
        }else{
            return mapper.convertToAddStudentToClubResponseDto(clubResponseDtos, noExistClubIds, alreadyAddedClubs);
        }
    }

    /**
     * <p>
     * It updates the student
     * </p>
     * @param id
     *        Id of the student as String
     * @param updateStudentDto {@link UpdateStudentDto}
     * @throws StudentException if rollNo of student not exist.
     * @return ResponseEntity of StudentResponseDto if student updated
     */
    public StudentResponseDto updateStudent(String id, UpdateStudentDto updateStudentDto) {
        Student existingStudent = studentRepo.findById(id)
                .orElseThrow(() -> new StudentException("No Student enrolled with this rollNo " + id));
        //update the student details
        existingStudent.setName(updateStudentDto.getName());
        existingStudent.setMark(updateStudentDto.getMark());
        existingStudent.setDob(updateStudentDto.getDob());
        //update the address details of student
        AddressRequestDto updatedAddress = updateStudentDto.getAddress();
        if (updatedAddress != null) {
            Address existingAddress = existingStudent.getAddress();
            existingAddress.setDoorNo(updatedAddress.getDoorNo());
            existingAddress.setStreet(updatedAddress.getStreet());
            existingAddress.setCity(updatedAddress.getCity());
            existingAddress.setState(updatedAddress.getState());
            existingAddress.setPincode(updatedAddress.getPincode());
        }
        //update the grade detail of student
        Grade grade = gradeService.getGradeOfStandardAndSection(mapper.convertUpdateGradeDtoToEntity(updateStudentDto.getGrade()));
        if(grade != null){
                if(grade.getSectionCount() > 0){
                    existingStudent.setGrade(grade);
            }
        }
        //convert the Student entity into StudentResponseDto
        return mapper.convertEntityToDto(studentRepo.save(existingStudent));
    }
}