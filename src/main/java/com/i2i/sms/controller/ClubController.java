package com.i2i.sms.controller;

import java.util.List;

import com.i2i.sms.dto.ClubResponseDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.i2i.sms.dto.ClubRequestDto;
import com.i2i.sms.dto.StudentResponseDto;
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
     * It creates the Club
     * </p>
     */
    @PostMapping
    public ResponseEntity<HttpStatus> addClub(@RequestBody ClubRequestDto clubRequestDto){
        try {
            clubService.addClubDetail(clubRequestDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * <p>
     * It gets all students of the given grade
     * </p>
     */
    @GetMapping("/{clubId}")
    public ResponseEntity<List<StudentResponseDto>> getAllStudentsOfClub(@PathVariable("clubId") int clubId){
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
