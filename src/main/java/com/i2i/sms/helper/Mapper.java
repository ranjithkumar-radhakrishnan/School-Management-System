package com.i2i.sms.helper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.i2i.sms.dto.*;
import com.i2i.sms.model.Address;
import com.i2i.sms.model.Club;
import com.i2i.sms.model.Grade;
import com.i2i.sms.model.Student;

@Component
public class Mapper {

    @Autowired
    private ModelMapper modelMapper;
    /**
     * <p>
     * Converts the StudentRequestDto into Student entity
     * StudentRequestDto contains student detail except student rollNo
     * which automatically generated in database
     * </p>
     */
    public Student convertDtoToEntity(StudentRequestDto studentRequestDto){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Address address = modelMapper.map(studentRequestDto.getAddress(), Address.class);

        Grade grade = modelMapper.map(studentRequestDto.getGrade(), Grade.class);

        Set<Club> clubs = studentRequestDto.getClubs().stream()
                .map(this::convertClubDtoToEntity).collect(Collectors.toSet());

        Student student = new Student();
        student.setName(studentRequestDto.getName());
        student.setMark(studentRequestDto.getMark());
        student.setDob(studentRequestDto.getDob());
        student.setAddress(address);
        student.setGrade(grade);
        student.setClubs(clubs);
        return student;
    }

    /**
     * <p>
     * Converts the ClubRequestDto into Club entity
     * ClubRequestDto contains club detail except club Id
     * which automatically generated in database
     * </p>
     */
    public Club convertClubDtoToEntity(ClubRequestDto clubRequestDto){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Club club  = modelMapper.map(clubRequestDto, Club.class);
        return club;
    }

    /**
     * <p>
     * Converts the List of Student entity into list of StudentResponseDto
     * StudentResponseDto contains only student, address, grade detail
     * </p>
     */
    public List<StudentResponseDto> covertStudentEntityToDto(List<Student> student){
        return student.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    /**
     * <p>
     * Converts the Student entity into StudentResponseDto
     * StudentResponseDto contains only student, address, grade detail
     * </p>
     */
    public StudentResponseDto convertEntityToDto(Student student){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        StudentResponseDto studentResponseDto = modelMapper.map(student, StudentResponseDto.class);
        return studentResponseDto;
    }

    /**
     * <p>
     * Converts the Set of Student entity into Set of StudentGradeResponseDto
     * StudentGradeResponseDto contains only the student detail, address except grade detail
     * </p>
     */
    public Set<StudentGradeResponseDto> covertStudentOfGradeEntityToDto(Set<Student> students){
        return students.stream()
                .map(this::covertStudentOfGradeEntityToDto)
                .collect(Collectors.toSet());
    }
    /**
     * <p>
     * Converts the Student entity into StudentGradeResponseDto
     * StudentGradeResponseDto contains only the student detail, address except grade detail
     * </p>
     */
    private StudentGradeResponseDto covertStudentOfGradeEntityToDto(Student student){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        StudentGradeResponseDto studentGradeResponseDto = modelMapper.map(student, StudentGradeResponseDto.class);
        return studentGradeResponseDto;
    }
    /**
     * <p>
     * Converts the list of Club entity into list of ClubRequestDto
     * </p>
     */
    public List<ClubRequestDto> convertClubEntityToDto(List<Club> clubs) {
        return clubs.stream()
                .map(this::convertClubEntityToDto)
                .collect(Collectors.toList());
    }
    /**
     * <p>
     * Converts the Club entity into ClubRequestDto
     * </p>
     */
    public ClubRequestDto convertClubEntityToDto(Club club) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ClubRequestDto clubRequestDto = modelMapper.map(club, ClubRequestDto.class);
        return clubRequestDto;
    }
}
