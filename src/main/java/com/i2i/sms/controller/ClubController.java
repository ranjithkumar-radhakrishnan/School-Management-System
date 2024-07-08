package com.i2i.sms.controller;

import java.util.List;

import com.i2i.sms.dto.ClubResponseDto;
import com.i2i.sms.utils.CommonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.i2i.sms.dto.ClubRequestDto;
import com.i2i.sms.dto.StudentResponseDto;
import com.i2i.sms.exception.ClubException;
import com.i2i.sms.service.ClubService;
import com.i2i.sms.utils.DateUtil;

@RestController
@RequestMapping("/sms/api/v1/clubs")
public class ClubController {
    @Autowired
    private ClubService clubService;

    private static final Logger logger = LogManager.getLogger(ClubController.class);

    /**
     * <p>
     * It creates the new Club
     * </p>
     * @param clubRequestDto {@link ClubRequestDto}
     * @throws ClubException if unable to add the club
     * @return ResponseEntity of ClubResponseDto if club created or else http status NOT_FOUND
     */
    @PostMapping
    public ResponseEntity<?> addClub(@RequestBody ClubRequestDto clubRequestDto){
        try {
            if(CommonUtil.isValidString(clubRequestDto.getName())) {
                if(CommonUtil.isValidString(clubRequestDto.getPresident())) {
                    ClubResponseDto clubResponseDto = clubService.addClubDetail(clubRequestDto);
                    return new ResponseEntity<>(clubResponseDto, HttpStatus.CREATED);
                }else{
                    return new ResponseEntity<>("Invalid President name : " + clubRequestDto.getPresident(), HttpStatus.BAD_REQUEST);
                }
            }else {
                return new ResponseEntity<>("Invalid Club name : " + clubRequestDto.getName(), HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    /**
     * <p>
     * It gets all students of the given club Id
     * </p>
     * @param clubId
     *        Id of the club as String
     * @throws ClubException if unable to get the students of the club
     * @return ResponseEntity of List of StudentResponseDto if present or else http status NOT_FOUND
     */
    @GetMapping("/{clubId}")
    public ResponseEntity<List<StudentResponseDto>> getAllStudentsOfClub(@PathVariable("clubId") String clubId){
        try {
           List<StudentResponseDto> studentResponseDtos=  clubService.showAllStudentsOfClub(clubId);
            studentResponseDtos.forEach(studentResponse -> {
                int age = DateUtil.getDifferenceBetweenDateByYears(studentResponse.getDob(), null);
                logger.debug("Calculates the student age " + age + " by their DOB ");
                studentResponse.setAge(age);
            });
           logger.info("Students retrieved succesfully of club Id {}", clubId);
           return new ResponseEntity<>(studentResponseDtos, HttpStatus.OK);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    /**
     * <p>
     * It gets all the clubs
     * </p>
     * @return ResponseEntity of List of ClubResponseDto if present or else http status NOT_FOUND
     */
    @GetMapping
    public ResponseEntity<List<ClubResponseDto>> getAllClubs(){
        List<ClubResponseDto> clubResponseDtos = clubService.getAllClubs();
        if(clubResponseDtos != null) {
            logger.info("Clubs retrieved successfully");
            return new ResponseEntity<>(clubResponseDtos, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(clubResponseDtos, HttpStatus.NOT_FOUND);
        }
    }
}
