package com.i2i.sms.controller;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.i2i.sms.model.Club;
import com.i2i.sms.model.Student;
import com.i2i.sms.service.ClubService;
import com.i2i.sms.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
* Implementation to handle club details.
*/
@Controller
public class ClubController {

    private Scanner scanner = new Scanner(System.in);
    @Autowired
    private ClubService clubService;
    private static final Logger logger = LogManager.getLogger(ClubController.class);
   /**
    * <p>
    * Get the clubName from student to them into particular club.
    * </p>
    *
    */
    public void addClubDetail() {
        boolean loopCondition = true;
        while(loopCondition) {
            System.out.println("Choose the Club: ");
            System.out.println("Enter 1 for add to Art Club\n" +
                               "Enter 2 for add to Chess Club\n" +
                               "Enter 3 for add to Film Club\n" +
                               "Enter 4 for add to Dance Club\n" +
                               "Enter 5 for add to Photography Club\n" +
                               "Enter 6 to exit");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    getClubDetail("Art Club");
                    break;

                case 2:
                    getClubDetail("Chess Club");
                    break;

                case 3:
                    getClubDetail("Film Club");
                    break;

                case 4:
                    getClubDetail("Dance Club");
                    break;

                case 5:
                    getClubDetail("Photography Club");
                    break;

                case 6:
                    loopCondition = false;
                    break;
            }
        }
    }

   /**
    * <p>
    * To add the new Club details.
    * </p>
    * 
    * @param clubName
    *        Name of the Club that is going to be added.
    */
    public void getClubDetail(String clubName) {    
        System.out.println("Enter the club President: ");
        String president = scanner.next();
        System.out.println("Enter the club website: ");
        String website = scanner.next();
        System.out.println("Enter the club Count: ");
        int count = scanner.nextInt();
        try {
            boolean isAdded = clubService.addClubDetail(clubName, president, website, count);
            if(isAdded) {
                System.out.println("Club detail added successfully");
            }
        }catch(Exception e){
            logger.error(e.getMessage());
        }
     }

    /**
    * <p>
    * To list all the students who are enrolled in particular club.
    * </p>
    */
    public void printAllStudentsOfClub() {
       List<Club> clubs = clubService.getAllClubs();
       clubs.forEach(club -> System.out.println(club.getId() + "-" +club.getName()));
       System.out.println("Enter the club id");
       int clubId = scanner.nextInt();
       try {
           Set<Student> students = clubService.showAllStudentsOfClub(clubId);
           if(!students.isEmpty()) {
               for (Student student : students) {
                   int age = DateUtil.getDifferenceBetweenDateByYears(student.getDob(), null);
                   System.out.println("\t\t\tGrade: " + student.getGrade().getStandard());
                   System.out.println("\t\t\tSection: " + student.getGrade().getSection());
                   System.out.println(student);
                   System.out.println("\t\tStudent age: " + age);
               }
               logger.info("Displayed all the students of club of Id {}", clubId);
               System.out.println("Displayed all the students of club of Id " + clubId);
           }else{
               logger.warn("No students enrolled in this club of Id {}", clubId);
               System.out.println("No students enrolled in this club of Id " + clubId);
           }
       }catch(Exception e){
           logger.error(e.getMessage());
       }
    }
}