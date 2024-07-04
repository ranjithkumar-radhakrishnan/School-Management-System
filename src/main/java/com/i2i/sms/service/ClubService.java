package com.i2i.sms.service;

import java.util.List;
import java.util.Set;

import com.i2i.sms.dto.ClubAssignStudentDto;
import com.i2i.sms.dto.ClubRequestDto;
import com.i2i.sms.dto.StudentResponseDto;
import com.i2i.sms.model.Club;

/**
 *This interface has declared methods which used to add, get, display the club details
 */
public interface ClubService {
    void addClubDetail(ClubRequestDto clubRequestDto);
    List<ClubRequestDto> getAllClubs();
    List<StudentResponseDto> showAllStudentsOfClub(int clubId);
    Set<Club> getClubs(Set<ClubAssignStudentDto> clubAssignStudentDtoSet);
}
