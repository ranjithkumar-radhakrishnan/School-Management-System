package com.i2i.sms.service;

import java.util.List;

import com.i2i.sms.dto.ClubRequestDto;
import com.i2i.sms.dto.ClubResponseDto;
import com.i2i.sms.dto.StudentResponseDto;
import com.i2i.sms.model.Club;

/**
 *This interface has declared methods which used to add, get, display the club details
 */
public interface ClubService {
    ClubResponseDto addClubDetail(ClubRequestDto clubRequestDto);
    List<ClubResponseDto> getAllClubs();
    List<StudentResponseDto> showAllStudentsOfClub(String clubId);
    Club getClubs(String clubId);
    boolean isClubExist(String clubId);
}
