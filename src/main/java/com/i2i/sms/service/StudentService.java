package com.i2i.sms.service;

import java.util.List;

import com.i2i.sms.dto.StudentAssignClubDto;
import com.i2i.sms.dto.StudentRequestDto;
import com.i2i.sms.dto.StudentResponseDto;

/**
 *This interface has declared methods which used to add, get, display and remove the student details
 */
public interface StudentService {
    void createStudentDetail(StudentRequestDto studentRequestDto);
    List<StudentResponseDto> showAllStudentDetails();
    StudentResponseDto getStudentDetailByRollNo(int rollNo);
    void removeStudentByRollNo(int rollNo);
    void assignStudentToClub(StudentAssignClubDto studentAssignClubDto);
}
