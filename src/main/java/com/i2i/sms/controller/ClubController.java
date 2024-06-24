package com.i2i.sms.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.i2i.sms.model.Club;
import com.i2i.sms.model.Student;
import com.i2i.sms.service.ClubService;
import com.i2i.sms.service.StudentService;
import com.i2i.sms.utils.DateUtil;

/**
* Implementation to handle club details.
*/
public class ClubController {

    private Scanner scanner = new Scanner(System.in);
    private StudentService studentService = new StudentService();
    private ClubService clubService = new ClubService();
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
        if(clubService.addClubDetail(clubName, president, website, count)) {
            System.out.println("Club created successfully...");
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
       Set<Student> students = clubService.showAllStudentsOfClub(clubId);
       for(Student student : students) {
           int age = DateUtil.getDifferenceBetweenDateByYears(student.getDob(), null);
           System.out.println("\t\t\tGrade: " + student.getGrade().getStandard());
           System.out.println("\t\t\tSection: " + student.getGrade().getSection());
           System.out.println(student);
           System.out.println("\t\tStudent age: " + age);
       }
    }
}