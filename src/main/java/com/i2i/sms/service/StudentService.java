package com.i2i.sms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.i2i.sms.dao.ClubDao;
import com.i2i.sms.dao.StudentDao;
import com.i2i.sms.model.Address;
import com.i2i.sms.model.Club;
import com.i2i.sms.model.Grade;
import com.i2i.sms.model.Student;
/**
*
*This class implemented to store, collect, search and remove the student details.
*
*/
public class StudentService {
    
    private StudentDao studentDao = new StudentDao();
    private GradeService gradeService = new GradeService();
    private ClubService clubService = new ClubService();

   /**
    * <p>
    * Creates student detail by getting their information.
    * </p>
    *
    * @param standard
    *        Grade of student passed as integer.
    * @param section
    *        Section of student passed as character.
    * @param student
    *        Student which holds the rollNo, name, mark, dob, Address detail 
    * @throws StudentException if not able to add student detail
    * @return boolean value as true if student detail added successfully or else false
    */
    public boolean createStudentDetail(int standard, char section, Student student) {
        Grade gradeWithStandard = gradeService.getGradeWithStandardAndSection(standard, section);
        if (null == gradeWithStandard) {
            Grade grade = new Grade();
            grade.setStandard(standard);
            grade.setSection(section);
            grade.setSectionCount(grade.getSectionCount()-1);
            student.setGrade(grade);
            return studentDao.createStudentDetail(student);
        } else {
            student.setGrade(gradeWithStandard);
            if (studentDao.createStudentDetail(student)) {
                return gradeService.updateCountOfGrade(gradeWithStandard.getGradeId(), false);
            } else {
                return false;
            }
        }
    }

   /**
    * <p>
    * It gets the student detail of all grades.
    * </p>
    *
    * @throws StudentException if unable to get the student detail of all grades.
    * @return List of student detail if present or else null
    */
    public List<Student> showAllStudentDetails() {
        return studentDao.fetchAllStudentDetail();
    }

   /**
    * <p>
    * Get student detail by rollNo.
    * </p>
    *
    * @param rollNo
    *        RollNo of student passed as int.
    * @throws StudentException if unable to get student detail of particular rollNo.
    * @return Student detail if the rollNo of student exist or else null
    */
    public Student getStudentDetailByRollNo(int rollNo) {
        return studentDao.retrieveStudentDetailByRollNo(rollNo);
    }
    

   /**
    *
    * <p>
    * Remove student detail by rollNo.
    * </p>
    *
    * @param rollNo
    *        RollNo of student as integer.
    * @throws StudentException if rollNo of student not exist.
    * @return Boolean type as true if student detail gets removed or else return false
    */
    public boolean removeStudentByRollNo(int rollNo) {
        List<Integer> clubIds = new ArrayList<>();
        Student student = studentDao.retrieveStudentDetailByRollNo(rollNo);
        int gradeId = student.getGrade().getGradeId();
        student.getClubs().forEach(club -> clubIds.add(club.getId()));

        if (studentDao.deleteStudentByRollNo(rollNo)) {
            for(Integer clubId : clubIds) {
                clubService.updateCountOfClub(clubId, true);
            }
            return gradeService.updateCountOfGrade(gradeId, true);
        } else {
            return false;
        }
    }
    
   /**
    * <p>
    * Creates student detail by getting their information.
    * </p>
    *
    * @param name
    *        Name of student passed as string.
    * @param mark
    *        Mark of student passed as integer.
    * @param dob
    *        Dob of student passed in "dd/MM/yyyy" format.
    * @param doorNo
    *        DoorNo of address passed as Integer.
    * @param street
    *        Street of the address as String.
    * @param city
    *        City of the address as String.
    * @param state
    *        State of the address as String.
    * @param pincode
    *        pincode of the address as Integer.
    * @throws StudentException if not able to add student detail
    * @return boolean value as true if student detail added successfully or else false
    */
    public Student createStudent(String name, int mark, Date dob, String doorNo, String street, String city, String state, int pincode) {
        Student student = new Student();
        student.setName(name);
        student.setMark(mark);
        student.setDob(dob);

        Address address = new Address();
        address.setDoorNo(doorNo);
        address.setStreet(street);
        address.setCity(city);
        address.setState(state);
        address.setPincode(pincode);
        student.setAddress(address);
        return student;
    }

   /**
    * <p>
    * It adds student to the clubs
    * </p>
    *
    * @param student
    *        Student which contains the information such as roll No, name, mark, dob
    * @param clubIds
    *        It contains all the club Ids enrolled by the student.
    * @throws StudentException if unable to add the student to clubs
    */
    public void assignStudentToClub(Student student, int[] clubIds) {
        Set<Club> clubs = clubService.getClubsOfStudent(clubIds);
        student.setClubs(clubs);
        if(studentDao.insertStudentToClub(student)) {
            for(int i=0;i<clubIds.length;i++) {
                clubService.updateCountOfClub(clubIds[i], false);
            }
        }
    }
}