package com.i2i.sms.service;

import java.util.List;

import com.i2i.sms.dto.*;


/**
 *This interface has declared methods which used to add, get, display and remove the student details
 */
public interface StudentService {
    StudentResponseDto createStudentDetail(StudentRequestDto studentRequestDto);
    List<StudentResponseDto> showAllStudentDetails();
    StudentResponseDto getStudentDetailByRollNo(String id);
    void removeStudentByRollNo(String id);
    AddStudentToClubResponseDto assignStudentToClub(String id, StudentAssignClubDto studentAssignClubDto);

    StudentResponseDto updateStudent(String id, UpdateStudentDto updateStudentDto);
}
