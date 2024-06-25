package com.i2i.sms.dao;

import java.util.List;

import org.hibernate.query.Query; 
import org.hibernate.Session;
import org.hibernate.Transaction; 

import com.i2i.sms.exception.StudentException;
import com.i2i.sms.helper.HibernateConnection;
import com.i2i.sms.model.Student;


/**
*
* Class implemented to store, retrieve, search and remove the student details.
*
*/
public class StudentDao {

   /**
    * <p>
    * Adds the student detail.
    * </p>
    *
    * @param student
    *        Which holds the students information such as name, rollNo, mark, Dob.
    * @throws StudentException if unable to add the student deatil.
    * @return boolean value as true if student detail added successfully or else false
    */
    public boolean createStudentDetail(Student student) {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {  
            transaction = session.beginTransaction();
            session.save(student);
            transaction.commit();
            return true;
        } catch (Exception e) { 
            if (null != transaction ) {
               transaction.rollback();
            }
            throw new StudentException("Unable to add the student detail with rollNo " + student.getRollNo(), e);
        }         
    }

   /**
    * <p>
    * It adds student to the clubs
    * </p>
    *
    * @param student
    *        Student which contains the information such as roll No, name, mark, dob.
    * @throws StudentException if unable to add the student to clubs.
    * @return Boolean value as true if it adds the student to clubs
    */
    public boolean insertStudentToClub(Student student) {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getSessionFactory().openSession()) { 
            transaction = session.beginTransaction(); 
            session.saveOrUpdate(student);
            transaction.commit(); 
            return true;
        } catch (Exception e) { 
            if(transaction != null || transaction.isActive()) {
               transaction.rollback();
            }
            throw new StudentException("Unable to add the student to club with roll No " + student.getRollNo(), e);
        }         
    }
  
   /**
    * <p>
    * It fetches all the student details.
    * </p>
    *
    * @throws StudentException if unable to fetch the student detail.
    * @return List of student detail if present or else null
    */
    public List<Student> fetchAllStudentDetail() {
        List<Student> students = null;
        try (Session session = HibernateConnection.getSessionFactory().openSession()) { 
            Query query = session.createQuery("from Student");
            students = query.list();
            return students;
        } catch (Exception e) { 
            throw new StudentException("Unable to display Student details", e);
        } 
    }

   /**
    * <p>
    * Retrieve student detail by rollNo
    * </p>
    *
    * @param rollNo
    *        RollNo of student passed as Integer.
    * @throws StudentException if the given rollNo not exist.
    * @return Student detail if the rollNo of student exist
    */
    public Student retrieveStudentDetailByRollNo(int rollNo) {
        Student student = null;
        try (Session session = HibernateConnection.getSessionFactory().openSession()) { 
            student = session.get(Student.class, rollNo);
            return student;
        } catch (Exception e) { 
            throw new StudentException("Unable to show student detail with RollNo " + rollNo, e);
        } 
    }

   /**
    * <p>
    * Remove student detail by rollNo.
    * </p>
    *
    * @param rollNo
    *        RollNo of student as Integer.
    * @throws StudentException if rollNo of student not exist.
    * @return Boolean value as true if the student detail gets removed or else false
    */
    public boolean deleteStudentByRollNo(int rollNo) {
        Transaction transaction = null;
        Student student = null;
        boolean isDelete = false;
        try (Session session = HibernateConnection.getSessionFactory().openSession()) { 
            transaction = session.beginTransaction(); 
            student = session.get(Student.class, rollNo);
            if (null != student) {
                session.delete(student);
                transaction.commit();
                isDelete = true;
            }
        } catch (Exception e) { 
            if (transaction != null || transaction.isActive()) {
                transaction.rollback();
            }
            throw new StudentException("Unable to delete the student with rollNo " + rollNo, e);
        } 
        return isDelete;
    }
}