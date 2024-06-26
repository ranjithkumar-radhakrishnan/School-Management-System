package com.i2i.sms.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.i2i.sms.dao.ClubDao;
import com.i2i.sms.exception.StudentException;
import com.i2i.sms.model.Club;
import com.i2i.sms.model.Student;

/**
*
*This class implemented to store, collect, search and remove the club details.
*
*/
public class ClubService {

    private ClubDao clubDao = new ClubDao();
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
   /**
    * <p>
    * Add the club detail which contains club Id, clubName, president, website, count.
    * </p>
    *
    * @param clubName
    *        Name of the club as String.
    * @param president
    *        Name of the president of club passed as String.
    * @param website
    *        website of club passed as String.
    * @param count
    *        count of the club passed as Integer.
    * @return Boolean value as true if club gets added successfully or else false
    */
    public boolean addClubDetail(String clubName, String president, String website, int count) {
        Club club = new Club();
        club.setName(clubName);
        club.setPresident(president);
        club.setWebsite(website);
        club.setCount(count);

        return clubDao.insertClub(club);          
    }

   /**
    * <p>
    * It gets all the clubs.
    * </p>
    *
    * @return List of clubs which contains the information such as club Id, name, president, website, count.
    */
    public List<Club> getAllClubs() {
        return clubDao.fetchAllClubs();
    }

   /**
    * <p>
    * It gets all the students of particular club with student roll No, name, mark, dob.
    * </p>
    *
    * @param clubId
    *        Id of the club passed as integer.
    * @return Set of students which contains the information such as roll No, name, mark, dob
    */
    public Set<Student> showAllStudentsOfClub(int clubId) {
        return clubDao.displayAllStudentsOfClub(clubId);
    }

   /**
    * <p>
    * It gets all the clubs enrolled by the student.
    * </p>
    *
    * @param clubIds
    *        Ids of the club passed in Integer array.
    * @return Set of clubs which is enrolled by the student
    */
    public Set<Club> getClubsOfStudent(int[] clubIds) {
        Set<Club> clubs = new HashSet<>();
        for(int i=0;i<clubIds.length;i++){
            Club club = clubDao.getClubById(clubIds[i]);
            clubs.add(club);
        }
        logger.info("Get all the clubs that student going to enroll");
        return clubs;
    }

   /**
    *
    * <p>
    * Updates the club count, either increased by one or decreased by one
    * </p>
    *
    * @param clubId
    *        Club Id of the grade passed that we want to update the count.
    * @param isIncrement
    *        It increases grade count by 1 if pass 1.
    *        It decreases grade count by 1 if pass 0.
    * @throws StudentException if unable to update the count of the club.
    * @return Boolean value as true if it updates the count of club or else false
    *         Boolean value as false if we pass second parameter other than 0 or 1
    */
    public boolean updateCountOfClub(int clubId, boolean isIncrement) {
        return clubDao.updateClubCount(clubId, isIncrement);
    }

}