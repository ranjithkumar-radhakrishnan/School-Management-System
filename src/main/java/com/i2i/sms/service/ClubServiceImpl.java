package com.i2i.sms.service;

import java.util.ArrayList;
import java.util.List;

import com.i2i.sms.dto.ClubResponseDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * It creates the new Club
     * </p>
     * @param clubRequestDto {@link ClubRequestDto}
     * @throws ClubException if unable to add the club
     * @return ClubResponseDto if club created
     */
    public ClubResponseDto addClubDetail(ClubRequestDto clubRequestDto) {
        try {
            Club club = mapper.convertClubDtoToEntity(clubRequestDto);
            ClubResponseDto clubResponseDto = mapper.convertClubEntityToDto(clubRepo.save(club));
            logger.info("Club added successfully where Id {}", clubResponseDto.getId());
            return clubResponseDto;
        }catch (Exception e){
            throw new ClubException("Unable to add the Club detail");
        }
    }

    /**
     * <p>
     * It gets all the clubs
     * </p>
     * @return  List of ClubResponseDto if present or else null
     */
    public List<ClubResponseDto> getAllClubs() {
        if(!clubRepo.findAll().isEmpty()) {
            logger.debug("Club is not empty");
            return mapper.convertClubEntityToDto(clubRepo.findAll());
        }else{
            return null;
        }
    }

    /**
     * <p>
     * It gets all students of the given club Id
     * </p>
     * @param clubId
     *        Id of the club as String
     * @throws ClubException if unable to get the students of the club
     * @return  List of StudentResponseDto if present or else null
     */
    public List<StudentResponseDto> showAllStudentsOfClub(String clubId) {
        Club club = clubRepo.findById(clubId)
                .orElseThrow(() -> new ClubException("No club available with this Id "+clubId));
        //Convert Set of student into List of student
        List<Student> students = new ArrayList<>(club.getStudents());
        return mapper.covertStudentEntityToDto(students);
    }
    /**
     * <p>
     * It gets the club with given club Id
     * </p>
     *
     * @param clubId
     *        Id of the club as String
     * @return Club if existed
     */
    public Club getClubs(String clubId){
        return clubRepo.findById(clubId).orElse(new Club());
    }
    /**
     * <p>
     * It gets the club with given club Id
     * </p>
     *
     * @param clubId
     *        Id of the club as String
     * @return boolean value as true if club existed or else false
     */
    public boolean isClubExist(String clubId ){
        return clubRepo.existsById(clubId);
    }
}