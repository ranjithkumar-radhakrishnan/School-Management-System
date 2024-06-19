import java.util.Scanner;

import com.i2i.sms.controller.ClubController;
import com.i2i.sms.controller.StudentController;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private StudentController studentController = new StudentController();
    private ClubController clubController = new ClubController();

    public static void main(String[] args) {
        Main main = new Main();
        main.startApplication();
    }
    
    public void startApplication() {
         boolean loop = true;   
        while (loop) {
            System.out.println();
            System.out.println("Enter 1 to add Student details\n" +
                               "Enter 2 to print student detail by Roll No\n" +
                               "Enter 3 to remove Student detail\n" +
                               "Enter 4 to print all student deatils\n" +
                               "Enter 5 to add the club details\n" +
                               "Enter 6 to add student to club\n" + 
                               "Enter 7 to print all students in particular club\n" +
                               "Enter 8 to exit");

            int option = scanner.nextInt();
            try {
                switch(option) {
             
                    case 1:
                        studentController.addStudentDetail();
                        break;
               
                    case 2:
                        studentController.getStudentDetailByRollNo();
                        break;

                    case 3:
                        studentController.getRollNoToRemove();
                        break;

                    case 4:
                        studentController.printAllStudentsInformation();
                        break;
                
                    case 5:
                        clubController.addClubDetail();
                        break;

                    case 6:
                        studentController.addStudentToClub();
                        break;
                 
                    case 7:
                        clubController.printAllStudentsOfClub();
                        break;
               
                    case 8:
                        loop = false;
                        break;
                    
                }
            } catch(Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

        }
    }
}