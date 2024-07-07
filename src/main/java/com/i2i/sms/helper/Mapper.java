package com.i2i.sms.helper;

import java.text.SimpleDateFormat;
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

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Student student = new Student();
        student.setName(studentRequestDto.getName());
        student.setMark(studentRequestDto.getMark());
        student.setDob(studentRequestDto.getDob());
        student.setAddress(address);
        student.setGrade(grade);
        return student;
    }

    /**
     * <p>
     * Converts the List of Student entity into list of studentGradeResponseDto
     * studentGradeResponseDto contains only student, address, grade detail
     * </p>
     */
    public List<StudentResponseDto> covertStudentEntityToDto(List<Student> students){
        return students.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    /**
     * <p>
     * Converts the Student entity into studentGradeResponseDto
     * studentGradeResponseDto contains only student, address, grade detail
     * </p>
     */
    public StudentResponseDto convertEntityToDto(Student student){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        GradeResponseDto gradeResponseDto = modelMapper.map(student.getGrade(), GradeResponseDto.class);
        AddressResponseDto addressResponseDto = modelMapper.map(student.getAddress(), AddressResponseDto.class);
        StudentResponseDto studentResponseDto = new StudentResponseDto();
        studentResponseDto.setId(student.getId());
        studentResponseDto.setName(student.getName());
        studentResponseDto.setMark(student.getMark());
        studentResponseDto.setDob(student.getDob());
        studentResponseDto.setAddress(addressResponseDto);
        studentResponseDto.setGrade(gradeResponseDto);
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
        AddressResponseDto addressResponseDto= modelMapper.map(student.getAddress(), AddressResponseDto.class);
        StudentGradeResponseDto studentGradeResponseDto = new StudentGradeResponseDto();
        studentGradeResponseDto.setId(student.getId());
        studentGradeResponseDto.setName(student.getName());
        studentGradeResponseDto.setMark(student.getMark());
        studentGradeResponseDto.setDob(student.getDob());
        studentGradeResponseDto.setAddress(addressResponseDto);
        return studentGradeResponseDto;
    }
    /**
     * <p>
     * Converts the list of Club entity into list of ClubResponseDto
     * </p>
     */
    public List<ClubResponseDto> convertClubEntityToDto(List<Club> clubs) {
        return clubs.stream()
                .map(this::convertClubEntityToDto)
                .collect(Collectors.toList());
    }
    /**
     * <p>
     * Converts the Club entity into ClubResponseDto
     * </p>
     */
    public ClubResponseDto convertClubEntityToDto(Club club) {
        ClubResponseDto clubResponseDto = modelMapper.map(club, ClubResponseDto.class);
        return clubResponseDto;
    }

    /**
     * <p>
     * Converts the ClubRequestDto into Club entity
     * ClubRequestDto contains club detail except club Id
     * which automatically generated in database
     * </p>
     */
    public Club convertClubDtoToEntity(ClubRequestDto clubRequestDto){
        Club club  = modelMapper.map(clubRequestDto, Club.class);
        return club;
    }

    public AddStudentToClubResponseDto convertStudentAddClubEntityToDto(List<ClubResponseDto> addedClubs, List<String> noExistClubIds) {
        AddStudentToClubResponseDto addStudentToClubResponseDto = new AddStudentToClubResponseDto();
        addStudentToClubResponseDto.setAddedClubs(addedClubs);
        addStudentToClubResponseDto.setNoClubsExist(noExistClubIds);
        return addStudentToClubResponseDto;
    }

    public Grade convertUpdateGradeDtoToEntity(UpdateGradeDto updateGradeDto){
         return modelMapper.map(updateGradeDto, Grade.class);
    }
}
