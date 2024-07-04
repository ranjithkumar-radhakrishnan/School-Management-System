package com.i2i.sms.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.i2i.sms.dto.ClubAssignStudentDto;
import com.i2i.sms.dto.ClubRequestDto;
import com.i2i.sms.dto.StudentResponseDto;
import com.i2i.sms.exception.ClubException;
import com.i2i.sms.helper.Mapper;
import com.i2i.sms.model.Club;
import com.i2i.sms.model.Student;
import com.i2i.sms.repositry.ClubRepo;

/**
*This class implemented to store, collect and search the club details.
*/
@Service
public class ClubServiceImpl implements ClubService {
    @Autowired
    private ClubRepo clubRepo;
    @Autowired
    private Mapper mapper;

    private static final Logger logger = LogManager.getLogger(StudentServiceImpl.class);

   /**
    * <p>
    * It creates the new club.
    * </p>
    * @param clubRequestDto
    *        the DTO which contains the club detail except club id
    * @throws ClubException if unable to create the club detail
    */
    public void addClubDetail(ClubRequestDto clubRequestDto) {
        try {
            Club club = mapper.convertClubDtoToEntity(clubRequestDto);
            clubRepo.save(club);
            logger.info("Club added successfully");
        }catch (Exception e){
            throw new ClubException("Unable to add the Club detail");
        }
    }

   /**
    * <p>
    * It gets all the clubs.
    * </p>
    *
    * @return List of ClubRequestDto which contains the club information or else null
    */
    public List<ClubRequestDto> getAllClubs() {
        if(!clubRepo.findAll().isEmpty()) {
            logger.debug("Club is not empty");
            return mapper.convertClubEntityToDto(clubRepo.findAll());
        }else{
            return null;
        }
    }

   /**
    * <p>
    * It displays all the students of particular club with student, address
    * </p>
    *
    * @param clubId
    *        Id of the club passed as integer.
    * @return List of students which contains the student, address,grade detail as StudentResponseDto
    */
    public List<StudentResponseDto> showAllStudentsOfClub(int clubId) {
        Club club = clubRepo.findById(clubId)
                .orElseThrow(() -> new ClubException("No club available with this Id "+clubId));
        List<Student> students = new ArrayList<>(club.getStudents());
        return mapper.covertStudentEntityToDto(students);
    }
    /**
     * <p>
     * It gets the set of clubs with given ClubAssignStudentDtoSet
     * </p>
     *
     * @param clubAssignStudentDtoSet
     *        Set of clubs as ClubAssignStudentDto
     * @return Set of Club which contains club detail
     */
    public Set<Club> getClubs(Set<ClubAssignStudentDto> clubAssignStudentDtoSet){
        Set<Club> clubs = new HashSet<>();
        for (ClubAssignStudentDto clubAssignStudentDto : clubAssignStudentDtoSet) {
            clubs.add(clubRepo.findById(clubAssignStudentDto.getId()).orElse(new Club()));
        }
        return clubs;
    }
}