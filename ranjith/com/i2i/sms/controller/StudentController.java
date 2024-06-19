package com.i2i.sms.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

import com.i2i.sms.model.Club;
import com.i2i.sms.model.Student;
import com.i2i.sms.service.ClubService;
import com.i2i.sms.service.StudentService;
import com.i2i.sms.utils.CommonUtil;
import com.i2i.sms.utils.DateUtil;
 
/**
* Implementation to handle student and their grade details.
*/
public class StudentController {
    
    private Scanner scanner = new Scanner(System.in);
    private StudentService studentService = new StudentService();
    
   /**
    * <p>
    * Get all the student detail such as name, mark, dob, standard and section
    * </p>
    *
    */
    public void addStudentDetail() {
        boolean validNameOrNot = true;
        String name = "";
        while(validNameOrNot) {
            System.out.println("Enter the Student Name");
            name = scanner.next();
            validNameOrNot = CommonUtil.isValidName(name);

            if (!validNameOrNot) {
                System.out.println("Student name should have only lowercase,uppercase letter and may contain space");
                validNameOrNot = true;
            } else {
                break;
            }
        }
    
        String studentMark = "";
        boolean validMarkOrNot = true;
        int mark = 0; 
        while(validMarkOrNot) {
            System.out.println("Enter the Student Mark");
            studentMark = scanner.next();
            validMarkOrNot = CommonUtil.isValidNumber(studentMark);
    
            if (!validMarkOrNot) {
                System.out.println("Student mark should be integer");
                validMarkOrNot = true;
            } else {
                mark = Integer.parseInt(studentMark);
                validMarkOrNot = CommonUtil.isValidRangeOfNumber(mark,0,100);
                if (!validMarkOrNot) {
                    System.out.println("Student mark should ranges between 0 to 100");
                    validMarkOrNot = true;
                } else {
                    break;
                }
            }
        }

        boolean validation = true;
        String date = "";
        Date dob = null;
        while (validation) {
            System.out.println("Enter the Student Dob in format DD/MM/YYYY");
            date = scanner.next();
            dob = DateUtil.validateDateFormat(date);
            if (dob == null) {
                System.out.println("Invalid date format");
                validation = true;
            } else {
                break;
            }
        }
    
        boolean validOrNot = true;
        String studentStandard = "";
        int standard = 0;
        while(validOrNot) {
            System.out.println("Enter the Student grade");
            studentStandard = scanner.next();
            validOrNot = CommonUtil.isValidNumber(studentStandard);
            if (!validOrNot) {
                System.out.println("Student grade should be integer..");
                validOrNot = true;
            } else {
                standard = Integer.parseInt(studentStandard);
                validOrNot = CommonUtil.isValidRangeOfNumber(standard,1,12);

                if (validOrNot) {
                    while(validOrNot) {
                        System.out.println("Enter the Student Section: ");
                        char section = scanner.next().charAt(0);
                        validOrNot = CommonUtil.isUppercaseCharacter(section);

                        if (validOrNot) {
                            while(validOrNot) {
                                System.out.println("Enter the student address");
                                  
                                System.out.println("Enter the Door Number: ");
                                String doorNo = scanner.next();
                                 
                                System.out.println("Enter the street: ");
                                String street = scanner.next();

                                if (CommonUtil.isValidName(street)) {
                                    System.out.println("Enter the city: ");
                                    String city = scanner.next();
                                    
                                    if (CommonUtil.isValidName(city)) {
                                        System.out.println("Enter the state: ");
                                        String state = scanner.next();
                                        
                                        if (CommonUtil.isValidName(state)){
                                            System.out.println("Enter the pincode: ");
                                            String pin = scanner.next();
                          
                                            if (CommonUtil.isValidNumber(pin)) {
                                                int pincode = Integer.parseInt(pin);
                                                Student student = studentService.createStudent(name, mark, dob, doorNo, street, city, state, pincode);
                                                if (studentService.createStudentDetail(standard, section, student)) {
                                                    System.out.println("Student detail succesfully added..");
                                                } 
                                                validOrNot = false;
                                            } else {
                                                System.out.println("Enter the pincode in correct format..");
                                                validOrNot = true;
                                            }
                                        } else {
                                            System.out.println("Enter the state in correct format..");
                                            validOrNot = true;
                                        }
                                    } else {
                                        System.out.println("Enter the city in correct format..");
                                        validOrNot = true;
                                    }
                                } else {
                                    System.out.println("Enter the street in correct format..");
                                    validOrNot = true;
                                }
                            }
                            
                        } else {
                            System.out.println("Student section not valid..");
                            validOrNot = true;
                        } 
                    }
                } else {
                    validOrNot = true;
                    System.out.println("Student grade should range from 1 to 12..");
                } 
             
            }    
        }
    }
   
   /**
    * <p>
    * Get the rollNo of student to display their detail
    * It displays the grade, section, name, mark, dob, age of the student.
    * </p>
    *
    */
    public void getStudentDetailByRollNo() {
        System.out.println("Enter the RollNo of Student");
        int rollNo = scanner.nextInt();
        Student student = studentService.getStudentDetailByRollNo(rollNo);
        if(student == null) {
            System.out.println("No student enrolled by this rollNo " + rollNo);
        } else {
            int age = DateUtil.getDifferenceBetweenDateByYears(student.getDob(),null);
            System.out.println("\t\t\tGrade: " + student.getGrade().getStandard());
            System.out.println("\t\t\tSection: " + student.getGrade().getStandard());
            System.out.println(student);
            System.out.println("\t\tStudent Age: "+age);
        }
    }


   /**
    * <p>
    * Get the rollNo of student to remove them.
    * </p>
    *
    * @throws StudentException if rollNo of student not exist.
    */
    public void getRollNoToRemove() {
        System.out.println("Enter the RollNo of the student");
        int rollNo = scanner.nextInt();
        if(studentService.removeStudentByRollNo(rollNo)) {
            System.out.println("Student detail removed successfully..");
        }
    }

   /**
    * <p>
    * Display the students detail of all grade.
    * </p>
    *
    */
    public void printAllStudentsInformation() {
        List<Student> allStudents = studentService.showAllStudentDetails();
        for(Student student : allStudents) {
            int age = DateUtil.getDifferenceBetweenDateByYears(student.getDob(), null);
            System.out.println("Grade: " + student.getGrade().getStandard());
            System.out.println("Section: " + student.getGrade().getSection());
            System.out.println(student);
            System.out.println("\t\tStudent age: " + age);
        }
    }

   /**
    * <p>
    * It adds student to the clubs
    * </p>
    */
    public void addStudentToClub() {
        System.out.println("Enter the Student Roll No:");
        int rollNo = scanner.nextInt();
        System.out.println("Enter the no of clubs you want to add:");
        int noOfClubs = scanner.nextInt();

        int clubIds[] = new int[noOfClubs];

        System.out.println("Enter the club Ids:");
        for(int i=0;i < noOfClubs;i++){
            clubIds[i] = scanner.nextInt();
        }

        Student student = studentService.getStudentDetailByRollNo(rollNo);
        studentService.assignStudentToClub(student, clubIds);
    }
}